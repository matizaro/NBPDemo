package zaroffe.mateusz.nbpdemo.di.time

import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import java.time.OffsetDateTime
import java.time.Period
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

@Module
class TimeModule {

    @Provides
    fun provideNewDayDateEmitter(): Observable<OffsetDateTime> {
        val now = OffsetDateTime.now()
        val midnight = now.plusDays(1)
            .withHour(0)
            .withMinute(0)
            .withSecond(0)
            .withNano(0)
        val millisToMidnight = ChronoUnit.MILLIS.between(now, midnight)
        return Observable.timer(millisToMidnight, TimeUnit.MILLISECONDS)
            .flatMap { Observable.interval(1,TimeUnit.DAYS) }
            .map { OffsetDateTime.now() }
            .mergeWith(Observable.just(now))
    }
}