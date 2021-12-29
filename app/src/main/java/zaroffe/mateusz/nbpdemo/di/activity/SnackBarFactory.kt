package zaroffe.mateusz.nbpdemo.di.activity

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import zaroffe.mateusz.nbpdemo.R
import java.lang.ref.WeakReference

class SnackBarFactory(private val getView: ()->View) {
    fun showWithText(text: String, length: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(getView(), text, length).show()
    }
}