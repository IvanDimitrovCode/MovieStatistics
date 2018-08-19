package com.example.ivan.moviestatistics.util.schedulers

import com.example.ivan.moviestatistics.util.schedulers.BaseSchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TestsSchedulerProvider : BaseSchedulerProvider {
    override fun computation(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun ui(): Scheduler {
        return Schedulers.trampoline()
    }
}