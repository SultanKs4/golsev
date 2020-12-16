package id.putraprima.mygoldtracker.data.db.dao

import androidx.room.*
import id.putraprima.mygoldtracker.data.model.History
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history ORDER BY date DESC")
    fun getHistory(): Flow<List<History>>

    @Query("SELECT SUM(weight) FROM history")
    fun getSumHistory(): Flow<Double>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: History)

    @Update(entity = History::class)
    suspend fun updateHistory(vararg history: History)
}