package zaroffe.mateusz.nbpdemo.ui.main.dataBinding

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

object BindingUtils {

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun bindIsVisible(view: View, isVisible: Boolean) {
        view.isVisible = isVisible
    }

}