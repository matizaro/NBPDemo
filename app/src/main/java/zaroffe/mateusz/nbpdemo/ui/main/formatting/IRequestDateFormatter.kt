package zaroffe.mateusz.nbpdemo.ui.main.formatting

import zaroffe.mateusz.nbpdemo.R
import zaroffe.mateusz.nbpdemo.app.ResourcesProviderWrapper
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

interface IRequestDateFormatter {
    fun formatDate(date: OffsetDateTime): String
}

class RequestDateFormatter @Inject constructor(resourcesProviderWrapper: ResourcesProviderWrapper): IRequestDateFormatter {

    private val pattern = resourcesProviderWrapper.getString(R.string.request_date_formatter)

    override fun formatDate(date: OffsetDateTime): String {
        return date.format(DateTimeFormatter.ofPattern(pattern))
    }

}