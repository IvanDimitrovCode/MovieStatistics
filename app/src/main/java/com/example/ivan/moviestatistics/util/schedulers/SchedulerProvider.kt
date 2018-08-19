package com.example.ivan.moviestatistics.util.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class SchedulerProvider private constructor() : BaseSchedulerProvider {

    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

    override fun io(): Scheduler {
        return Schedulers.io()
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    companion object {
        private var INSTANCE: SchedulerProvider? = null

        @JvmStatic
        fun requestInstance(): SchedulerProvider {
            if (INSTANCE == null) {
                INSTANCE = SchedulerProvider()
            }
            return INSTANCE as SchedulerProvider
        }
    }
}