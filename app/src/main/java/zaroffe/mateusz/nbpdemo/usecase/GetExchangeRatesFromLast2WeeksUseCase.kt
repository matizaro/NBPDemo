package zaroffe.mateusz.nbpdemo.usecase

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.internal.connection.Exchange
import zaroffe.mateusz.nbpdemo.R
import zaroffe.mateusz.nbpdemo.app.ResourcesProviderWrapper
import zaroffe.mateusz.nbpdemo.network.api.ETableType
import zaroffe.mateusz.nbpdemo.network.api.NBPApi
import zaroffe.mateusz.nbpdemo.network.responses.interval.ExchangeIntervalResponse
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetExchangeRatesFromLast2WeeksUseCase @Inject constructor(
    private val api: NBPApi,
    private val midnightDateEmitter: Observable<OffsetDateTime>,
    private val resourcesProviderWrapper: ResourcesProviderWrapper
) {
    fun execute(code: String, tableType: ETableType) =
        midnightDateEmitter.flatMapSingle {
            val today = it.formatDate()
            val twoWeeksAgo = it.minusWeeks(2).formatDate()
            api.getExchangeRatesForInterval(tableType, code, twoWeeksAgo, today)
        }.map {
            ExchangeIntervalResponse(
                it.code,
                it.currency,
                it.rates.reversed(),
                it.table
            )
        }.subscribeOn(Schedulers.newThread())

    private fun OffsetDateTime.formatDate() = this.format(DateTimeFormatter.ofPattern(resourcesProviderWrapper.getString(R.string.request_date_formatter)))

}