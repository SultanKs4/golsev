package id.putraprima.mygoldtracker.data.db.dao

import androidx.room.*
import id.putraprima.mygoldtracker.data.model.Profile
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile LIMIT 1")
    fun getProfile(): Flow<List<Profile>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(vararg profile: Profile)

    @Update(entity = Profile::class)
    suspend fun updateProfile(vararg profile: Profile)
}