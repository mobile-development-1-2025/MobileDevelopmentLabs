package ru.itmo.mobiledev.lab4

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
            if (AppPreferences.isOffline(getApplication())) {
                error.value = "Оффлайн режим включен"
                isLoading.value = false
                return@launch
            }
            val result = repository.refresh()
            if (result.isFailure) {
                error.value = "Нет сети, показаны сохраненные сообщения"
            }
            isLoading.value = false
        }
    }

    fun toggleLike(message: MessageEntity) {
        viewModelScope.launch {
            repository.toggleLike(message)
        }
    }

    fun clearError() {
        error.value = null
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared")
        super.onCleared()
    }

    companion object {
        private const val TAG = "NewsViewModel"
    }
}
