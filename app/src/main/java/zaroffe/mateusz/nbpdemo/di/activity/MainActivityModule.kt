package zaroffe.mateusz.nbpdemo.di.activity

import androidx.navigation.findNavController
import dagger.Binds
import dagger.Module
import dagger.Provides
import zaroffe.mateusz.nbpdemo.MainActivity
import zaroffe.mateusz.nbpdemo.R
import zaroffe.mateusz.nbpdemo.di.fragment.FragmentBuildersModule
import zaroffe.mateusz.nbpdemo.ui.main.base.error.ErrorStateHandler
import zaroffe.mateusz.nbpdemo.ui.main.base.error.IErrorStateHandler
import java.lang.ref.WeakReference

@Module(includes = [FragmentBuildersModule::class, MainActivityModule.BindModule::class])
class MainActivityModule {

    @PerActivity
    @Provides
    fun providesNavigationComponent(activity: MainActivity) =
        activity.findNavController(R.id.nav_host_fragment)

    @PerActivity
    @Provides
    fun providesSnackBarFactory(activity: MainActivity) = SnackBarFactory { activity.findViewById(R.id.mainCoordinatorLayout) }

    @Module
    interface BindModule {

        @PerActivity
        @Binds
        fun bindsIErrorStateHandler(handler: ErrorStateHandler): IErrorStateHandler

    }

}