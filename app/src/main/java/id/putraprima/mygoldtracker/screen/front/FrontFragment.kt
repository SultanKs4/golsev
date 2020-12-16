package id.putraprima.mygoldtracker.screen.front

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import id.putraprima.mygoldtracker.GoldsApplication
import id.putraprima.mygoldtracker.R
import id.putraprima.mygoldtracker.databinding.FragmentFrontBinding
import id.putraprima.mygoldtracker.util.ImageHelper
import id.putraprima.mygoldtracker.viewmodel.FrontViewModel
import id.putraprima.mygoldtracker.viewmodel.FrontViewModelFactory


class FrontFragment : Fragment() {
    private lateinit var binding: FragmentFrontBinding
    private val viewModel: FrontViewModel by viewModels {
        FrontViewModelFactory((activity?.application as GoldsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.show()
        viewModel.priceWeb()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_front, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.imageProfile.setImageResource(R.drawable.default_profile)
        binding.editProfile.setOnClickListener {
            val action = FrontFragmentDirections.actionFrontFragmentToProfileFragment("update")
            findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.profile.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                lifecycleScope.launchWhenCreated {
                    val bitmap = ImageHelper.loadImageFromStorage(it[0].path)
                    binding.imageProfile.setImageBitmap(bitmap)
                    binding.nama = it[0].nama
                    binding.email = it[0].email
                }
            }
        })

        binding.viewPager.adapter = FrontPagerAdapter(this)
        val tabLayoutMediator = TabLayoutMediator(
                binding.tabLayout, binding.viewPager
        ) { tab, position ->
            when (position) {
                0 -> tab.text = "Dompet"
                1 -> tab.text = "Harga"
                2 -> tab.text = "Pembelian"
            }
        }

        tabLayoutMediator.attach()
    }
}