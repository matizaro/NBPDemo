package zaroffe.mateusz.nbpdemo.ui.main.exchangeRates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import zaroffe.mateusz.nbpdemo.databinding.ItemCurrencyMainBinding
import zaroffe.mateusz.nbpdemo.ui.main.dataBinding.RecyclerUtils

class ExchangeRatesAdapter(items: List<Currency>)
    : RecyclerUtils.UpdatableAdapter<ExchangeRatesAdapter.CurrencyViewHolder, Currency>() {

    private val currencies: MutableList<Currency> = items.toMutableList()

    class CurrencyViewHolder(
        private val itemBinding: ItemCurrencyMainBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(currency: Currency) {
            itemBinding.itemCurrencyMainVM = currency
        }
    }

    override fun setData(newList: List<Currency>) {
        val callback = CurrencyDiffCallback(currencies, newList)
        val diffResult = DiffUtil.calculateDiff(callback)
        currencies.clear()
        currencies.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(
            ItemCurrencyMainBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false))
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(currencies[position])
    }

    override fun getItemCount() = currencies.size

}