package id.putraprima.mygoldtracker.util

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapter {
    @BindingAdapter("app:convertRupiah")
    @JvmStatic
    fun convertRupiah(view: TextView, number: BigDecimal?) {
        if (number != null) {
            view.text = Currency.rupiah(number)
        }
    }

    @BindingAdapter("app:convertWeight")
    @JvmStatic
    fun convertWeight(view: TextView, number: Double?) {
        if (number != null) {
            view.text = "$number gr"
        }
    }

    @BindingAdapter("app:convertDateHistory")
    @JvmStatic
    fun convertDateHistory(view: TextView, date: Date?) {
        if (date != null) {
            view.text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
        }
    }

    @BindingAdapter("app:textDifference")
    @JvmStatic
    fun textDifference(view: TextView, difference: String?) {
        if (!difference.isNullOrEmpty()) {
            view.text = difference
            view.setTextColor(if (difference.startsWith("-", startIndex = 1)) Color.RED else Color.GREEN)
        }
    }
}