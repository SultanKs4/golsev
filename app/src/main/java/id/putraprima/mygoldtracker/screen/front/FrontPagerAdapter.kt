package id.putraprima.mygoldtracker.screen.front

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.putraprima.mygoldtracker.screen.harga.HargaFragment
import id.putraprima.mygoldtracker.screen.history.HistoryFragment
import id.putraprima.mygoldtracker.screen.portolio.PorfolioFragment
import id.putraprima.mygoldtracker.screen.profile.ProfileFragment


class FrontPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PorfolioFragment()
            1 -> HargaFragment()
            2 -> HistoryFragment()
            else -> throw Exception()
        }
    }

}