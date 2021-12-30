package zaroffe.mateusz.nbpdemo.viewmodel

import com.jraska.livedata.test
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Test
import zaroffe.mateusz.nbpdemo.base.BaseUnitTest
import zaroffe.mateusz.nbpdemo.mock.ExchangeIntervalResponseMocks
import zaroffe.mateusz.nbpdemo.mock.ExchangeIntervalResponseMocks.rate1
import zaroffe.mateusz.nbpdemo.mock.ExchangeIntervalResponseMocks.rate2
import zaroffe.mateusz.nbpdemo.mock.ExchangeIntervalResponseMocks.rate3
import zaroffe.mateusz.nbpdemo.network.api.ETableType
import zaroffe.mateusz.nbpdemo.ui.main.exchangeRateDetails.DayPrice
import zaroffe.mateusz.nbpdemo.ui.main.exchangeRateDetails.ExchangeRateDetailsViewModel
import zaroffe.mateusz.nbpdemo.ui.main.exchangeRateDetails.VMExchangeRateDetailsState
import zaroffe.mateusz.nbpdemo.ui.main.formatting.ICurrencyFormatting
import zaroffe.mateusz.nbpdemo.ui.main.formatting.PLNCurrencyFormatting
import zaroffe.mateusz.nbpdemo.usecase.GetExchangeRatesFromLast2WeeksUseCase

class ExchangeRateDetailsViewModelTest : BaseUnitTest() {
    @MockK
    private lateinit var getExchangeRatesFromLast2WeeksUseCase: GetExchangeRatesFromLast2WeeksUseCase

    @SpyK
    private var currencyFormatting: ICurrencyFormatting = PLNCurrencyFormatting()

    @SpyK
    private var disposable: CompositeDisposable = CompositeDisposable()


    private lateinit var viewModel: ExchangeRateDetailsViewModel

    @Before
    fun before() {
        viewModel = ExchangeRateDetailsViewModel(
            getExchangeRatesFromLast2WeeksUseCase,
            currencyFormatting,
            disposable
        )
    }

    @Test
    fun should_isLoadingTrue_when_loadData() {
        //given
        val code = "mockCode"
        val currency = "mockCurrency"
        val testObserver = viewModel.state.test()
        every {
            getExchangeRatesFromLast2WeeksUseCase.execute(
                code,
                ETableType.A
            )
        } returns Single.never()
        //when
        viewModel.loadData(code, currency, ETableType.A)
        //then
        testObserver.assertValue(VMExchangeRateDetailsState(code, currency, emptyList(), true))
        testObserver.assertHistorySize(1)
        verify {
            getExchangeRatesFromLast2WeeksUseCase.execute(code, ETableType.A)
            disposable.add(any())
        }
    }


    @Test
    fun should_isWarnTrue_whenGetExchangeRatesFromLast2WeeksUseCaseSuccessWithBigPriceDifference() {
        //given
        val exchangeIntervalResponseATable =
            ExchangeIntervalResponseMocks.exchangeIntervalResponseATable
        val testObserver = viewModel.state.test()
        every {
            getExchangeRatesFromLast2WeeksUseCase.execute(
                exchangeIntervalResponseATable.code,
                exchangeIntervalResponseATable.table
            )
        } returns Single.just(exchangeIntervalResponseATable)
        //when
        viewModel.loadData(
            exchangeIntervalResponseATable.code,
            exchangeIntervalResponseATable.currency,
            exchangeIntervalResponseATable.table
        )
        //then
        testObserver.assertValue(
            VMExchangeRateDetailsState(
                exchangeIntervalResponseATable.code,
                exchangeIntervalResponseATable.currency,
                listOf(
                    DayPrice(rate1.effectiveDate, currencyFormatting.format(rate1.mid), false),
                    DayPrice(rate2.effectiveDate, currencyFormatting.format(rate2.mid), true),
                    DayPrice(rate3.effectiveDate, currencyFormatting.format(rate3.mid), true)
                ),
                false
            )
        )
        testObserver.assertHistorySize(2)
        verify {
            getExchangeRatesFromLast2WeeksUseCase.execute(
                exchangeIntervalResponseATable.code,
                exchangeIntervalResponseATable.table
            )
            currencyFormatting.format(any())
            disposable.add(any())
        }
    }

    @Test
    fun should_errorMsg_whenGetExchangeRatesFromLast2WeeksUseCaseFailure() {
        //given
        val code = "mockCode"
        val currency = "mockCurrency"
        val testObserver = viewModel.state.test()
        val tableType = ETableType.A
        val throwable = Throwable("fake message")
        every {
            getExchangeRatesFromLast2WeeksUseCase.execute(
                code,
                tableType
            )
        } returns Single.error(throwable)
        //when
        viewModel.loadData(code, currency, tableType)
        //then
        testObserver.assertValue(
            VMExchangeRateDetailsState(
                code,
                currency,
                emptyList(),
                false,
                throwable.message
            )
        )
        testObserver.assertHistorySize(2)
        verify {
            getExchangeRatesFromLast2WeeksUseCase.execute(code, tableType)
            disposable.add(any())
        }
    }

}