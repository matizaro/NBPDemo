package zaroffe.mateusz.nbpdemo.ui.main.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel constructor(private val disposable: CompositeDisposable) : ViewModel() {

    fun dispose() {
        if(!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    protected fun Disposable.toDispose() = disposable.add(this)

}