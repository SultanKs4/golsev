package id.putraprima.mygoldtracker.util

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class Currency {
    companion object {
        fun rupiah(number: BigDecimal): String {
            val numberFormat = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            return numberFormat.format(number).toString()
        }
    }
}