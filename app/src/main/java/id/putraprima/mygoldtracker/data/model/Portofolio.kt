package id.putraprima.mygoldtracker.data.model

import java.math.BigDecimal

data class Portofolio(
        val weight: Double,
        val sellToday: BigDecimal,
        val difference: String
)