package ru.mail.technotrack.concurrencydemo

import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

fun downloadBigFileAsync(size : Int, result : (IntArray) -> Unit) : Thread {
    val t = Thread{
        try {
            result(downloadBigFile(size))
        }
        catch (e : InterruptedException) {
            result(intArrayOf())
        }
    }
    t.start()
    return t
}

fun downloadBigFile(size : Int) : IntArray {
    val parts = 5
    val offsets = devideFileToOffsets(size, parts)

    val executor = Executors.newFixedThreadPool(parts)

    val futures = LinkedList<Future<IntArray>>()

    for (part in offsets) {
        val future : Future<IntArray> = executor.submit(Callable {
            downloadTask(part.first, part.second)
        })
        futures.add(future)
    }

    val result = futures.map {
        it.get()
    }.reduce { acc, array -> acc + array }

    return result
}

fun devideFileToOffsets(size : Int, parts : Int) : ArrayList<Pair<Int, Int>> {
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

fun downloadTask(start : Int, end : Int) : IntArray {
    val result = IntArray(end-start)
    var index = 0
    for(item in start until end) {
        result[index++] = item
        Thread.sleep(10)
    }
    return result
}