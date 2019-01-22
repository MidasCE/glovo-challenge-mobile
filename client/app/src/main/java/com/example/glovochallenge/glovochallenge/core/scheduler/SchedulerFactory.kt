package com.example.glovochallenge.glovochallenge.core.scheduler

import io.reactivex.Scheduler

interface SchedulerFactory {
    fun io() : Scheduler

    fun main() : Scheduler
}
