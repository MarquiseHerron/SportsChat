package com.example.findfitness

import android.content.Context
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdaptor(val context: Context, val messageActivityList: ArrayList<MessageActivity>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val Item_Receive = 1
    val Item_Sent = 2
// Recieved and sent message logic
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == Item_Receive) {
            val view: View = LayoutInflater.from(context).inflate(R.layout.recieved_message, parent, false)
            return ReceivedMessageViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(context).inflate(R.layout.sent_message, parent, false)
            return SentMessageViewHolder(view)
        }
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageActivityList[position]
        if (holder.javaClass == SentMessageViewHolder::class.java){

        val viewHolder = holder as SentMessageViewHolder
            holder.sentmessage.text = currentMessage.message

    } else
    {
        val viewHolder = holder as ReceivedMessageViewHolder
        holder.recievedmessage.text = currentMessage.message
    }
}

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageActivityList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.ID)) {
            return Item_Sent
        } else {
            return Item_Receive
        }
    }
    override fun getItemCount(): Int {
        return messageActivityList.size

    }

// Takes information from received layout and sent
    class SentMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sentmessage = itemView.findViewById<TextView>(R.id.tvSentM)

    }
        class ReceivedMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val recievedmessage = itemView.findViewById<TextView>(R.id.tvMessageReceived)
    }


}