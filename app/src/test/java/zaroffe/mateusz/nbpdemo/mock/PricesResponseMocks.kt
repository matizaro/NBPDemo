package zaroffe.mateusz.nbpdemo.mock

import zaroffe.mateusz.nbpdemo.network.api.ETableType
import zaroffe.mateusz.nbpdemo.network.responses.prices.PricesResponse
import zaroffe.mateusz.nbpdemo.network.responses.prices.PricesTable
import zaroffe.mateusz.nbpdemo.network.responses.prices.Rate

object PricesResponseMocks {

    val rateAAA = Rate(
        "AAA",
        "ATableCurrency",
        0.1
    )

    val aTableAAAResponse: PricesTable
        get() {
            return PricesTable(
                "", "",
                listOf(rateAAA),
                ETableType.A
            )
        }

    val rateAAB = Rate(
        "AAB",
        "ATableCurrency2",
        0.2
    )

    val aTableAABResponse: PricesTable
        get() {
            return PricesTable(
                "", "",
                listOf(rateAAB),
                ETableType.A
            )
        }

    val eTableTypeAResponse: PricesResponse get() {
        return PricesResponse().apply {
            add(aTableAAAResponse)
            add(aTableAABResponse)
        }
    }

    val rateBBB = Rate(
        "BBB",
        "BTableCurrency",
        0.3
    )

    val bTableBBBResponse get() = PricesTable(
        "", "",
        listOf(rateBBB),
        ETableType.B
    )

    val rateBBA = Rate(
        "BBA",
        "BTableCurrency2",
        0.4
    )

    val bTableBBAResponse get() = PricesTable(
        "", "",
        listOf(rateBBA),
        ETableType.B
    )

    val eTableTypeBResponse: PricesResponse get() {
        return PricesResponse().apply {
            add(bTableBBBResponse)
            add(bTableBBAResponse)
        }
    }
}