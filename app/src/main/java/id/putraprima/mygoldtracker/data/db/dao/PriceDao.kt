package id.putraprima.mygoldtracker.data.db.dao

import androidx.room.*
import id.putraprima.mygoldtracker.data.model.Price
import kotlinx.coroutines.flow.Flow

@Dao
interface PriceDao {
    @Query("SELECT * FROM price ORDER BY date DESC")
    fun getPrice(): Flow<List<Price>>

    @Query("SELECT * FROM price ORDER BY date DESC LIMIT 1")
    fun getPriceToday(): Flow<Price>

    @Query("SELECT * FROM price ORDER BY date DESC LIMIT 7")
    fun getPriceWeekly(): Flow<List<Price>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrice(vararg price: Price)

    @Update(entity = Price::class)
    suspend fun updatePrice(vararg price: Price)

    @Query("DELETE FROM price")
    suspend fun deleteAllPrice()
}