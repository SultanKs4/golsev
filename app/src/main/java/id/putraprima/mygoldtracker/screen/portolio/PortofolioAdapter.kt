package id.putraprima.mygoldtracker.screen.portolio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.putraprima.mygoldtracker.data.model.Portofolio
import id.putraprima.mygoldtracker.databinding.ItemPortofolioBinding

class PortofolioAdapter : RecyclerView.Adapter<PortofolioAdapter.PortofolioViewHolder>() {
    var portofolioList: List<Portofolio>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class PortofolioViewHolder(private val binding: ItemPortofolioBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(portofolio: Portofolio?) {
            if (portofolio != null) {
                binding.portofolio = portofolio
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortofolioViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemPortofolioBinding = ItemPortofolioBinding.inflate(layoutInflater, parent, false)
        return PortofolioViewHolder(itemPortofolioBinding)
    }

    override fun onBindViewHolder(holder: PortofolioViewHolder, position: Int) {
        holder.bind(portofolioList?.get(position))
    }

    override fun getItemCount(): Int {
        return portofolioList?.size ?: 0
    }
}