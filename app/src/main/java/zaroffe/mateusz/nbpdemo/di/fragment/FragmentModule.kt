package zaroffe.mateusz.nbpdemo.di.fragment

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class FragmentModule {

    @PerFragment
    @Provides
    fun providesDisposable() = CompositeDisposable()
}