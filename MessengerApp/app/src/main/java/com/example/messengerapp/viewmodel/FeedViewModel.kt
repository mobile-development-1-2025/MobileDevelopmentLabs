package com.example.messengerapp.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.messengerapp.data.model.MessageEntity
import com.example.messengerapp.data.repository.MessageRepository
import kotlinx.coroutines.launch
import android.util.Log
import com.example.messengerapp.util.NotificationHelper

class FeedViewModel(
    application: Application,
    private val repository: MessageRepository
) : AndroidViewModel(application) {

    val messages = MutableLiveData<List<MessageEntity>>()
    val loading = MutableLiveData<Boolean>(false)
    val error = MutableLiveData<String?>()

    private val likedIds = mutableSetOf<Int>()


    init {
        Log.d("FeedViewModel", "ViewModel created")
        loadMessages()
    }

    fun loadMessages(showNotification: Boolean = false) {
        viewModelScope.launch {
            loading.value = true
            try {
                val result = repository.getMessages()
                val updated = result.map { message ->
                    message.copy(
                        isLiked = likedIds.contains(message.id)
                    )
                }
                messages.value = updated
                error.value = null
                if (showNotification) {
                    NotificationHelper.showSyncNotification(getApplication())
                }
            } catch (e: Exception) {
                error.value = e.message
            } finally {
                loading.value = false
            }
        }
    }

    fun toggleLike(message: MessageEntity) {
        if (likedIds.contains(message.id)) {
            likedIds.remove(message.id)
        } else {
            likedIds.add(message.id)
        }

        messages.value = messages.value?.map {
            if (it.id == message.id)
                it.copy(isLiked = !it.isLiked)
            else it
        }
    }



    override fun onCleared() {
        super.onCleared()
        Log.d("FeedViewModel", "ViewModel cleared")
    }
}