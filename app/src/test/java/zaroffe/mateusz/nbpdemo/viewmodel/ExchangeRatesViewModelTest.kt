package zaroffe.mateusz.nbpdemo.viewmodel

import com.jraska.livedata.test
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Test
import zaroffe.mateusz.nbpdemo.MockitoHelper.mock
import zaroffe.mateusz.nbpdemo.base.BaseUnitTest
import zaroffe.mateusz.nbpdemo.mock.PricesResponseMocks.rateAAA
import zaroffe.mateusz.nbpdemo.network.api.ETableType
import zaroffe.mateusz.nbpdemo.ui.main.exchangeRates.Currency
import zaroffe.mateusz.nbpdemo.ui.main.exchangeRates.ExchangeRatesNavigator
import zaroffe.mateusz.nbpdemo.ui.main.exchangeRates.ExchangeRatesViewModel
import zaroffe.mateusz.nbpdemo.ui.main.exchangeRates.VMExchangeRatesState
import zaroffe.mateusz.nbpdemo.ui.main.formatting.ICurrencyFormatting
import zaroffe.mateusz.nbpdemo.ui.main.formatting.PLNCurrencyFormatting
import zaroffe.mateusz.nbpdemo.usecase.GetMidPricesUseCase

class ExchangeRatesViewModelTest : BaseUnitTest() {

    @MockK
    private lateinit var getMidPricesUseCase: GetMidPricesUseCase

    @MockK(relaxed = true)
    private lateinit var navigator: ExchangeRatesNavigator

    @SpyK
    private var currencyFormatting: ICurrencyFormatting = PLNCurrencyFormatting()

    private lateinit var disposable: CompositeDisposable

    private lateinit var viewModel: ExchangeRatesViewModel

    @Before
    fun before() {
        disposable = spyk(CompositeDisposable())
        viewModel = ExchangeRatesViewModel(
            getMidPricesUseCase,
            navigator,
            currencyFormatting,
            disposable
        )
    }

    @Test
    fun should_isLoadingTrue_when_loadData() {
        //given
        val testObserver = viewModel.state.test()
        every { getMidPricesUseCase.execute() } returns Single.never()
        //when
        viewModel.loadData()
        //then
        testObserver.assertValue(VMExchangeRatesState(emptyList(), true, false))
        testObserver.assertHistorySize(1)
        verify {
            getMidPricesUseCase.execute()
            disposable.add(any())
        }
    }

    @Test
    fun should_isRefreshingTrue_when_refreshData() {
        //given
        val testObserver = viewModel.state.test()
        every { getMidPricesUseCase.execute() } returns Single.never()
        //when
        viewModel.refreshData()
        //then
        testObserver.assertValue(VMExchangeRatesState(emptyList(), false, true))
        testObserver.assertHistorySize(1)
        verify {
            getMidPricesUseCase.execute()
            disposable.add(any())
        }
    }

    @Test
    fun should_returnCurrencyDataAndIsLoadingFalse_when_getMidPricesUseCaseSuccess() {
        //given
        val testObserver = viewModel.state.test()
        val rateA = rateAAA
        every { getMidPricesUseCase.execute() } returns Single.just(
            listOf(
                Pair(rateA, ETableType.A)
            )
        )
        //when
        viewModel.loadData()
        //then
        testObserver.assertValue(
            VMExchangeRatesState(
                listOf(
                    Currency(
                        rateA.code,
                        rateA.currency,
                        currencyFormatting.format(rateA.mid),
                        mock()
                    )
                ), false, false
            )
        )
        testObserver.assertHistorySize(2)
        verify {
            getMidPricesUseCase.execute()
            currencyFormatting.format(any())
            disposable.add(any())
        }
    }

    @Test
    fun should_returnCurrencyDataAndIsLoadingFalse_when_RefreshDataGetMidPricesUseCaseSuccess() {
        //given
        val testObserver = viewModel.state.test()
        val rateA = rateAAA
        every { getMidPricesUseCase.execute() } returns Single.just(
            listOf(
                Pair(rateA, ETableType.A)
            )
        )
        //when
        viewModel.refreshData()
        //then
        testObserver.assertValue(
            VMExchangeRatesState(
                listOf(
                    Currency(
                        rateA.code,
                        rateA.currency,
                        currencyFormatting.format(rateA.mid),
                        mock()
                    )
                ), false, false
            )
        )
        testObserver.assertHistorySize(2)
        verify {
            getMidPricesUseCase.execute()
            currencyFormatting.format(any())
            disposable.add(any())
        }
    }

    @Test
    fun should_returnErrorMsgAndIsLoadingFalse_when_getMidPricesUseCaseError() {
        //given
        val testObserver = viewModel.state.test()
        val throwable = Throwable("fake error")
        every { getMidPricesUseCase.execute() } returns Single.error(throwable)
        //when
        viewModel.loadData()
        //then
        testObserver.assertValue(VMExchangeRatesState(emptyList(), false, false, throwable.message))
        testObserver.assertHistorySize(2)
        verify {
            getMidPricesUseCase.execute()
            disposable.add(any())
        }
    }

    @Test
    fun should_returnErrorMsgAndIsLoadingFalse_when_RefreshDataGetMidPricesUseCaseError() {
        //given
        val testObserver = viewModel.state.test()
        val throwable = Throwable("fake error")
        every { getMidPricesUseCase.execute() } returns Single.error(throwable)
        //when
        viewModel.refreshData()
        //then
        testObserver.assertValue(VMExchangeRatesState(emptyList(), false, false, throwable.message))
        testObserver.assertHistorySize(2)
        verify {
            getMidPricesUseCase.execute()
            disposable.add(any())
        }
    }

    @Test
    fun should_navigate_when_currencyClicked() {
        //given
        val testObserver = viewModel.state.test()
        val rateA = rateAAA
        every { getMidPricesUseCase.execute() } returns Single.just(
            listOf(
                Pair(rateA, ETableType.A)
            )
        )
        viewModel.loadData()
        //when
        testObserver.value().exchangeRates[0].onCurrencyPressed()
        //then
        testObserver.assertHistorySize(2)
        verify {
            getMidPricesUseCase.execute()
            currencyFormatting.format(any())
            disposable.add(any())
            navigator.navigateTo(
                ExchangeRatesNavigator.ExchangeRatesDestinations.Details(
                    rateA.code,
                    rateA.currency,
                    ETableType.A
                )
            )
        }
    }

}