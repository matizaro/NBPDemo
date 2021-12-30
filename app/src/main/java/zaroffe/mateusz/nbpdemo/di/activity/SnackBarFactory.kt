package zaroffe.mateusz.nbpdemo.di.activity

import android.view.View
import com.google.android.material.snackbar.Snackbar

class SnackBarFactory(private val getView: () -> View) {

    fun showWithText(text: String, length: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(getView(), text, length).show()
    }

}