package id.putraprima.mygoldtracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class Profile(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val nama: String,
        val email: String,
        val path: String,
)
