package com.dak0ta.learnity.app.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.dak0ta.learnity.app.notification.QuotesNotificationManager
import com.dak0ta.learnity.core.datastore.domain.usecase.userid.GetUserIdUseCase
import com.dak0ta.learnity.feature.home.domain.usecase.RefreshQuotesUseCase
import javax.inject.Inject

class QuotesWorkerFactory @Inject constructor(
    private val getUserIdUseCase: GetUserIdUseCase,
    private val refreshQuotesUseCase: RefreshQuotesUseCase,
    private val notificationManager: QuotesNotificationManager,
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters,
    ): ListenableWorker? =
        when (workerClassName) {
            QuotesRefreshWorker::class.java.name ->
                QuotesRefreshWorker(
                    appContext,
                    workerParameters,
                    getUserIdUseCase,
                    refreshQuotesUseCase,
                    notificationManager,
                )

            else -> null
        }
}
