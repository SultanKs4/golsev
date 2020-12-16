package id.putraprima.mygoldtracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*

@Entity(tableName = "history")
data class History(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val weight: Double,
        val date: Date,
        val price: BigDecimal
)