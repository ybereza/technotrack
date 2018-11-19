package ru.mail.techotrack.lection8

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.util.LruCache
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

import java.io.File
import java.net.URL
import kotlin.coroutines.CoroutineContext


class ListFragment : Fragment() {
    private lateinit var memoryCache: LruCache<String, BitmapDrawable>
    private var imageSize: Int = 0
    private lateinit var bitmapLoader : BitmapLoader

    private interface DataStateListener {
        fun onItemChanged(position: Int)
    }

    private fun updateImageSize(dm: DisplayMetrics): Int {
        var h = dm.heightPixels
        var w = dm.widthPixels
        if (w > h) {
            val tmp = w
            w = h
            h = tmp
        }
        return (Math.min(h * 0.7f, w * 0.7f) + 0.5f).toInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        val cacheSize = maxMemory / 8
        memoryCache = object : LruCache<String, BitmapDrawable>(cacheSize) {
            override fun sizeOf(key: String, bitmap: BitmapDrawable): Int {
                return bitmap.bitmap.rowBytes * bitmap.bitmap.height / 1024
            }
        }

        imageSize = updateImageSize(resources.displayMetrics)
        bitmapLoader = BitmapLoader(requireContext().cacheDir, memoryCache, imageSize, resources)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.list_fragment, container, false) ?: return null

        val mRecyclerView = root.findViewById<View>(R.id.dict_list) as RecyclerView
        mRecyclerView.setHasFixedSize(true)

        val mLayoutManager = LinearLayoutManager(requireActivity())
        mRecyclerView.layoutManager = mLayoutManager

        val wa = MyAdapter(
                bitmapLoader,
                ImageData.images
        )
        mRecyclerView.adapter = wa
        return root
    }

    override fun onDestroy() {
        bitmapLoader.cancel()
        super.onDestroy()
    }

    private class ViewHolder(card: View) : RecyclerView.ViewHolder(card) {
        val textView: TextView = card.findViewById(R.id.dict_word)
        val imageView: ImageView = card.findViewById(R.id.dict_image)
    }

    private inner class MyAdapter(private var loader : BitmapLoader,
                                  private var data: Array<ImageData.Image>) : RecyclerView.Adapter<ViewHolder>(), DataStateListener {

        override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val convertView = inflater.inflate(R.layout.list_element, parent, false)
            val holder = ViewHolder(convertView)

            val lp = LinearLayout.LayoutParams(imageSize, imageSize)
            lp.gravity = Gravity.CENTER_HORIZONTAL
            holder.imageView.layoutParams = lp

            return holder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val word = data[position]
            holder.textView.text = word.text

            val image = loader.getBitmapDrawable(word.text)
            if (image != null) {
                holder.imageView.setImageDrawable(image)
            }
            else {
                holder.imageView.setImageResource(android.R.drawable.sym_def_app_icon)
                loader.loadBitmap(position, word.text, this)
            }
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onItemChanged(position: Int) {
            notifyItemChanged(position)
        }
    }

    private class BitmapLoader(private val cacheDir : File,
                               private val cache : LruCache<String, BitmapDrawable>,
                               private val imageSize: Int,
                               private val resources : Resources,
                               private val job : Job = Job()) : CoroutineScope {

        override val coroutineContext: CoroutineContext
            get() = Dispatchers.Main + job

        fun getBitmapDrawable(key : String) : BitmapDrawable? = cache.get(key)

        fun loadBitmap(position: Int, name: String, listener: DataStateListener) {
            launch(Dispatchers.Main) {
                val result = async(IO) { loadBitmapAsync(position, name, imageSize) }.await()
                if (result) {
                    listener.onItemChanged(position)
                }
            }
        }

        fun cancel() {
            job.cancel()
        }

        private fun putBitmapIntoCache(key: String, bitmap: Bitmap) {
            cache.put(key, BitmapDrawable(resources, bitmap))
        }

        private fun loadBitmapAsync(position: Int, name: String, imageSize : Int) : Boolean {
            val file = File(cacheDir, name.replace("/", ""))
            var bitmap = file.asBitmap(imageSize, imageSize)
            if (bitmap == null) {
                val url = URL(name)
                val downloadedFile = url.asFile(file)
                bitmap = downloadedFile.asBitmap(imageSize, imageSize)
            }
            bitmap?.run {
                putBitmapIntoCache(name, this)
            }

            return bitmap != null
        }
    }
}

