package com.example.findfitness

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class AdaptUser(val context: Context, val userList: ArrayList<User>): RecyclerView.Adapter<AdaptUser.UsersView>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersView {
        val view: View = LayoutInflater.from(context).inflate(R.layout.user_id, parent, false)
        return UsersView(view)
    }

    override fun getItemCount(): Int {
      return userList.size
    }

    override fun onBindViewHolder(holder: UsersView, position: Int) {
        val currentUser = userList[position]
        holder.UserOutput.text = currentUser.name

        holder.itemView.setOnClickListener{
            val intent = Intent(context, ChatspaceActivity::class.java)
            intent.putExtra("name", currentUser.name)
            intent.putExtra("uid", currentUser.uid)
            context.startActivity(intent)
        }



    }
    class UsersView(itemView: View) : RecyclerView.ViewHolder(itemView){
val UserOutput = itemView.findViewById<TextView>(R.id.etUserOutput)
    }
}