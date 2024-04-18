package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemBinding

class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    var data: List<User> = emptyList()
        set(newValue){
            field = newValue
            notifyDataSetChanged()
        }

    var onItemClick: ((User) -> Unit)? = null
    var onChangePositionUp: ((User) -> Unit)? = null
    var onChangePositionDown: ((User) -> Unit)? = null
    var onDelete: ((User) -> Unit)? = null
    class UserViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from( parent.context)
        val binding = ItemBinding.inflate(inflater, parent, false)

        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = data[position]
        val context = holder.itemView.context
        with(holder.binding){
            nameTextView.text = user.name
            Glide.with(context)
                .load(user.image)
                .circleCrop()
                .error(R.drawable.ic_person)
                .placeholder(R.drawable.ic_person)
                .into(imageView)
            imageView2.tag = user
        }
        holder.itemView.setOnClickListener{
            onItemClick?.invoke(user)
        }
        holder.binding.imageView2.setOnClickListener{
            showPopupMenu(holder.binding.imageView2)
        }

    }
    fun updateList(newList: List<User>) {
        data = newList
        notifyDataSetChanged()
    }
    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.inflate(R.menu.popup_menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item_1 -> {
                    onChangePositionUp?.invoke(view.tag as User)
                    true
                }
                R.id.menu_item_2 -> {
                    onChangePositionDown?.invoke(view.tag as User)
                    true
                }
                R.id.menu_item_3 -> {
                    onDelete?.invoke(view.tag as User)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

}