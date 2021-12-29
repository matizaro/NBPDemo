package zaroffe.mateusz.nbpdemo.network.responses.interval


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ExchangeIntervalResponse(
    @SerializedName("code") val code: String,
    @SerializedName("currency") val currency: String,
    @SerializedName("rates") val rates: List<Rate>,
    @SerializedName("table") val table: String
)