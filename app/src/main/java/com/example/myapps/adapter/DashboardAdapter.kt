package com.example.myapps.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapps.DetailChatActivity
import com.example.myapps.R
import com.example.myapps.model.User

class DashboardAdapter(val context: android.content.Context , val userList : ArrayList<User>) :
    RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {

    class DashboardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtViewName = itemView.findViewById<TextView>(R.id.tv_namaUser_adapter)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.adapter_dashboard,parent,false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.txtViewName.text = currentUser.username
        holder.itemView.setOnClickListener{
            val intent = Intent(context, DetailChatActivity::class.java)
            intent.putExtra("username",currentUser.username)
            intent.putExtra("uid",currentUser.customerId)
            context.startActivity(intent )

        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }
}