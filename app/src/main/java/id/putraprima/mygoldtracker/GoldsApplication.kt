package id.putraprima.mygoldtracker

import android.app.Application
import android.content.Context
import id.putraprima.mygoldtracker.data.GoldRepository
import id.putraprima.mygoldtracker.data.api.GoldService
import id.putraprima.mygoldtracker.data.api.RetrofitBuilder
import id.putraprima.mygoldtracker.data.db.GoldRoomDatabase

class GoldsApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: GoldsApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    private val service by lazy { RetrofitBuilder.build().create(GoldService::class.java) }
    private val database by lazy { GoldRoomDatabase.getDatabase(this) }
    val repository by lazy { GoldRepository(database, service) }
}