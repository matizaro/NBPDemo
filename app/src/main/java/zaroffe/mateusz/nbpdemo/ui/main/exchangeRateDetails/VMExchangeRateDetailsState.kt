package zaroffe.mateusz.nbpdemo.ui.main.exchangeRateDetails

import zaroffe.mateusz.nbpdemo.ui.main.base.error.IErrorState

data class VMExchangeRateDetailsState(
    val code: String,
    val currency: String,
    val pricesTimeline: List<DayPrice>,
    val isLoading: Boolean,
    override val errorMsg: String? = null
):IErrorState

data class DayPrice(
    val date: String,
    val price: String,
    val isWarn: Boolean
)