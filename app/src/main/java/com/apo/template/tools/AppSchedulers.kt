package com.apo.template.tools

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

object AppSchedulers {

    private lateinit var io: Scheduler
    private lateinit var mainThread: Scheduler

    fun init(
        io: Scheduler = Schedulers.io(),
        mainThread: Scheduler = Schedulers.trampoline()
    ) {
        AppSchedulers.io = io
        AppSchedulers.mainThread = mainThread
    }

    fun io() = io
    fun mainThread() = mainThread
}

fun AppSchedulers.initForTests() {
    init(
        io = Schedulers.trampoline(),
        mainThread = Schedulers.trampoline()
    )
}