package zaroffe.mateusz.nbpdemo.usecase

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import zaroffe.mateusz.nbpdemo.network.api.ETableType
import zaroffe.mateusz.nbpdemo.network.api.NBPApi
import zaroffe.mateusz.nbpdemo.network.responses.prices.PricesResponse
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetMidPricesUseCase @Inject constructor(private val api: NBPApi) {

    fun execute() = Single.zip(
        api.getCurrentMidPriceForTableType(ETableType.A),
        api.getCurrentMidPriceForTableType(ETableType.B)) {
        aTable, bTable ->
        (aTable.ratesWithTableType() +
                bTable.ratesWithTableType())
            .sortedBy { it.first.code }
    }.subscribeOn(Schedulers.io())

    private fun PricesResponse.ratesWithTableType() =
        map { table -> table.rates.map { it to table.table } }.flatten()

}