package zaroffe.mateusz.nbpdemo.network.api

import io.reactivex.Single
import retrofit2.http.*
import zaroffe.mateusz.nbpdemo.network.responses.interval.ExchangeIntervalResponse
import zaroffe.mateusz.nbpdemo.network.responses.prices.PricesResponse

interface NBPApi {

    @GET("exchangerates/tables/{table}/")
    @Headers("Accept: application/json")
    fun getCurrentMidPriceForTableType(@Path("table") table: ETableType): Single<PricesResponse>

    @GET("exchangerates/rates/{table}/{code}/{startDate}/{endDate}/")
    @Headers("Accept: application/json")
    fun getExchangeRatesForInterval(
        @Path("table") table: ETableType,
        @Path("code") code: String,
        @Path("startDate") startDate: String,
        @Path("endDate") endDate: String
    ): Single<ExchangeIntervalResponse>
}

object NBPApiConst {

    const val HTTP_ADDRESS = "https://api.nbp.pl/api/"

}