package zaroffe.mateusz.nbpdemo.ui.main.exchangeRateDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import zaroffe.mateusz.nbpdemo.databinding.ItemCurrencyDayPriceBinding
import zaroffe.mateusz.nbpdemo.ui.main.dataBinding.RecyclerUtils

class DayPricesAdapter(items: List<DayPrice>)
    : RecyclerUtils.UpdatableAdapter<DayPricesAdapter.DayPriceViewHolder, DayPrice>() {

    class DayPriceViewHolder(
        private val itemBinding: ItemCurrencyDayPriceBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(dayPrice: DayPrice) {
            itemBinding.itemCurrencyDayPriceVM = dayPrice
        }
    }

    private val dayPrices: MutableList<DayPrice> = items.toMutableList()

    override fun setData(newList: List<DayPrice>) {
        val callback = DayPricesDiffCallback(dayPrices, newList)
        val diffResult = DiffUtil.calculateDiff(callback)
        dayPrices.clear()
        dayPrices.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayPriceViewHolder {
        return DayPriceViewHolder(
            ItemCurrencyDayPriceBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false))
    }

    override fun onBindViewHolder(holder: DayPriceViewHolder, position: Int) {
        holder.bind(dayPrices[position])
    }

    override fun getItemCount() = dayPrices.size

}