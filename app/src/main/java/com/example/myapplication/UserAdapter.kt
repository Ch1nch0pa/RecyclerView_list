package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemBinding
class UserDiffUtil(
    private val oldList: List<User>,
    private val newList: List<User>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser.id == newUser.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPerson = oldList[oldItemPosition]
        val newPerson = newList[newItemPosition]
        return oldPerson == newPerson
    }
}
interface UserActionListener {
    fun onUserInfo(user: User)
    fun onUserMove(user: User, position: Int)
    fun onUserDelete(user: User)
}
class UserAdapter (private val userActionListener: UserActionListener) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>(), View.OnClickListener {

    var data: List<User> = emptyList()
        set(newValue){
            val userDiffUtil = UserDiffUtil(field, newValue)
            val userDiffUtilResult = DiffUtil.calculateDiff(userDiffUtil)
            field = newValue
            userDiffUtilResult.dispatchUpdatesTo(this@UserAdapter)
        }
    class UserViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from( parent.context)
        val binding = ItemBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.imageView2.setOnClickListener(this)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = data[position]
        val context = holder.itemView.context
        with(holder.binding){
            nameTextView.text = "${user.first_name} ${user.last_name}"
            Glide.with(context)
                .load(user.avatar)
                .circleCrop()
                .error(R.drawable.ic_person)
                .placeholder(R.drawable.ic_person)
                .into(imageView)
            imageView2.tag = user
            root.tag = user
        }

    }
    override fun onClick(view: View)
    {
        val user: User = view.tag as User

        when (view.id) {
            R.id.imageView2 -> showPopupMenu(view)
            else -> userActionListener.onUserInfo(user)
        }

    }
    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.inflate(R.menu.popup_menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item_1 -> {
                    userActionListener.onUserMove(view.tag as User, 1)
                    true
                }
                R.id.menu_item_2 -> {
                    userActionListener.onUserMove(view.tag as User, -1)
                    true
                }
                R.id.menu_item_3 -> {
                    userActionListener.onUserDelete(view.tag as User)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

}