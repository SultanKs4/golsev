package id.putraprima.mygoldtracker.viewmodel

import androidx.lifecycle.*
import id.putraprima.mygoldtracker.data.GoldRepository
import id.putraprima.mygoldtracker.data.model.Portofolio
import id.putraprima.mygoldtracker.data.model.Price
import id.putraprima.mygoldtracker.util.Currency
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

class PortofolioViewModel(private val repository: GoldRepository) : ViewModel() {
    private var _portofolioTotal: MutableLiveData<Portofolio> = MutableLiveData()
    val portofolioTotal: LiveData<Portofolio>
        get() = _portofolioTotal
    private var _portofolioFraction: MutableLiveData<List<Portofolio>> = MutableLiveData()
    val portofolioFraction: LiveData<List<Portofolio>>
        get() = _portofolioFraction
    val sumHistory: LiveData<Double> = repository.historySum.asLiveData()
    val priceWeekly: LiveData<List<Price>> = repository.priceWeekly.asLiveData()

    fun setTotal(sumWeight: Double, listPriceWeekly: List<Price>) {
        viewModelScope.launch {
            _portofolioTotal.value = calculatePortofolio(sumWeight, listPriceWeekly[0], listPriceWeekly[1])
        }
    }

    fun setFraction(sumWeight: Double, listPriceWeekly: List<Price>) {
        viewModelScope.launch {
            val WEIGHT_LIST = arrayListOf(0.5, 1.0, 2.0, 3.0, 4.0, 5.0)
            val listPortofolio: ArrayList<Portofolio> = ArrayList()
            WEIGHT_LIST.forEach {
                if (it <= sumWeight)
                    listPortofolio.add(calculatePortofolio(it, listPriceWeekly[0], listPriceWeekly[1]))
            }
            _portofolioFraction.value = listPortofolio
        }
    }

    fun calculatePortofolio(weight: Double, priceToday: Price, priceYesterday: Price): Portofolio {
        val sellToday = priceToday.sell.multiply(weight.toBigDecimal())
        val sellYesterday = priceYesterday.sell.multiply(weight.toBigDecimal())
        var differenceValue = sellToday.minus(sellYesterday)
        var status = "+"
        if (differenceValue < BigDecimal.ZERO) {
            differenceValue = sellYesterday.minus(sellToday)
            status = "-"
        }
        val percentage = differenceValue.divide(sellYesterday, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal(100))
                .round(MathContext(2))
        val differenceFinal = "($status$percentage%) ${Currency.rupiah(differenceValue)}"
        return Portofolio(
                weight = weight,
                sellToday = sellToday,
                difference = differenceFinal
        )
    }
}

class PortofolioViewModelFactory(private val repository: GoldRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PortofolioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PortofolioViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }

}