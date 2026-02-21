package com.example.messengerlab.ui.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.messengerlab.data.local.AppDatabase
import com.example.messengerlab.data.local.CommentEntity
import com.example.messengerlab.data.repository.CommentRepository
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "NewsViewModel"
    private val repository = CommentRepository(application)
    private val dao = AppDatabase.getInstance(application).commentDao()

    private val _comments = MutableLiveData<List<CommentEntity>>()
    val comments: LiveData<List<CommentEntity>> = _comments

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init { load() }

    fun load(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                _comments.value = repository.fetchComments(forceRefresh)
                Log.d(TAG, "Loaded ${_comments.value?.size} comments")
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
                Log.e(TAG, "Load failed: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }

    fun sendComment(text: String) {
        viewModelScope.launch {
            try {
                val newId = -(System.currentTimeMillis() % Int.MAX_VALUE).toInt()
                val comment = CommentEntity(
                    id = newId,
                    postId = 0,
                    name = "Me",
                    email = "me@local",
                    body = text,
                    createdAt = System.currentTimeMillis()
                )
                dao.insertAll(listOf(comment))
                _comments.value = dao.getAll()
                Log.d(TAG, "Comment sent: $text")
            } catch (e: Exception) {
                _error.value = "Send error: ${e.message}"
                Log.e(TAG, "Send failed: ${e.message}")
            }
        }
    }
}