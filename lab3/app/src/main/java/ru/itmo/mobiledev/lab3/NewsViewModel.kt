package ru.itmo.mobiledev.lab3

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MessageRepository(
        api = ApiClient.api,
        dao = AppDatabase.getInstance(application).messageDao()
    )

    val messages: LiveData<List<MessageEntity>> = repository.observeMessages().asLiveData()
    val isLoading = MutableLiveData(false)
    val error = MutableLiveData<String?>(null)

    init {
        Log.d(TAG, "init")
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null
            val result = repository.refresh()
            if (result.isFailure) {
                error.value = "Нет сети, показаны сохраненные сообщения"
            }
            isLoading.value = false
        }
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared")
        super.onCleared()
    }

    companion object {
        private const val TAG = "NewsViewModel"
    }
}
