package com.example.playcontacts.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.playcontacts.R
import com.example.playcontacts.models.User
import com.example.playcontacts.models.UsersResponseModel

class ItemUsersAdapter(
    private val context: Context,
    private var users: UsersResponseModel? = null
) :
    RecyclerView.Adapter<ItemUsersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (users == null) return
        Log.i("pos", position.toString() + "")
        val user: User = users!![position]
        holder.tvName.text = user.name
        holder.tvEmail.text = user.email
    }

    override fun getItemCount(): Int {
        return if (users.isNullOrEmpty()) 0 else users!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvEmail: TextView = itemView.findViewById(R.id.tv_email)
    }
}