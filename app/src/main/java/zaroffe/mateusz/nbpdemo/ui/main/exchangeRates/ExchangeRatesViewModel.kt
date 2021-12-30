package zaroffe.mateusz.nbpdemo.ui.main.exchangeRates

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import zaroffe.mateusz.nbpdemo.network.api.ETableType
import zaroffe.mateusz.nbpdemo.network.responses.prices.Rate
import zaroffe.mateusz.nbpdemo.ui.main.base.BaseViewModel
import zaroffe.mateusz.nbpdemo.ui.main.formatting.ICurrencyFormatting
import zaroffe.mateusz.nbpdemo.usecase.GetMidPricesUseCase
import javax.inject.Inject

class ExchangeRatesViewModel @Inject constructor(
    private val getMidPricesUseCase: GetMidPricesUseCase,
    private val navigator: ExchangeRatesNavigator,
    private val currencyFormatting: ICurrencyFormatting,
    disposable: CompositeDisposable
): BaseViewModel(disposable) {

    private val _state = MutableLiveData<VMExchangeRatesState>()

    val state: LiveData<VMExchangeRatesState> = _state

    fun refreshData() {
        _state.value = VMExchangeRatesState(_state.value?.exchangeRates?: emptyList(), false, true)
        executeUseCase()
    }

    fun loadData() {
        _state.value = VMExchangeRatesState(_state.value?.exchangeRates?: emptyList(), true, false)
        executeUseCase()
    }

    private fun executeUseCase() {
        getMidPricesUseCase
            .execute()
            .map (::toExchangeRatesState)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onGetMidPricesSuccess, ::onGetMidPricesError)
            .toDispose()
    }

    private fun toExchangeRatesState(rateWithETableType: List<Pair<Rate, ETableType>>) =
        VMExchangeRatesState(
            rateWithETableType.map { (rate, tableType) ->
                Currency(
                    rate.code,
                    rate.currency,
                    currencyFormatting.format(rate.mid)
                ) { currencyPressed(rate.code, rate.currency, tableType) }
            },
            false,
            false
        )

    private fun onGetMidPricesSuccess(state: VMExchangeRatesState) {
        _state.value = state
    }

    @VisibleForTesting
    fun currencyPressed(code: String, currency: String, tableType: ETableType) {
        navigator.navigateTo(ExchangeRatesNavigator.ExchangeRatesDestinations.Details(
            code, currency, tableType
        ))
    }

    private fun onGetMidPricesError(throwable: Throwable) {
        _state.value = VMExchangeRatesState(
            emptyList(),
            false,
            false,
            throwable.message
        )
    }

}