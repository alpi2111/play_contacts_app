package com.example.playcontacts.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.playcontacts.R
import com.example.playcontacts.helpers.FunctionsHelper
import com.example.playcontacts.models.User
import com.example.playcontacts.models.UsersResponseModel
import com.example.playcontacts.ui.details.UserDetailActivity

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

        holder.lyUser.setOnClickListener {
            val intent = Intent(context, UserDetailActivity::class.java)
            intent.putExtra("user", user)
            (context as Activity).startActivity(intent)
        }
        holder.tvName.text = user.name
        holder.tvEmail.text = user.email
        holder.tvLetter.visibility = View.GONE
        holder.ivAvatar.visibility = View.GONE
        if (position % 2 == 0) {
            holder.ivAvatar.visibility = View.VISIBLE
            FunctionsHelper.glideBuilder(
                context,
                "https://industrialkem.com.mx/wp-content/plugins/elementskit/widgets/yelp/assets/images/profile-placeholder.png",
                holder.ivAvatar
            )
        } else {
            var letter = ""
            user.name.split(" ").forEachIndexed { index, value ->
                if (index <= 1) {
                    letter += value.first().uppercaseChar()
                }
            }
            holder.tvLetter.text = letter
            holder.tvLetter.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return if (users.isNullOrEmpty()) 0 else users!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lyUser: LinearLayout = itemView.findViewById(R.id.ly_selectable)
        val tvLetter: TextView = itemView.findViewById(R.id.tv_letters)
        val ivAvatar: ImageView = itemView.findViewById(R.id.iv_avatar)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvEmail: TextView = itemView.findViewById(R.id.tv_email)
    }
}