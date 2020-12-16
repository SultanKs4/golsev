package id.putraprima.mygoldtracker.screen.harga

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.shashank.sony.fancytoastlib.FancyToast
import id.putraprima.mygoldtracker.GoldsApplication
import id.putraprima.mygoldtracker.data.model.History
import id.putraprima.mygoldtracker.data.model.Price
import id.putraprima.mygoldtracker.util.Currency
import id.putraprima.mygoldtracker.viewmodel.HargaViewModel
import id.putraprima.mygoldtracker.viewmodel.HargaViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class BuyDialogFragment(private val price: Price) : DialogFragment() {
    private val viewModel: HargaViewModel by viewModels {
        HargaViewModelFactory((activity?.application as GoldsApplication).repository)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Yakin ingin membeli emas ${price.weight} gr dengan harga ${Currency.rupiah(price.buy)}")
                    .setPositiveButton("Ya", DialogInterface.OnClickListener { dialogInterface, i ->
                        val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                        val dateObj = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(currentTime)
                        viewModel.insert(History(
                                date = dateObj,
                                weight = price.weight,
                                price = price.buy
                        ))
                        FancyToast.makeText(it, "Emas berhasil dibeli!", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show()
                    })
                    .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialogInterface, i ->
                        dialog?.cancel()
                        FancyToast.makeText(it, "Pembelian telah dibatalkan.", FancyToast.LENGTH_LONG, FancyToast.INFO, true).show()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}