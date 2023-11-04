package com.example.chat_now_v2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chat_now_v2.adapter.messageAdapter
import com.example.chat_now_v2.dataClasses.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class chatActivity : AppCompatActivity() {

    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: messageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mDbRef: DatabaseReference

    var receiverRoom:String?=null
    var senderRoom:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        val name=intent.getStringExtra("name")
        val recieveruid=intent.getStringExtra("uid")
        val imageUri=intent.getStringExtra("image")

        val senderuid= FirebaseAuth.getInstance().currentUser?.uid
        mDbRef= FirebaseDatabase.getInstance().getReference()

        senderRoom=recieveruid+senderuid
        receiverRoom=senderuid+recieveruid


        // Inflate the custom ActionBar layout
        val actionBar = supportActionBar
        actionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        actionBar?.setDisplayShowCustomEnabled(true)
        actionBar?.setCustomView(R.layout.actionbar_custom_layout)

        val customImageView = actionBar?.customView?.findViewById<ImageView>(R.id.customImageView)
        if (imageUri != null) {
            Glide.with(this@chatActivity).load(imageUri)
                .into(customImageView!!)
        }

        val customTextView= actionBar?.customView?.findViewById<TextView>(R.id.customTextView)
        if(name != null){
            customTextView?.text=name
        }

        customImageView?.setOnClickListener {
             intent = Intent(this,friendProfile::class.java)
            intent.putExtra("uniqueid",recieveruid)
            startActivity(intent)


        }


        messageRecyclerView=findViewById(R.id.chatRecyclerView)
        messageBox=findViewById(R.id.messagebox)
        sendButton=findViewById(R.id.imgview)
        messageList=ArrayList()
        messageAdapter=messageAdapter(this,messageList)

        messageRecyclerView.layoutManager= LinearLayoutManager(this)
        messageRecyclerView.adapter=messageAdapter



        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                    for(postSnapshot in snapshot.children){
                        val message=postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)

                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


        sendButton.setOnClickListener{
            val message=messageBox.text.toString()
            val messageObject=Message(message,senderuid)

            mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)

                }

            messageBox.setText("")



        }







    }
}