package zaroffe.mateusz.nbpdemo.ui.main.exchangeRates

import zaroffe.mateusz.nbpdemo.ui.main.base.error.IErrorState

data class VMExchangeRatesState(
    val exchangeRates: List<Currency>,
    val isLoading: Boolean,
    val isRefreshing: Boolean,
    override val errorMsg: String? = null
): IErrorState {
    val hasExchangeRates get() = exchangeRates.isNotEmpty()
}

data class Currency(
    val symbol: String,
    val fullName: String,
    val midPrice: String,
    val onCurrencyPressed: () -> Unit
)