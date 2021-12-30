package zaroffe.mateusz.nbpdemo.mock

import zaroffe.mateusz.nbpdemo.network.api.ETableType
import zaroffe.mateusz.nbpdemo.network.responses.interval.ExchangeIntervalResponse
import zaroffe.mateusz.nbpdemo.network.responses.interval.Rate

object ExchangeIntervalResponseMocks {
    val rate1
        get() = Rate(
            "fakeEffectiveDate1",
            0.01,
            "fakeNo"
        )

    val rate2
        get() = Rate(
            "fakeEffectiveDate2",
            0.02,
            "fakeNo2"
        )

    val rate3
        get() = Rate(
            "fakeEffectiveDate3",
            0.03,
            "fakeNo3"
        )

    val exchangeIntervalResponseATable: ExchangeIntervalResponse
        get() {
            return ExchangeIntervalResponse(
                "AAA",
                "fakeCurrency",
                listOf(rate1, rate2, rate3), ETableType.A
            )
        }
}