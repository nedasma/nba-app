package com.example.nbaapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nbaapp.data.TeamRepositoryImpl
import com.example.nbaapp.utils.DispatcherProvider

/**
 * Factory to create AppViewModel instances in fragments
 */
class AppViewModelProviderFactory(
    private val repository: TeamRepositoryImpl,
    private val dispatchers: DispatcherProvider
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return AppViewModel(repository, dispatchers) as T
    }
}