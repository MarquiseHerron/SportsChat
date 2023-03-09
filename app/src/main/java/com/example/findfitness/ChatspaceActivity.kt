package com.example.findfitness

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.os.PersistableBundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatspaceActivity : AppCompatActivity() {
// intializing and calling data for chat functionality
    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdaptor: MessageAdaptor
    private lateinit var messageActivityList: ArrayList<MessageActivity>
    private lateinit var dtaRef: DatabaseReference
    var receiverRoom: String? = null
    var senderRoom: String? = null
// The logic of the collection of data into the data base when message is sent or received
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chatspace)
        val intent = Intent()
        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        dtaRef = FirebaseDatabase.getInstance().getReference()
        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        supportActionBar?.title = name
        // calling all of the parts from the layout of chatspace
        messageRecyclerView = findViewById(R.id.ViewPort)
        messageBox = findViewById(R.id.etSendText)
        sendButton = findViewById(R.id.Send)
        messageActivityList = ArrayList()
        messageAdaptor = MessageAdaptor(this, messageActivityList)
        // Logic of linear layout
        messageRecyclerView.layoutManager = LinearLayoutManager(this)
        messageRecyclerView.adapter = messageAdaptor
        // data to be accessed in firebase
        dtaRef.child("chats").child(senderRoom!!).child("message")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageActivityList.clear()
                    for (postSnapshot in snapshot.children) {
                        val message = postSnapshot.getValue(MessageActivity::class.java)
                        messageActivityList.add(message!!)
                    }

                    messageAdaptor.notifyDataSetChanged()
                }

                override fun onCancelled(p0: DatabaseError) {

                }
            })
            // When pushing the send button where the message goes and its senderID
        sendButton.setOnClickListener {
            val message = messageBox.text.toString()
            val messageObject = MessageActivity(message,senderUid )

            dtaRef.child("chats").child(senderRoom!!).child("message").push()
                .setValue(messageObject)
                .addOnSuccessListener {

                    dtaRef.child("chats").child(receiverRoom!!).child("message").push()
                        .setValue(messageObject)
                }
            messageBox.setText(" ")

        }

    }
}



