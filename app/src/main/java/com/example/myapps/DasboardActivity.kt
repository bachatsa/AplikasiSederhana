package com.example.myapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapps.adapter.DashboardAdapter
import com.example.myapps.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class  DasboardActivity : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: List<User>
    private lateinit var adapter: DashboardAdapter
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dasboard)
        firebaseAuth = FirebaseAuth.getInstance()

        userList = ArrayList()
        adapter = DashboardAdapter(this, userList as ArrayList<User>)

        userRecyclerView = findViewById(R.id.rycv_dashboard)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = adapter


        LoginFragment.LoginRefrence.rev.getReference("userLog").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                (userList as ArrayList<User>).clear()
                for (postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(User::class.java)
                    if(firebaseAuth.currentUser?.uid != currentUser?.customerId){
                        (userList as ArrayList<User>).add(currentUser!!)
                    }

                }
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
            }


        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.btnLogout){
            firebaseAuth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            finish()
            startActivity(intent)
            return true
        }

        return true
    }
}