package zaroffe.mateusz.nbpdemo.network.responses.prices

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Rate(
    @SerializedName("code") val code: String,
    @SerializedName("currency") val currency: String,
    @SerializedName("mid") val mid: Double
)