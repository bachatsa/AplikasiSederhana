package com.example.myapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapps.adapter.MessageAdapter
import com.example.myapps.model.Message
import com.example.myapps.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class DetailChatActivity : AppCompatActivity() {

    private lateinit var messageRecylerView : RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter : MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var userList: List<User>
    private lateinit var firebaseAuth: FirebaseAuth





    var recieverRoom : String? = ""
    var  senderRoom : String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_chat)
        firebaseAuth = FirebaseAuth.getInstance()
        val name = intent.getStringExtra("username")
        val receiveruid = intent.getStringExtra("uid")

        val senderUid = firebaseAuth.currentUser?.uid

        senderRoom = receiveruid  + senderUid
        recieverRoom = senderUid + receiveruid


        supportActionBar?.title = name

        messageRecylerView = findViewById(R.id.rycv_detail_chat)
        messageBox = findViewById(R.id.edittext_textChat)
        sendButton = findViewById(R.id.imgButtonSend)


        messageList = ArrayList()



        userList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList as ArrayList<Message> )

        messageRecylerView.layoutManager = LinearLayoutManager(this)
        messageRecylerView.adapter = messageAdapter


        //recyclerview in chat
        LoginFragment.LoginRefrence.rev.getReference("userLog").child("chats").child(senderRoom!!)
            .child("messages")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList .clear()
                    val message2 = Message()
                    message2.message = "Ferres ferres"
                    message2.senderId = "123243432"
                    for (postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(Message::class.java)

                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                    Log.d("printMessage",messageList.size.toString())
                    Log.d("printMessage",snapshot.toString())
                    Log.d("printMessage",senderRoom.toString())


                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext,"Error to get data $error",Toast.LENGTH_SHORT).show()

                }

            })





        sendButton.setOnClickListener {
            val message = messageBox.text.toString().trim()
            val messageObj = senderUid?.let { it1 -> Message(message, it1) }

            LoginFragment.LoginRefrence.rev.getReference("userLog").child("chats")
                .child(senderRoom!!).child("messages").push().setValue(messageObj)
                .addOnSuccessListener {
                    LoginFragment.LoginRefrence.rev.getReference("userLog").child("chats")
                        .child(recieverRoom!!).child("messages").push().setValue(messageObj)
                }
            messageBox.setText("")
        }


    }
}