package zaroffe.mateusz.nbpdemo.di.activity

import dagger.Module
import dagger.android.ContributesAndroidInjector
import zaroffe.mateusz.nbpdemo.MainActivity
import zaroffe.mateusz.nbpdemo.di.viewmodel.ViewModelModule

@Module
abstract class ActivityBuildersModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [MainActivityModule::class, ViewModelModule::class])
    abstract fun contributeMainActivity(): MainActivity

}