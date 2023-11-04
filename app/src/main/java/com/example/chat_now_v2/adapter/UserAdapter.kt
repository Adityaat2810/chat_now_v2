package com.example.chat_now_v2.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chat_now_v2.R
import com.example.chat_now_v2.chatActivity
import com.example.chat_now_v2.dataClasses.user

class UserAdapter(val context: Context,val userList:ArrayList<user>):
RecyclerView.Adapter<UserAdapter.userViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.userViewHolder {


        val view: View = LayoutInflater.from(context).inflate(R.layout.userlayout,parent,false)
        return userViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserAdapter.userViewHolder, position: Int) {
        val currentUser=userList[position]
        Glide.with(context)
            .load(currentUser.imageUri) // Replace with the URL or resource path of the user's profile picture
            .placeholder(R.drawable.login) // Placeholder for loading or if the imageUri is null
            .error(R.drawable.login) // Error image if loading fails
            .centerCrop()
            .into(holder.textpic)


        holder.textName.text = currentUser.name



        holder.itemView.setOnClickListener{
            val intent = Intent(context,chatActivity::class.java)

            intent.putExtra("name",currentUser.name)
            intent.putExtra("uid",currentUser.uid)
            intent.putExtra("image",currentUser.imageUri)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class userViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textName=itemView.findViewById<TextView>(R.id.userName)
        val textpic = itemView.findViewById<ImageView>(R.id.userDp)

    }


}