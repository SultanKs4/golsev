package id.putraprima.mygoldtracker.viewmodel

import androidx.lifecycle.*
import id.putraprima.mygoldtracker.data.GoldRepository
import id.putraprima.mygoldtracker.data.model.Profile
import kotlinx.coroutines.launch


class FrontViewModel(private val repository: GoldRepository) : ViewModel() {
    val profile: LiveData<List<Profile>> = repository.profile.asLiveData()

    fun priceWeb() {
        viewModelScope.launch {
            repository.getPriceHistory()
            repository.getPriceToday()
        }
    }
}

class FrontViewModelFactory(private val repository: GoldRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FrontViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FrontViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}