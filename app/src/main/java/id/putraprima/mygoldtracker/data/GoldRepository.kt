package id.putraprima.mygoldtracker.data

import androidx.annotation.WorkerThread
import id.putraprima.mygoldtracker.data.api.GoldService
import id.putraprima.mygoldtracker.data.db.GoldRoomDatabase
import id.putraprima.mygoldtracker.data.db.dao.HistoryDao
import id.putraprima.mygoldtracker.data.db.dao.PriceDao
import id.putraprima.mygoldtracker.data.db.dao.ProfileDao
import id.putraprima.mygoldtracker.data.model.History
import id.putraprima.mygoldtracker.data.model.Price
import id.putraprima.mygoldtracker.data.model.Profile
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GoldRepository(database: GoldRoomDatabase, private val service: GoldService) {
    private val profileDao: ProfileDao = database.profileDao()
    private val historyDao: HistoryDao = database.historyDao()
    private val priceDao: PriceDao = database.priceDao()

    val profile: Flow<List<Profile>> = profileDao.getProfile()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertProfile(profile: Profile) {
        profileDao.insertProfile(profile)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateProfile(profile: Profile) {
        profileDao.updateProfile(profile)
    }

    val history: Flow<List<History>> = historyDao.getHistory()

    val historySum: Flow<Double> = historyDao.getSumHistory()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertHistory(history: History) {
        historyDao.insertHistory(history)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateHistory(history: History) {
        historyDao.updateHistory(history)
    }

    val price: Flow<List<Price>> = priceDao.getPrice()

    val priceToday: Flow<Price> = priceDao.getPriceToday()

    val priceWeekly: Flow<List<Price>> = priceDao.getPriceWeekly()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertPrice(vararg price: Price) {
        priceDao.insertPrice(*price)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updatePrice(price: Price) {
        priceDao.updatePrice(price)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllPrice() {
        priceDao.deleteAllPrice()
    }

    @WorkerThread
    suspend fun getPriceToday() {
        val price = service.getPriceToday()
        val bodyJson = price.body()?.string()
        if (bodyJson != null) {
            val jsonObject = JSONObject(bodyJson)
            jsonObject.getJSONObject("data").let {
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it.getString("date"))
                val dataToday = Price(
                        date = date,
                        buy = it.getDouble("buy_price").toBigDecimal(),
                        sell = it.getDouble("sell_price").toBigDecimal()
                )
                updatePrice(dataToday)
            }
        }
    }

    @WorkerThread
    suspend fun getPriceHistory() {
        deleteAllPrice()
        val priceHistory = service.getPriceHistory()
        val bodyJson = priceHistory.body()?.string()
        val listPrice = ArrayList<Price>()
        if (bodyJson != null) {
            val jsonObject = JSONObject(bodyJson)
            jsonObject.optJSONArray("data").let {
                0.until(it.length()).map { i -> it.optJSONObject(i) }
            }.map {
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it.getString("date_price"))
                listPrice.add(Price(
                        date = date,
                        buy = it.getDouble("buy_price").toBigDecimal(),
                        sell = it.getDouble("sell_price").toBigDecimal()
                ))
            }
            insertPrice(*listPrice.toTypedArray())
        }
    }
}