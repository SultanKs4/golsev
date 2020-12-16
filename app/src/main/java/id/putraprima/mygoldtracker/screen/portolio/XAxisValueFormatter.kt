package id.putraprima.mygoldtracker.screen.portolio

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class XAxisValueFormatter : ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        val sdf = SimpleDateFormat("MMM", Locale.getDefault()).format(Date())
        return "${value.toInt()}-$sdf"
    }
}