package zaroffe.mateusz.nbpdemo.ui.main.base

import androidx.lifecycle.LiveData
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<T : BaseViewModel> : DaggerFragment() {

    @Inject
    protected open lateinit var viewModel: T

    override fun onDestroy() {
        viewModel.dispose()
        super.onDestroy()
    }

    protected fun <S> LiveData<S>.observe(setState: (S) -> Unit) {
        this.observe(viewLifecycleOwner) {
            setState(it)
        }
    }

}