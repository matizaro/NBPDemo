package zaroffe.mateusz.nbpdemo.ui.main.exchangeRates

import androidx.navigation.NavController
import zaroffe.mateusz.nbpdemo.R
import zaroffe.mateusz.nbpdemo.network.api.ETableType
import javax.inject.Inject
import javax.inject.Provider

class ExchangeRatesNavigator constructor(
    private val navController: Provider<NavController>
    ) {

    fun navigateTo(destination: ExchangeRatesDestinations) {
        (destination as? ExchangeRatesDestinations.Details)?.let {
            val directions = ExchangeRatesFragmentDirections
                .actionExchangeRatesFragmentToExchangeRateDetailsFragment(
                    it.code,
                    it.currency,
                    it.tableType)
            navController.get().navigate(directions)
        }
    }

    sealed class ExchangeRatesDestinations {
        data class Details(
            val code: String,
            val currency: String,
            val tableType: ETableType
        ):ExchangeRatesDestinations()
    }
}