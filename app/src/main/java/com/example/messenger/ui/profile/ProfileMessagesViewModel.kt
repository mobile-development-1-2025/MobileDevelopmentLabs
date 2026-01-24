package com.example.mymessenger.ui.profile

import android.app.Application
import androidx.lifecycle.*
import com.example.mymessenger.data.MessageRepository
import com.example.mymessenger.data.local.AppDatabase
import com.example.mymessenger.data.local.MessageEntity
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MessageRepository

    private val _favorites = MutableLiveData<List<MessageEntity>>()
    val favorites: LiveData<List<MessageEntity>> = _favorites

    init {
        val dao = AppDatabase.getDatabase(application).messageDao()
        repository = MessageRepository(dao)
        loadFavorites()
    }

    fun loadFavorites() {
        viewModelScope.launch {
            _favorites.value = repository.getLikedMessages()
        }
    }
    fun removeFromFavorites(message: MessageEntity) {
        viewModelScope.launch {
            repository.toggleLike(message)
            loadFavorites()
        }
    }

}
