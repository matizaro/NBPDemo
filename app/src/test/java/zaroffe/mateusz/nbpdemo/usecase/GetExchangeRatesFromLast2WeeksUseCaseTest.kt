package zaroffe.mateusz.nbpdemo.usecase

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import org.junit.Before
import org.junit.Test
import zaroffe.mateusz.nbpdemo.base.BaseUnitTest
import zaroffe.mateusz.nbpdemo.mock.ExchangeIntervalResponseMocks
import zaroffe.mateusz.nbpdemo.network.api.ETableType
import zaroffe.mateusz.nbpdemo.network.api.NBPApi
import zaroffe.mateusz.nbpdemo.ui.main.formatting.IRequestDateFormatter
import java.time.OffsetDateTime

class GetExchangeRatesFromLast2WeeksUseCaseTest : BaseUnitTest() {

    @MockK
    private lateinit var api: NBPApi


    private var midnightDateEmitter: BehaviorSubject<OffsetDateTime> = BehaviorSubject.create()

    @MockK
    private lateinit var requestDateFormatter: IRequestDateFormatter

    private lateinit var useCase: GetExchangeRatesFromLast2WeeksUseCase

    @Before
    fun before() {
        useCase = GetExchangeRatesFromLast2WeeksUseCase(
            api,
            requestDateFormatter
        )
    }

    @Test
    fun should_emitExchangeIntervalResponseWithReversedDates_when_GetExchangeRatesFromLast2WeeksUseCaseSuccess() {
        //given
        val exchangeIntervalResponseATable =
            ExchangeIntervalResponseMocks.exchangeIntervalResponseATable
        val now = OffsetDateTime.now()
        val startDate = "startDate"
        val endDate = "endDate"
        every {
            api.getExchangeRatesForInterval(
                ETableType.A,
                exchangeIntervalResponseATable.code,
                startDate,
                endDate
            )
        } returns Single.just(
            exchangeIntervalResponseATable
        )
        midnightDateEmitter.onNext(now)
        every { requestDateFormatter.formatDate(now) } returns endDate
        every { requestDateFormatter.formatDate(now.minusWeeks(2)) } returns startDate
        //when
        val resultS = useCase.execute(exchangeIntervalResponseATable.code, ETableType.A).test()
        //then
        resultS.assertValue(exchangeIntervalResponseATable.copy(rates = exchangeIntervalResponseATable.rates.reversed()))

        verify {
            requestDateFormatter.formatDate(now.minusWeeks(2))
            requestDateFormatter.formatDate(now)
            api.getExchangeRatesForInterval(
                ETableType.A,
                exchangeIntervalResponseATable.code,
                startDate,
                endDate
            )
        }

    }

}