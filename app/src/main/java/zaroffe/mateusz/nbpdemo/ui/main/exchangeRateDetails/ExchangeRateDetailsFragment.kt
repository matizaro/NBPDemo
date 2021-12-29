package zaroffe.mateusz.nbpdemo.ui.main.exchangeRateDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import zaroffe.mateusz.nbpdemo.databinding.FragmentExchangeRateDetailsBinding
import zaroffe.mateusz.nbpdemo.ui.main.base.BaseFragment
import zaroffe.mateusz.nbpdemo.ui.main.base.error.IErrorStateHandler
import zaroffe.mateusz.nbpdemo.ui.main.dataBinding.RecyclerUtils.setData
import javax.inject.Inject

class ExchangeRateDetailsFragment: BaseFragment<ExchangeRateDetailsViewModel>() {

    @Inject
    protected lateinit var errorStateHandler: IErrorStateHandler

    val args: ExchangeRateDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) :View {
        val view = FragmentExchangeRateDetailsBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.exchangeRateDetailsVM = viewModel
        }.root
        viewModel.state.observe { state ->
            errorStateHandler.handleError(state)
        }
        viewModel.loadData(args.code, args.currency, args.tableType)
        return view
    }

    companion object {
        @JvmStatic
        @BindingAdapter("dayPrices")
        fun bindExchangeRates(recyclerView: RecyclerView, items: List<DayPrice>?) {
            recyclerView.setData(items, ::DayPricesAdapter)
        }
    }
}