package id.putraprima.mygoldtracker.screen.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import id.putraprima.mygoldtracker.GoldsApplication
import id.putraprima.mygoldtracker.R
import id.putraprima.mygoldtracker.data.model.Profile
import id.putraprima.mygoldtracker.databinding.FragmentSplashBinding
import id.putraprima.mygoldtracker.viewmodel.SplashViewModel
import id.putraprima.mygoldtracker.viewmodel.SplashViewModelFactory

class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    private val SPLASH_DELAY: Long = 750 //2 seconds
    private var mDelayHandler: Handler? = null
    private var progressBarStatus = 0
    var dummy: Int = 0
    private val viewModel: SplashViewModel by viewModels {
        SplashViewModelFactory((activity?.application as GoldsApplication).repository)
    }
    lateinit var profile: List<Profile>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        binding.lifecycleOwner = this
        Handler(Looper.getMainLooper()).postDelayed(mRunnable, SPLASH_DELAY)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.profile.observe(viewLifecycleOwner, {
            profile = it
        })
        Handler(Looper.getMainLooper()).postDelayed({
            if (profile.isNullOrEmpty()) {
                val action = SplashFragmentDirections.actionSplashFragmentToProfileFragment("insert")
                findNavController().navigate(action)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_frontFragment)
            }
        }, 1000)
    }

    private val mRunnable: Runnable = Runnable {
        Thread(Runnable {
            while (progressBarStatus < 100) {
                try {
                    dummy = dummy + 25
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                //tracking progress
                progressBarStatus = dummy

                //update progress bar
                binding.progressBar.progress = progressBarStatus
            }
        }).start()
    }

    override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
}