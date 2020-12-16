package id.putraprima.mygoldtracker.viewmodel

import androidx.lifecycle.*
import id.putraprima.mygoldtracker.data.GoldRepository
import id.putraprima.mygoldtracker.data.model.History
import id.putraprima.mygoldtracker.data.model.Price
import kotlinx.coroutines.launch

class HargaViewModel(private val repository: GoldRepository) : ViewModel() {
    val priceToday: LiveData<Price> = repository.priceToday.asLiveData()

    fun insert(history: History) = viewModelScope.launch {
        repository.insertHistory(history)
    }
}

class HargaViewModelFactory(private val repository: GoldRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HargaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HargaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}