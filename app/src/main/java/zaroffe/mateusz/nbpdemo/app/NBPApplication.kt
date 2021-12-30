package zaroffe.mateusz.nbpdemo.app

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import zaroffe.mateusz.nbpdemo.di.app.DaggerAppComponent

class NBPApplication: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {
        return DaggerAppComponent.factory().create(this)
    }

}