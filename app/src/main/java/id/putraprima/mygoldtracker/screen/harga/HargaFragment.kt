package id.putraprima.mygoldtracker.screen.harga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import id.putraprima.mygoldtracker.GoldsApplication
import id.putraprima.mygoldtracker.R
import id.putraprima.mygoldtracker.data.model.Price
import id.putraprima.mygoldtracker.databinding.FragmentHargaBinding
import id.putraprima.mygoldtracker.viewmodel.HargaViewModel
import id.putraprima.mygoldtracker.viewmodel.HargaViewModelFactory

class HargaFragment : Fragment() {
    private lateinit var binding: FragmentHargaBinding
    private val viewModel: HargaViewModel by viewModels {
        HargaViewModelFactory((activity?.application as GoldsApplication).repository)
    }
    private val WEIGHT_LIST = arrayListOf(0.5, 1.0, 2.0, 3.0, 4.0)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_harga, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRvHarga()
    }

    private fun setupRvHarga() {
        val recyclerView = binding.rvHarga
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        val adapter = HargaAdapter(object : OnItemHargaListener {
            override fun onHargaClicked(price: Price) {
                BuyDialogFragment(price).show(parentFragmentManager, "Dialog Buy Gold")
            }
        })
        recyclerView.adapter = adapter
        viewModel.priceToday.observe(viewLifecycleOwner, {
            if (it != null) {
                val listPrice = ArrayList<Price>()
                WEIGHT_LIST.forEach { weight ->
                    val priceBuy = it.buy.multiply(weight.toBigDecimal())
                    val priceSell = it.sell.multiply(weight.toBigDecimal())
                    listPrice.add(Price(weight = weight, date = it.date, buy = priceBuy,
                            sell = priceSell)
                    )
                }
                adapter.priceList = listPrice
            }
        })
    }
}