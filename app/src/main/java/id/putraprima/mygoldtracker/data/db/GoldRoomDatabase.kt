package id.putraprima.mygoldtracker.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.putraprima.mygoldtracker.data.db.converter.Converters
import id.putraprima.mygoldtracker.data.db.dao.HistoryDao
import id.putraprima.mygoldtracker.data.db.dao.PriceDao
import id.putraprima.mygoldtracker.data.db.dao.ProfileDao
import id.putraprima.mygoldtracker.data.model.History
import id.putraprima.mygoldtracker.data.model.Price
import id.putraprima.mygoldtracker.data.model.Profile

@Database(entities = [Profile::class, Price::class, History::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GoldRoomDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun priceDao(): PriceDao
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: GoldRoomDatabase? = null

        fun getDatabase(context: Context): GoldRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        GoldRoomDatabase::class.java,
                        "MyGoldTracker"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}