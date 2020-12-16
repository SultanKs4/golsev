package id.putraprima.mygoldtracker.screen.portolio

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import id.putraprima.mygoldtracker.util.Currency

class YAxisValueFormatter : ValueFormatter() {
    override fun getPointLabel(entry: Entry?): String {
        return Currency.rupiah(entry!!.y.toBigDecimal())
    }

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return Currency.rupiah(value.toBigDecimal())
    }
}