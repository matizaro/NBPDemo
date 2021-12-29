package zaroffe.mateusz.nbpdemo.usecase

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import zaroffe.mateusz.nbpdemo.network.api.ETableType
import zaroffe.mateusz.nbpdemo.network.api.NBPApi
import zaroffe.mateusz.nbpdemo.network.responses.prices.PricesResponse
import javax.inject.Inject

class GetMidPricesUseCase @Inject constructor(private val api: NBPApi) {

    fun execute() = Single.zip(
        api.getCurrentMidPriceForTableType(ETableType.A),
        api.getCurrentMidPriceForTableType(ETableType.B)) {
        aTable, bTable ->
        (aTable.ratesWithTableType(ETableType.A) +
                bTable.ratesWithTableType(ETableType.B))
            .sortedBy { it.first.code }
    }.subscribeOn(Schedulers.newThread())

    private fun PricesResponse.ratesWithTableType(tableType: ETableType) =
        map { it.rates }.flatten().map { it to tableType }

}