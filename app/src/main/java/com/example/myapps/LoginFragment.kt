package com.example.myapps

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapps.databinding.LoginLayoutBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment(R.layout.login_layout), View.OnClickListener {

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var edUsername : TextView
    private lateinit var edPassword : TextView
    private lateinit var tvSignup : TextView
    private lateinit var btn_signIn : Button

    object LoginRefrence{
        val rev = Firebase.database("https://myapps-3396a-default-rtdb.firebaseio.com/")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth  = FirebaseAuth.getInstance()
        edUsername = view.findViewById(R.id.edittext_username)
        edPassword = view.findViewById(R.id.edittext_password)
        tvSignup = view.findViewById(R.id.textView_SignUp)
        btn_signIn = view.findViewById(R.id.buttonSignIn)
        btn_signIn.setOnClickListener(this)
        tvSignup.setOnClickListener (this)



    }

    override fun onClick(p0: View?) {
        if(p0?.id == R.id.buttonSignIn){
            var username = edUsername.text.toString().trim()
            var password = edPassword.text.toString().trim()
            var EmptyFields = false
            if (username.isEmpty()){
                EmptyFields = true
                edUsername.error = "Please enter your username"
            }
            if (password.isEmpty()){
                EmptyFields = true
                edPassword.error = "Please enter your password"
            }
            if (!EmptyFields){
                signUp(username,password)
//                val customerId = firebaseAuth.currentUser?.uid!!
//                LoginRefrence.rev.getReference("userLog").child(customerId).get().addOnSuccessListener {
//                    if (it.exists()){
//                        val _username = it.child("username").value
//                        val _noPhone = it.child("phone").value
//                        val _email = it.child("email").value
//                        val _password = it.child("password").value
//
//
//                    }
//
//                }.addOnFailureListener {
//                    Toast.makeText(context, "User dan password tidak terdaftar", Toast.LENGTH_SHORT).show()
//                }
            }
        }
        else if (p0?.id == R.id.textView_SignUp){
            findNavController().navigate(R.id.action_loginFragment_to_Register)
        }

    }
    fun StringDecodedot(a : String): String {
        return a.replace(",", ".")
    }

    fun signUp(_email : String, _password : String){
        this.firebaseAuth.signInWithEmailAndPassword(_email, _password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(context,"Login Berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, DasboardActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(context,"username dan password salah", Toast.LENGTH_SHORT).show()
                }
            }
    }
}