package zaroffe.mateusz.nbpdemo.di.fragment

import androidx.navigation.NavController
import dagger.Module
import dagger.Provides
import zaroffe.mateusz.nbpdemo.ui.main.exchangeRates.ExchangeRatesNavigator
import javax.inject.Provider

@Module
class ExchangeRatesFragmentModule {
    @Provides
    @PerFragment
    fun provideExchangeRatesNavigator(navController: Provider<NavController>) = ExchangeRatesNavigator(navController)
}
