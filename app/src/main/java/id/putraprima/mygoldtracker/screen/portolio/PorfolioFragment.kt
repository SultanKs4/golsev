package id.putraprima.mygoldtracker.screen.portolio

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.github.mikephil.charting.animation.Easing.EaseOutQuart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import id.putraprima.mygoldtracker.GoldsApplication
import id.putraprima.mygoldtracker.R
import id.putraprima.mygoldtracker.data.model.Price
import id.putraprima.mygoldtracker.databinding.FragmentPorfolioBinding
import id.putraprima.mygoldtracker.viewmodel.PortofolioViewModel
import id.putraprima.mygoldtracker.viewmodel.PortofolioViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PorfolioFragment : Fragment() {
    private lateinit var binding: FragmentPorfolioBinding
    private val viewModel: PortofolioViewModel by viewModels {
        PortofolioViewModelFactory((activity?.application as GoldsApplication).repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_porfolio, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.priceWeekly.observe(viewLifecycleOwner, { price ->
            if (price.isNotEmpty()) {
                viewModel.sumHistory.observe(viewLifecycleOwner, { history ->
                    if (history != null) {
                        viewModel.setTotal(history, price)
                        viewModel.setFraction(history, price)
                    }
                })
                setupGoldChart(price)
            }
        })
        setupRvPortofolio()
    }

    private fun setupGoldChart(listPrice: List<Price>) {
        val chart: LineChart = binding.goldChart
        val entriesChart: ArrayList<Entry> = ArrayList()
        listPrice.asReversed().forEach {
            val date = SimpleDateFormat("dd", Locale.getDefault()).format(it.date)
            entriesChart.add(Entry(date.toFloat(), it.sell.toFloat()))
        }
        val dataSet = LineDataSet(entriesChart, "Price")
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSet.color = Color.rgb(96, 187, 85);
        dataSet.setDrawValues(false)
        dataSet.circleRadius = 5f
        dataSet.circleHoleColor = Color.rgb(96, 187, 85)
        dataSet.setCircleColor(Color.rgb(96, 187, 85))
        val lineData = LineData(dataSet)

        val markerView = context?.let { CustomMarkerView(it, R.layout.custom_marker_view) }
        markerView?.chartView = chart
        chart.marker = markerView
        chart.description = null
        chart.axisRight.isEnabled = false
        chart.axisLeft.setDrawGridLines(false)
        chart.xAxis.setDrawGridLines(false)
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.animateY(500, EaseOutQuart)
        chart.axisLeft.textColor = Color.rgb(96, 187, 85)
        chart.xAxis.textColor = Color.rgb(96, 187, 85)
        chart.axisLeft.textSize = 12f
        chart.legend.textSize = 12f
        val mode = context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
        var color = Color.rgb(40, 40, 40)
        when (mode) {
            Configuration.UI_MODE_NIGHT_YES -> {
                color = Color.rgb(255, 255, 255)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                color = Color.rgb(0, 0, 0)
            }
        }
        chart.legend.textColor = color

        chart.data = lineData
        chart.axisLeft.valueFormatter = YAxisValueFormatter()
        chart.xAxis.valueFormatter = XAxisValueFormatter()
        chart.invalidate()
    }

    private fun setupRvPortofolio() {
        val recyclerView = binding.rvPortofolio
        val gridLayoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = gridLayoutManager
        val adapter = PortofolioAdapter()
        recyclerView.adapter = adapter

        viewModel.portofolioFraction.observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.portofolioList = it
            }
        })
    }
}