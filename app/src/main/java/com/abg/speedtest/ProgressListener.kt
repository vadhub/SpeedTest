package com.abg.speedtest

interface ProgressListener {
    fun update(bytesRead: Long, contentLength: Long, done: Boolean)
}
