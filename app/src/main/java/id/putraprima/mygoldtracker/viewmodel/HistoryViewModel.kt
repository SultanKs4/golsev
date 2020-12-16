package id.putraprima.mygoldtracker.viewmodel

import androidx.lifecycle.*
import id.putraprima.mygoldtracker.data.GoldRepository
import id.putraprima.mygoldtracker.data.model.History
import id.putraprima.mygoldtracker.data.model.Price
import id.putraprima.mygoldtracker.data.model.Profile
import java.lang.IllegalArgumentException

class HistoryViewModel(private val goldRepository : GoldRepository) : ViewModel() {
    val historyList:LiveData<List<History>> = goldRepository.history.asLiveData()
}


class HistoryViewModelFactory(private val goldRepository : GoldRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(goldRepository) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }

}