package zaroffe.mateusz.nbpdemo.ui.main.exchangeRates

import androidx.recyclerview.widget.DiffUtil

class CurrencyDiffCallback(
    private val oldList: List<Currency>,
    private val newList: List<Currency>
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