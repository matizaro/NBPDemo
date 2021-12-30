package zaroffe.mateusz.nbpdemo.ui.main.dataBinding

import androidx.recyclerview.widget.RecyclerView

object RecyclerUtils {

    fun <VH: RecyclerView.ViewHolder,
            ADAPTER_TYPE: UpdatableAdapter<VH, DATA_TYPE> ,
            DATA_TYPE> RecyclerView.setData(
        items: List<DATA_TYPE>?,
        adapterProvider: (List<DATA_TYPE>) -> UpdatableAdapter<VH, DATA_TYPE>) =
        items?.let {
            if(adapter == null) {
                adapter = adapterProvider(items)
            } else {
                (adapter as? ADAPTER_TYPE)?.setData(items)
            }
            it
        }

    abstract class UpdatableAdapter<VH: RecyclerView.ViewHolder, DATA>: RecyclerView.Adapter<VH>() {
        abstract fun setData(newList: List<DATA>)
    }

}
