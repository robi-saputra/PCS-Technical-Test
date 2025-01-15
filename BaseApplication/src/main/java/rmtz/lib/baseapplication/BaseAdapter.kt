package rmtz.lib.baseapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter <VB : ViewBinding, T>(
    var items: MutableList<T>
) : RecyclerView.Adapter<BaseAdapter.BaseViewHolder<VB>>() {

    abstract fun bindViewBinding(inflater: LayoutInflater, parent: ViewGroup): VB

    abstract fun onBind(holder: BaseViewHolder<VB>, item: T, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = bindViewBinding(inflater, parent)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        onBind(holder, items[position], position)
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: MutableList<T>) {
        items = newItems
        notifyDataSetChanged()
    }

    fun updateItemsInRange(newItems: MutableList<T>) {
        items.addAll(newItems)
        notifyItemRangeInserted(items.size - newItems.size, newItems.size)
    }

    class BaseViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)
}