package id.putraprima.mygoldtracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.fragment2)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            title = navController.currentDestination?.label
        }
    }
}