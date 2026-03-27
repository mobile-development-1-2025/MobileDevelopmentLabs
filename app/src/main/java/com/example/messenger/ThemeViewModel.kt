import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ThemeViewModel : ViewModel() {

    private val _isDarkTheme = MutableLiveData(false)
    val isDarkTheme: LiveData<Boolean> get() = _isDarkTheme

    fun setTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ThemeViewModel", "onCleared()")
    }
}
