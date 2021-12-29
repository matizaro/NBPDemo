package zaroffe.mateusz.nbpdemo.ui.main.formatting

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

interface ICurrencyFormatting {
    fun format(value: Double): String
}

class PLNCurrencyFormatting @Inject constructor(): ICurrencyFormatting {

    override fun format(value: Double): String {
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 5
        format.currency = Currency.getInstance("PLN")
        val decimalFormatSymbols = (format as? DecimalFormat)?.decimalFormatSymbols
        decimalFormatSymbols?.currencySymbol = ""
        (format as? DecimalFormat)?.decimalFormatSymbols = decimalFormatSymbols
        return format.format(value)
    }

}