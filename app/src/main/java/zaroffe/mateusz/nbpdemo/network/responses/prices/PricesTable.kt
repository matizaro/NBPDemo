package zaroffe.mateusz.nbpdemo.network.responses.prices

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PricesTable(
    @SerializedName("effectiveDate") val effectiveDate: String,
    @SerializedName("no") val no: String,
    @SerializedName("rates") val rates: List<Rate>,
    @SerializedName("table") val table: String
)