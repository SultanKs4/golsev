package id.putraprima.mygoldtracker.screen.harga

import id.putraprima.mygoldtracker.data.model.Price

interface OnItemHargaListener {
    fun onHargaClicked(price: Price)
}