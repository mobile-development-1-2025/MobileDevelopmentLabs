package com.example.lab1.data.remote.pagination

import android.content.Context
import android.content.SharedPreferences

class PaginationManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREF_NAME,
        Context.MODE_PRIVATE
    )

    companion object {
        private const val PREF_NAME = "pagination_prefs"
        private const val KEY_CURRENT_PAGE = "current_page"
        private const val KEY_PAGE_SIZE = "page_size"
        private const val DEFAULT_PAGE_SIZE = 30
        private const val MAX_PAGES = 12 // Примерно 340 комментариев / 30 на страницу
    }

    /**
     * Получает следующую страницу для загрузки
     * Переключается между страницами циклически для проверки обновления
     */
    fun getNextPage(): Int {
        val currentPage = prefs.getInt(KEY_CURRENT_PAGE, 0)
        val nextPage = (currentPage + 1) % MAX_PAGES
        prefs.edit().putInt(KEY_CURRENT_PAGE, nextPage).apply()
        return nextPage
    }

    /**
     * Получает текущую страницу
     */
    fun getCurrentPage(): Int {
        return prefs.getInt(KEY_CURRENT_PAGE, 0)
    }

    /**
     * Получает размер страницы
     */
    fun getPageSize(): Int {
        return prefs.getInt(KEY_PAGE_SIZE, DEFAULT_PAGE_SIZE)
    }

    /**
     * Сбрасывает пагинацию на первую страницу
     */
    fun reset() {
        prefs.edit().putInt(KEY_CURRENT_PAGE, 0).apply()
    }
}
