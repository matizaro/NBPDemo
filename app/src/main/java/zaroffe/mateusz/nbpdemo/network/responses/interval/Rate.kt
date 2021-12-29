package zaroffe.mateusz.nbpdemo.network.responses.interval


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Rate(
    @SerializedName("effectiveDate") val effectiveDate: String,
    @SerializedName("mid") val mid: Double,
    @SerializedName("no") val no: String
)