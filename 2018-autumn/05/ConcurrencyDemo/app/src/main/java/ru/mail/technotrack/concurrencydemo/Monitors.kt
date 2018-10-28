package ru.mail.technotrack.concurrencydemo

import android.os.Handler

class MonitorSample(private val uiHandler : Handler, private val messageId : Int) {
    private val buffer = IntArray(25) { _ -> 0 }
    private val maxsize = buffer.size
    private var index = 0

    @Volatile private var finished = true

    private val monitor = java.lang.Object()

    private fun put() {
        synchronized(monitor) {
            while (index == maxsize) {
                propagate()
                monitor.wait()
            }
            buffer[index] = index++
            monitor.notify()
        }
    }

    private fun unsafePut() {
        synchronized(monitor) {
            while (index == maxsize) {
                propagate()
                Thread.yield()
            }
            buffer[index] = index++
        }
    }

    private fun remove() {
        synchronized(monitor) {
            while (index == 0) {
                monitor.wait()
            }
            buffer[--index] = 0
            monitor.notify()
        }
    }

    private fun unsafeRemove() {
        synchronized(monitor) {
            while (index == 0) {
                Thread.yield()
            }
            buffer[--index] = 0
        }
    }

    private fun propagate() {
        val msg = uiHandler.obtainMessage(messageId, buffer)
        uiHandler.sendMessage(msg)
    }

    fun execute() {
        finished = false

        val t1 = Thread {
            while (!finished) put()
        }
        val t2 = Thread {
            while (!finished) remove()
        }

        t1.start()
        t2.start()
    }

    fun stop() {
        finished = true
    }

    fun isFinished() : Boolean {
        return finished
    }

}