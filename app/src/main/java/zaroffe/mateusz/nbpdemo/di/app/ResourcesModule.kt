package zaroffe.mateusz.nbpdemo.di.app

import dagger.Binds
import dagger.Module
import dagger.Provides
import zaroffe.mateusz.nbpdemo.app.NBPApplication
import zaroffe.mateusz.nbpdemo.app.ResourcesProviderWrapper
import zaroffe.mateusz.nbpdemo.ui.main.formatting.ICurrencyFormatting
import zaroffe.mateusz.nbpdemo.ui.main.formatting.IRequestDateFormatter
import zaroffe.mateusz.nbpdemo.ui.main.formatting.PLNCurrencyFormatting
import zaroffe.mateusz.nbpdemo.ui.main.formatting.RequestDateFormatter
import javax.inject.Singleton

@Module(includes = [ResourcesModule.BindsModule::class])
class ResourcesModule {

    @Provides
    @Singleton
    fun provideResourcesProvider(app: NBPApplication) = ResourcesProviderWrapper(app)

    @Module
    interface BindsModule {

        @Binds
        abstract fun bindsPLNCurrencyFormatting(formatting: PLNCurrencyFormatting): ICurrencyFormatting

        @Binds
        abstract fun bindsIRequestDateFormatting(formatting: RequestDateFormatter): IRequestDateFormatter

    }
}