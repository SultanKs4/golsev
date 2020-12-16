package id.putraprima.mygoldtracker.screen.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.putraprima.mygoldtracker.data.model.History
import id.putraprima.mygoldtracker.databinding.ItemHistoryBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    var historyList: List<History>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class HistoryViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: History?) {
            binding.history = history
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemHistoryBinding = ItemHistoryBinding.inflate(layoutInflater, parent, false)
        return HistoryViewHolder(itemHistoryBinding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(historyList?.get(position))
    }

    override fun getItemCount(): Int {
        return historyList?.size ?: 0
    }
}