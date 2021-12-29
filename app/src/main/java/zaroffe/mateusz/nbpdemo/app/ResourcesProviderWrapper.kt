package zaroffe.mateusz.nbpdemo.app

import androidx.annotation.StringRes

class ResourcesProviderWrapper constructor(private val app: NBPApplication) {
    fun getString(@StringRes res: Int) = app.getString(res)
}