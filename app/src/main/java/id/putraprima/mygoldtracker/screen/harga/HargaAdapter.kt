package id.putraprima.mygoldtracker.screen.harga

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.putraprima.mygoldtracker.data.model.Price
import id.putraprima.mygoldtracker.databinding.ItemHargaBinding

class HargaAdapter(private val onItemHargaListener: OnItemHargaListener) : RecyclerView.Adapter<HargaAdapter.HargaViewHolder>() {
    var priceList: List<Price>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class HargaViewHolder(private val binding: ItemHargaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(price: Price?, itemHargaListener: OnItemHargaListener) {
            binding.price = price
            binding.clickListener = itemHargaListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HargaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemHargaBinding = ItemHargaBinding.inflate(layoutInflater, parent, false)
        return HargaViewHolder(itemHargaBinding)
    }

    override fun onBindViewHolder(holder: HargaViewHolder, position: Int) {
        holder.bind(priceList?.get(position), onItemHargaListener)
    }

    override fun getItemCount(): Int {
        return priceList?.size ?: 0
    }
}