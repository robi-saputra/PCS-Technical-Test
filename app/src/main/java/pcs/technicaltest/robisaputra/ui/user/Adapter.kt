package pcs.technicaltest.robisaputra.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import pcs.technicaltest.robisaputra.databinding.ItemUserBinding
import rmtz.lib.baseapplication.BaseAdapter
import rmtz.lib.baseapplication.utils.formatDate
import robi.codingchallenge.networks.data.User
import java.util.Locale

class Adapter(items: MutableList<User>) : BaseAdapter<ItemUserBinding, User>(items) {
    var callback: AdapterCallback? = null

    interface AdapterCallback {
        fun onItemClick(user: User)
    }

    override fun bindViewBinding(inflater: LayoutInflater, parent: ViewGroup): ItemUserBinding {
        return ItemUserBinding.inflate(inflater, parent, false)
    }

    override fun onBind(holder: BaseViewHolder<ItemUserBinding>, item: User, position: Int) {
        Glide.with(holder.itemView.context).load(item.avatar).centerCrop().into(holder.binding.ivAvatar)
        holder.binding.tvName.text = item.name
        holder.binding.tvJoined.text = item.createdAt.formatDate("yyyy-MM-dd'T'HH:mm:ss.SSSX", "dd MMM yyyy", Locale.ROOT)
        holder.binding.root.setOnClickListener {
            callback?.onItemClick(item)
        }
    }


}