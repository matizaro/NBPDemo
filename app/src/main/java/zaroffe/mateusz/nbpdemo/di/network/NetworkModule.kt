package zaroffe.mateusz.nbpdemo.di.network

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import zaroffe.mateusz.nbpdemo.network.api.NBPApi
import zaroffe.mateusz.nbpdemo.network.api.NBPApiConst
import javax.inject.Provider
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun providesNBPApi() = Retrofit
        .Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(NBPApiConst.HTTP_ADDRESS)
        .build()
        .create(NBPApi::class.java)

}