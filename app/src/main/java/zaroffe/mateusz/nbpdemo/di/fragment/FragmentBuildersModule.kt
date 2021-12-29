package zaroffe.mateusz.nbpdemo.di.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector
import zaroffe.mateusz.nbpdemo.ui.main.exchangeRateDetails.ExchangeRateDetailsFragment
import zaroffe.mateusz.nbpdemo.ui.main.exchangeRates.ExchangeRatesFragment

@Module
abstract class FragmentBuildersModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [FragmentModule::class, ExchangeRatesFragmentModule::class])
    abstract fun contributesExchangeRatesFragment(): ExchangeRatesFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributesExchangeRateDetailsFragment(): ExchangeRateDetailsFragment

}