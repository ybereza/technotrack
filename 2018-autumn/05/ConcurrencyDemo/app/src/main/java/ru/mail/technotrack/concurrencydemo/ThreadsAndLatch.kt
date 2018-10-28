package ru.mail.technotrack.concurrencydemo

import java.util.*
import java.util.concurrent.CountDownLatch

object ThreadsAndLatch {

    fun downloadBigFileAsync(size: Int, result: (IntArray) -> Unit): Thread {
        val t = Thread {
            try {
                result(ThreadsAndLatch.downloadBigFile(size))
            } catch (e: InterruptedException) {
                result(intArrayOf())
            }
        }
        t.start()
        return t
    }

    fun downloadBigFile(size: Int): IntArray {
        val parts = 5
        val offsets = devideFileToOffsets(size, parts)

        val latch = CountDownLatch(parts)
        val buffers = LinkedList<IntArray>()

        for (offset in offsets) {
            val buffer = IntArray(offset.second - offset.first)
            buffers.add(buffer)
            val t = createDownloadThread(buffer, offset.first, offset.second, latch)
            t.start()
        }
        latch.await()

        return buffers.reduce { acc, array -> acc + array }
    }

    fun createDownloadThread(buffer : IntArray, start : Int, end : Int, latch : CountDownLatch) : Thread {
        val t = Thread {
            val data = downloadTask(start, end)
            var index = 0
            while (index < buffer.size) {
                buffer[index] = data[index]
                ++index
            }
            latch.countDown()
        }
        return t
    }

    fun devideFileToOffsets(size: Int, parts: Int): ArrayList<Pair<Int, Int>> {
        val offsetSize = size / parts
        val offsetExtra = size % parts
        val offsets = ArrayList<Pair<Int, Int>>(parts)
        for (item in 0 until parts) {
            val start = item * offsetSize
            val end = start + offsetSize
            if (item + 1 == parts) offsets.add(Pair(start, end + offsetExtra))
            else offsets.add(Pair(start, end))
        }
        return offsets
    }

    fun downloadTask(start: Int, end: Int): IntArray {
        val result = IntArray(end - start)
        var index = 0
        for (item in start until end) {
            result[index++] = item
            Thread.sleep(10)
        }
        return result
    }
}