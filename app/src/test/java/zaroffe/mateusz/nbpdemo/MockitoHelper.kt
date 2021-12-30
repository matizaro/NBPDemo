package zaroffe.mateusz.nbpdemo

import org.mockito.Mockito

object MockitoHelper {

    inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

}