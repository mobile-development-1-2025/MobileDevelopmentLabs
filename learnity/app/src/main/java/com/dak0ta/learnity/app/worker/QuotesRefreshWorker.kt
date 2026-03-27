package com.dak0ta.learnity.app.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dak0ta.learnity.app.notification.QuotesNotificationManager
import com.dak0ta.learnity.core.datastore.domain.usecase.userid.GetUserIdUseCase
import com.dak0ta.learnity.feature.home.domain.usecase.RefreshQuotesUseCase

@Suppress("TooGenericExceptionCaught")
class QuotesRefreshWorker(
    context: Context,
    params: WorkerParameters,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val refreshQuotesUseCase: RefreshQuotesUseCase,
    private val notificationManager: QuotesNotificationManager,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Log.d(TAG, "Worker started")

        val userId = getUserIdUseCase()
        if (userId == null || userId == 0) {
            Log.d(TAG, "User not authorized, skipping work")
            return Result.success()
        }

        return try {
            refreshQuotesUseCase()
            Log.d(TAG, "Quotes refreshed successfully")
            notificationManager.showQuotesUpdated()
            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Error refreshing quotes", e)
            Result.retry()
        }
    }

    private companion object {

        const val TAG = "Learnity:QuotesRefreshWorker"
    }
}
