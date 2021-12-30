package zaroffe.mateusz.nbpdemo.ui.main.exchangeRateDetails

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import zaroffe.mateusz.nbpdemo.R
import zaroffe.mateusz.nbpdemo.network.api.ETableType
import zaroffe.mateusz.nbpdemo.network.responses.interval.ExchangeIntervalResponse
import zaroffe.mateusz.nbpdemo.network.responses.interval.Rate
import zaroffe.mateusz.nbpdemo.ui.main.base.BaseViewModel
import zaroffe.mateusz.nbpdemo.ui.main.formatting.ICurrencyFormatting
import zaroffe.mateusz.nbpdemo.usecase.GetExchangeRatesFromLast2WeeksUseCase
import javax.inject.Inject

class ExchangeRateDetailsViewModel @Inject constructor(
    private val getExchangeRatesFromLast2Weeks: GetExchangeRatesFromLast2WeeksUseCase,
    private val currencyFormatting: ICurrencyFormatting,
    disposable: CompositeDisposable
) : BaseViewModel(disposable) {

    private val _state = MutableLiveData<VMExchangeRateDetailsState>()
    val state: LiveData<VMExchangeRateDetailsState> = _state

    fun loadData(code: String, currency: String, tableType: ETableType) {
        _state.value = VMExchangeRateDetailsState(
            code, currency, emptyList(), true
        )
        getExchangeRatesFromLast2Weeks.execute(code, tableType)
            .map(::toVMExchangeRateDetailsState)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onGetExchangeRatesSuccess) {
                onGetExchangeRatesFailure(
                    code,
                    currency,
                    it
                )
            }
            .toDispose()
    }

    private fun toVMExchangeRateDetailsState(exchangeIntervalResponse: ExchangeIntervalResponse): VMExchangeRateDetailsState {

        val currentMidRate = if (exchangeIntervalResponse.rates.size > 1) {
            exchangeIntervalResponse.rates[0].mid
        } else {
            null
        }

        return VMExchangeRateDetailsState(
            exchangeIntervalResponse.code,
            exchangeIntervalResponse.currency,
            exchangeIntervalResponse.rates.map { rate ->
                DayPrice(
                    rate.effectiveDate,
                    currencyFormatting.format(rate.mid),
                    currentMidRate?.let {
                        shouldWarn(it, rate)
                    } ?: false
                )
            }, false
        )
    }

    private fun onGetExchangeRatesSuccess(state: VMExchangeRateDetailsState) {
        _state.value = state
    }

    private fun shouldWarn(currentMidPrice: Double, rate: Rate): Boolean {
        return rate.mid < WARNING_RATIO_LOWER * currentMidPrice
                || rate.mid > WARNING_RATIO_UPPER * currentMidPrice
    }

    private fun onGetExchangeRatesFailure(code: String, currency: String, throwable: Throwable) {
        _state.value = _state.value?.copy(errorMsg = throwable.message, isLoading = false)
            ?: VMExchangeRateDetailsState(code, currency, emptyList(), false)
    }

    companion object {
        const val WARNING_DELTA = 0.1
        const val WARNING_RATIO_UPPER = 1 + WARNING_DELTA
        const val WARNING_RATIO_LOWER = 1 - WARNING_DELTA

        @JvmStatic
        @BindingAdapter("isWarn")
        fun shouldShowWarnColor(textView: TextView, isWarn: Boolean) {
            textView.setTextColor(
                if (isWarn) {
                    textView.context.getColor(R.color.red)
                } else {
                    textView.context.getColor(R.color.black)
                }
            )

        }
    }
}