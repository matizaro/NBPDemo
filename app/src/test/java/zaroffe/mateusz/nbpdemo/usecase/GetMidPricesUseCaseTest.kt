package zaroffe.mateusz.nbpdemo.usecase

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import zaroffe.mateusz.nbpdemo.base.BaseUnitTest
import zaroffe.mateusz.nbpdemo.mock.PricesResponseMocks.eTableTypeAResponse
import zaroffe.mateusz.nbpdemo.mock.PricesResponseMocks.eTableTypeBResponse
import zaroffe.mateusz.nbpdemo.network.api.ETableType
import zaroffe.mateusz.nbpdemo.network.api.NBPApi


class GetMidPricesUseCaseTest : BaseUnitTest() {

    @MockK
    private lateinit var nbpApi: NBPApi

    private lateinit var useCase: GetMidPricesUseCase

    @Before
    fun before() {
        useCase = GetMidPricesUseCase(nbpApi)
    }

    @Test
    fun should_emit2ListsMergedAndSorted_when_getCurrentMidPriceForTableTypeSuccess() {
        //given
        val aResponse = eTableTypeAResponse
        every { nbpApi.getCurrentMidPriceForTableType(ETableType.A) } returns Single.just(
            aResponse
        )
        val bResponse = eTableTypeBResponse
        every { nbpApi.getCurrentMidPriceForTableType(ETableType.B) } returns Single.just(
            bResponse
        )
        //when
        val resultS = useCase.execute().test()
        //then
        resultS.assertValue(
            listOf(
                Pair(aResponse[0].rates[0], ETableType.A),
                Pair(aResponse[1].rates[0], ETableType.A),
                Pair(bResponse[1].rates[0], ETableType.B),
                Pair(bResponse[0].rates[0], ETableType.B)
            )
        )
        verify {
            nbpApi.getCurrentMidPriceForTableType(ETableType.A)
            nbpApi.getCurrentMidPriceForTableType(ETableType.B)
        }
    }

    @Test
    fun should_emitError_when_atLeastOneEmitsError() {
        //given
        val aResponse = eTableTypeAResponse
        every { nbpApi.getCurrentMidPriceForTableType(ETableType.A) } returns Single.just(
            aResponse
        )
        val throwable = Throwable("test error")
        every { nbpApi.getCurrentMidPriceForTableType(ETableType.B) } returns Single.error(
            throwable
        )
        //when
        val testObserver = useCase.execute().test()
        //then
        testObserver.assertError(throwable)

        verify {
            nbpApi.getCurrentMidPriceForTableType(ETableType.A)
            nbpApi.getCurrentMidPriceForTableType(ETableType.B)
        }
    }

}
