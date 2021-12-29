package zaroffe.mateusz.nbpdemo.di.app

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import zaroffe.mateusz.nbpdemo.app.NBPApplication
import zaroffe.mateusz.nbpdemo.di.activity.ActivityBuildersModule
import zaroffe.mateusz.nbpdemo.di.network.NetworkModule
import zaroffe.mateusz.nbpdemo.di.time.TimeModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AndroidInjectionModule::class,
    ActivityBuildersModule::class,
    NetworkModule::class,
    TimeModule::class,
    ResourcesModule::class
])
interface AppComponent: AndroidInjector<NBPApplication> {

    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<NBPApplication>

}