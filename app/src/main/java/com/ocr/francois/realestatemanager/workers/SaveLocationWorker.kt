package com.ocr.francois.realestatemanager.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class SaveLocationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        TODO("Not yet implemented")
    }
}