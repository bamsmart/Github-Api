package net.shinedev.core.common.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.shinedev.core.R
import net.shinedev.core.databinding.ItemUserBinding
import net.shinedev.core.domain.model.User
import net.shinedev.core.listener.OnItemClickListener

class UserAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var user = arrayListOf<User>()
    var onItemClickListener: OnItemClickListener? = null
    var getPosition: Int? = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(user[position], position)
    }

    override fun getItemCount(): Int {
        return if (user.isNotEmpty()) {
            user.size
        } else {
            0
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: ArrayList<User>) {
        this.user = data
        notifyDataSetChanged()
    }

    private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemUserBinding.bind(view)

        fun bind(user: User, position: Int) = with(binding) {
            getPosition = position

            tvUserName.text = user.username

            Glide.with(itemView.context).load(user.avatar).into(imgPhoto)
            imgPhoto.setOnClickListener {
                onItemClickListener?.onItemClick(user)
            }
            listItem.setOnClickListener {
                onItemClickListener?.onItemClick(user)
            }
        }
    }
}