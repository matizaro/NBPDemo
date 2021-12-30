package zaroffe.mateusz.nbpdemo.ui.main.exchangeRates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import me.zhanghai.android.fastscroll.FastScrollerBuilder
import zaroffe.mateusz.nbpdemo.databinding.FragmentExchangeRatesMainBinding
import zaroffe.mateusz.nbpdemo.ui.main.base.BaseFragment
import zaroffe.mateusz.nbpdemo.ui.main.base.error.IErrorStateHandler
import zaroffe.mateusz.nbpdemo.ui.main.dataBinding.RecyclerUtils.setData
import javax.inject.Inject

class ExchangeRatesFragment: BaseFragment<ExchangeRatesViewModel>() {

    @Inject
    protected lateinit var errorStateHandler: IErrorStateHandler

    private lateinit var binding: FragmentExchangeRatesMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if(!::binding.isInitialized) {
            binding = FragmentExchangeRatesMainBinding.inflate(inflater, container, false)
            viewModel.loadData()
        }
        binding.also {
            it.lifecycleOwner = viewLifecycleOwner
            it.exchangeRatesVM = viewModel
        }
        viewModel.state.observe { state ->
            errorStateHandler.handleError(state)
            binding.executePendingBindings()
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        @BindingAdapter("exchangeRates")
        fun bindExchangeRates(recyclerView: RecyclerView, items: List<Currency>?) {
            recyclerView.setData(items, ::ExchangeRatesAdapter)
                ?.takeIf { it.isNotEmpty() }
                ?.let { currencies ->
                FastScrollerBuilder(recyclerView).setPopupTextProvider {
                    getCurrentScrollLetter(currencies, it)
                }.build()
            }
        }

        private fun getCurrentScrollLetter(
            items: List<Currency>,
            index: Int
        ) = items[index]
            .symbol.getOrNull(0)?.toString() ?: ""
    }

}