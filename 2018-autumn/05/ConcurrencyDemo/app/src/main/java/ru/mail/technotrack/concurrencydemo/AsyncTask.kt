package ru.mail.technotrack.concurrencydemo

import android.os.AsyncTask
import android.util.Log
import java.util.*
import java.util.concurrent.CountDownLatch

class AsyncTaskFileDownloader(private val latch : CountDownLatch,
                              private val listener : (IntArray) -> Unit) : AsyncTask<Int, Unit, IntArray>() {

    override fun doInBackground(vararg params: Int?): IntArray {
        return downloadTask(params[0]!!, params[1]!!) // bad, bad code
    }

    private fun downloadTask(start: Int, end: Int): IntArray {
        val result = IntArray(end - start)
        var index = 0
        for (item in start until end) {
            result[index++] = item
            Thread.sleep(10)
        }
        return result
    }

    override fun onPostExecute(result: IntArray?) {
        if (result != null && result.isNotEmpty()) {
            val text = result!!.map {
                it.toString()
            }.reduce { acc, s -> "$acc $s" }
            Log.v("MonitorSample", text)
            listener(result)
        }
        latch.countDown()
    }
}

private fun downloadFileUsingAsyncTask(size : Int) : IntArray {
    val parts = 5
    val offsets = devideFileToOffsets(size, parts)

    val buffers = LinkedList<IntArray>()
    val latch = CountDownLatch(parts)

    for (offset in offsets) {
        val task = AsyncTaskFileDownloader(latch) {
            buffers.add(it)
        }
        task.execute(offset.first, offset.second)
    }
    latch.await()

    return buffers.reduce { acc, array -> acc + array }
}

fun downloadFileUsingAsyncTaskAsync(size: Int, result: (IntArray) -> Unit) : AsyncTask<*,*,*> {
    val task = object : AsyncTask<Int, Void, IntArray>() {

        override fun onPostExecute(data: IntArray?) {
            data?.run(result)
        }

        override fun doInBackground(vararg params: Int?): IntArray {
            return downloadFileUsingAsyncTask(params[0]!!)
        }
    }
    task.execute(size)
    return task
}

fun downloadFileUsingAsyncTaskAsyncThread(size: Int, result: (IntArray) -> Unit) : Thread {
    val t = Thread {
        try {
            result(downloadFileUsingAsyncTask(size))
        } catch (e: InterruptedException) {
            result(intArrayOf())
        }
    }
    t.start()
    return t
}