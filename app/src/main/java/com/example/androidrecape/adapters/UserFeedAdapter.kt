package com.example.androidrecape.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.androidrecape.R
import com.example.androidrecape.models.User
import com.google.android.material.textview.MaterialTextView

class UserFeedAdapter(val context: Context,val listener : OnItemClickListener<User>) : RecyclerView.Adapter<UserFeedAdapter.UserFeedViewHolder>() {

    interface OnItemClickListener<T>{
        fun onItemClicked(item:T)
    }
    private val _users = mutableListOf<User>()

    fun setItems(users:List<User>){
        _users.clear()
        _users.addAll(users)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFeedViewHolder {
       return UserFeedViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user_feed, parent, false))
    }

    override fun getItemCount(): Int {
        return _users.size
    }

    override fun onBindViewHolder(holder: UserFeedViewHolder, position: Int) {
        holder.userName.text = _users[position].name
        holder.itemView.setOnClickListener {
            listener.onItemClicked(_users[position])
        }
    }

    inner class UserFeedViewHolder(itemView: View):ViewHolder(itemView)
    {
        var userName : MaterialTextView = itemView.findViewById(R.id.user_name_text_view)
    }
}