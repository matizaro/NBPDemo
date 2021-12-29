package zaroffe.mateusz.nbpdemo.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import zaroffe.mateusz.nbpdemo.ui.main.exchangeRateDetails.ExchangeRateDetailsViewModel
import zaroffe.mateusz.nbpdemo.ui.main.exchangeRates.ExchangeRatesViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ExchangeRatesViewModel::class)
    abstract fun bindExchangeRatesViewModel(exchangeRatesViewModel: ExchangeRatesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExchangeRateDetailsViewModel::class)
    abstract fun bindExchangeRateDetailsViewModel(exchangeRateDetailsViewModel: ExchangeRatesViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}