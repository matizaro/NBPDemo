package zaroffe.mateusz.nbpdemo.usecase

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import zaroffe.mateusz.nbpdemo.network.api.ETableType
import zaroffe.mateusz.nbpdemo.network.api.NBPApi
import zaroffe.mateusz.nbpdemo.ui.main.formatting.IRequestDateFormatter
import java.time.OffsetDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetExchangeRatesFromLast2WeeksUseCase @Inject constructor(
    private val api: NBPApi,
    private val requestDateFormatter: IRequestDateFormatter
) {
    fun execute(code: String, tableType: ETableType) =
        Single.just(OffsetDateTime.now()).flatMap {
            val today = requestDateFormatter.formatDate(it)
            val twoWeeksAgo = requestDateFormatter.formatDate(it.minusWeeks(2))
            api.getExchangeRatesForInterval(tableType, code, twoWeeksAgo, today)
        }.map {
            it.copy(rates = it.rates.reversed())
        }.subscribeOn(Schedulers.io())

}