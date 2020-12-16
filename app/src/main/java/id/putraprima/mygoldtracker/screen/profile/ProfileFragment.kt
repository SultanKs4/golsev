package id.putraprima.mygoldtracker.screen.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import id.putraprima.mygoldtracker.GoldsApplication
import id.putraprima.mygoldtracker.R
import id.putraprima.mygoldtracker.data.model.Profile
import id.putraprima.mygoldtracker.databinding.FragmentProfileBinding
import id.putraprima.mygoldtracker.util.ImageHelper
import id.putraprima.mygoldtracker.viewmodel.ProfileViewModel
import id.putraprima.mygoldtracker.viewmodel.ProfileViewModelFactory


class ProfileFragment : Fragment() {
    val args: ProfileFragmentArgs by navArgs()
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory((activity?.application as GoldsApplication).repository)
    }
    val pickImages = registerForActivityResult(GetContent()) {
        it?.let { uri ->
            lifecycleScope.launchWhenStarted {
                binding.imageProfile.setImageURI(uri)
                viewModel.tmpUri(uri)
            }
        }
    }
    private var uriPreiew: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.imageProfile.setImageResource(R.drawable.default_profile)
        binding.imageLayout.setOnClickListener {
            pickImages.launch("image/*")
        }
        binding.fabCamera.setOnClickListener {
            pickImages.launch("image/*")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.profileTmpUri.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.imageProfile.setImageURI(it)
                uriPreiew = it
            }
        })
        viewModel.profileNew.observe(viewLifecycleOwner, {
            if (it != null) {
                if (args.saveType == "insert") {
                    viewModel.insert(it)
                    findNavController().navigate(R.id.action_profileFragment_to_frontFragment)
                } else if (args.saveType == "update") {
                    val profile = Profile(id = 1, nama = it.nama, email = it.email, path = it.path)
                    viewModel.update(profile)
                    findNavController().navigateUp()
                }
            }
        })
        viewModel.profile.observe(viewLifecycleOwner, { profile ->
            if (!profile.isNullOrEmpty()) {
                if (uriPreiew == null) {
                    lifecycleScope.launchWhenResumed {
                        val bitmap = ImageHelper.loadImageFromStorage(profile[0].path)
                        binding.imageProfile.setImageBitmap(bitmap)
                    }
                }
                binding.nama = profile[0].nama
                binding.email = profile[0].email
            }
        })
    }
}