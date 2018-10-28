package ru.mail.technotrack.concurrencydemo

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var textView : TextView
    lateinit var startStop : Button

    var latUpdateTime : Long = 0

    val handler = @SuppressLint("HandlerLeak")

    object : Handler() {
        override fun handleMessage(msg: Message?) {
            msg?.run {
                val list = msg.obj as IntArray
                showBuffer(list)
            }
        }
    }

    val monitorSample = MonitorSample(handler, 12345)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
        startStop = findViewById(R.id.startstop)

        //monitorSample()
        //downloadFileWithExecutor()
        //downloadExecutorInMainThread()
        //downloadFileWithThreads()
        //downloadFileWithAsyncTaskWrong()
        //downloadFileWithAsyncTask()
    }

    private fun downloadFileWithAsyncTask() {
        var clicked = false
        var thread : Thread? = null
        startStop.setOnClickListener {
            if (clicked) {
                thread?.interrupt()
            }
            else {
                textView.text = ""
                thread = downloadFileUsingAsyncTaskAsyncThread(1024) {
                    clicked = false
                    handler.post {
                        showBuffer(it)
                    }

                }
            }
            clicked = !clicked
        }
    }

    private fun downloadFileWithAsyncTaskWrong() {
        var clicked = false
        var task :  AsyncTask<*, *, *>? = null
        startStop.setOnClickListener {
            if (clicked) {
                task?.cancel(true)
            }
            else {
                textView.text = ""
                task = downloadFileUsingAsyncTaskAsync(1024) {
                    clicked = false
                    showBuffer(it)
                }
            }
            clicked = !clicked
        }
    }

    private fun downloadFileWithThreads() {
        var clicked = false
        var thread : Thread? = null
        startStop.setOnClickListener {
            if (clicked) {
                thread?.interrupt()
            }
            else {
                textView.text = ""
                thread = ThreadsAndLatch.downloadBigFileAsync(1024) {
                    clicked = false
                    handler.post {
                        showBuffer(it)
                    }

                }
            }
            clicked = !clicked
        }
    }

    private fun downloadFileWithExecutor() {
        var clicked = false
        var thread : Thread? = null
        startStop.setOnClickListener {
            if (clicked) {
                thread?.interrupt()
            }
            else {
                textView.text = ""
                thread = downloadBigFileAsync(1024) {
                    clicked = false
                    handler.post {
                        showBuffer(it)
                    }
                }
            }
            clicked = !clicked
        }
    }

    private fun downloadExecutorInMainThread() {
        startStop.setOnClickListener {
            showBuffer(downloadBigFile(1024))
        }

    }

    private fun monitorSample() {
        startStop.setOnClickListener {
            if (monitorSample.isFinished()) {
                monitorSample.execute()
            }
            else {
                monitorSample.stop()
            }
        }
    }

    private fun showBuffer(buffer : IntArray) {
        if (System.currentTimeMillis() - latUpdateTime >= 16) {
            if (buffer.isNotEmpty()) {
                val text = buffer.map {
                    it.toString()
                }.reduce { acc, s -> "$acc $s" }
                textView.text = text
                Log.v("MonitorSample", text)
            }
            else {
                textView.text = "Empty buffer"
            }
            latUpdateTime = System.currentTimeMillis()
        }
    }
}
