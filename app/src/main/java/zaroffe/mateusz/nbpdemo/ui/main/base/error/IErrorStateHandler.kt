package zaroffe.mateusz.nbpdemo.ui.main.base.error

import zaroffe.mateusz.nbpdemo.di.activity.SnackBarFactory
import javax.inject.Inject

interface IErrorStateHandler {

    fun handleError(state: IErrorState)

}

class ErrorStateHandler @Inject constructor(private val snackBarFactory: SnackBarFactory): IErrorStateHandler {

    override fun handleError(state: IErrorState) {
        state.errorMsg?.let(snackBarFactory::showWithText)
    }

}
