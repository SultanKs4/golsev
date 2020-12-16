package id.putraprima.mygoldtracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import id.putraprima.mygoldtracker.data.GoldRepository
import id.putraprima.mygoldtracker.data.model.Profile

class SplashViewModel(private val repository: GoldRepository) : ViewModel() {
    val profile: LiveData<List<Profile>> = repository.profile.asLiveData()
}

class SplashViewModelFactory(private val repository: GoldRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SplashViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}