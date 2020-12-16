package id.putraprima.mygoldtracker.screen.history

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
import id.putraprima.mygoldtracker.databinding.FragmentHistoryBinding
import id.putraprima.mygoldtracker.viewmodel.HistoryViewModel
import id.putraprima.mygoldtracker.viewmodel.HistoryViewModelFactory

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding

    private val viewModel: HistoryViewModel by viewModels {
        HistoryViewModelFactory((activity?.application as GoldsApplication).repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRvHistory()
    }

    private fun setupRvHistory() {
        val recyclerView = binding.rvHistory
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        val adapter = HistoryAdapter()
        recyclerView.adapter = adapter

        viewModel.historyList.observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.historyList = it
            }
        })
    }
}