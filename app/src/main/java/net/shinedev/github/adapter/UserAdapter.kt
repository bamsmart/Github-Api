package net.shinedev.github.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.shinedev.github.entity.User
import net.shinedev.github.listener.OnItemClickListener
import com.bumptech.glide.Glide
import net.shinedev.github.R
import net.shinedev.github.databinding.ItemUserBinding

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

    fun setData(data: ArrayList<User>) {
        this.user = data
    }

    private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemUserBinding.bind(view)

        fun bind(user: User, position: Int) {
            binding.tvUserName.text = user.username
            Glide.with(itemView.context).load(user.avatar).into(binding.imgPhoto)

            getPosition = position

            binding.imgPhoto.setOnClickListener {
                onItemClickListener?.onItemClick(user)
            }
            binding.listItem.setOnClickListener {
                onItemClickListener?.onItemClick(user)
            }
        }
    }


}