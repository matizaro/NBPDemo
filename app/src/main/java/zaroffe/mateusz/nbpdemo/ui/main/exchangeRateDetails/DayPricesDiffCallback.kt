package zaroffe.mateusz.nbpdemo.ui.main.exchangeRateDetails

import androidx.recyclerview.widget.DiffUtil

class DayPricesDiffCallback(
    private val oldList: List<DayPrice>,
    private val newList: List<DayPrice>
    ) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}