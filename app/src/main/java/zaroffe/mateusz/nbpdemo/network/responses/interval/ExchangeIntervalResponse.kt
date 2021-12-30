package zaroffe.mateusz.nbpdemo.network.responses.interval


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import zaroffe.mateusz.nbpdemo.network.api.ETableType

@Keep
data class ExchangeIntervalResponse(
    @SerializedName("code") val code: String,
    @SerializedName("currency") val currency: String,
    @SerializedName("rates") val rates: List<Rate>,
    @SerializedName("table") val table: ETableType
)