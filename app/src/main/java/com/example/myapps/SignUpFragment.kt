package com.example.myapps

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapps.model.User
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment(R.layout.signup_layout) , View.OnClickListener {

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var edtxusername : EditText
    private lateinit var edtxEmail : EditText
    private lateinit var edtxPhone : EditText
    private lateinit var edtxPassword : EditText
    private lateinit var edtxCPassword : EditText
    private lateinit var btn_SignUp : Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edtxusername = view.findViewById(R.id.edtittext_Signup_username)
        edtxEmail = view.findViewById(R.id.edtittext_Signup_email)
        edtxPhone = view.findViewById(R.id.edtittext_Signup_nomorTlp)
        edtxPassword = view.findViewById(R.id.edtittext_Signup_Password)
        edtxCPassword = view.findViewById(R.id.edtittext_Signup_ConPassword)
        btn_SignUp = view.findViewById(R.id.btn_signup_signup)
        btn_SignUp.setOnClickListener(this)


        firebaseAuth = FirebaseAuth.getInstance()

    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.btn_signup_signup){
            var username = edtxusername.text.toString().trim()
            var uemail = edtxEmail.text.toString().trim()
            var phone = edtxPhone.text.toString().trim()
            var password = edtxPassword.text.toString().trim()
            var cpassword = edtxCPassword.text.toString().trim()

            var _email = StringEncodedot(uemail)

            var isEmptyField = false
            if(username.isEmpty()){
                isEmptyField = true
                edtxusername.error = "Enter your username"
            }
            if (uemail.isEmpty()){
                isEmptyField = true
                edtxEmail.error = "Enter your email"
            }
            if (phone.isEmpty()){
                isEmptyField = true
                edtxPhone.error = "Enter your phone number"
            }
            if (password.isEmpty()){
                isEmptyField = true
                edtxPassword.error = "Enter your password"
            }
            if (!cpassword.equals(password)){
                isEmptyField = true
                edtxCPassword.error = "Your Password doesn't match"
            }

            if(!isEmptyField){


                Log.d("cek email",_email)


                signUp(uemail,password,username,phone,password,cpassword)

            }
        }
    }
    fun StringEncodedot(a : String): String {
        return a.replace(".", ",")
    }

    fun signUp(_email : String, _password : String,username : String,phone :String, password : String, cpassword:String){
        this.firebaseAuth.createUserWithEmailAndPassword(_email, _password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText( context, "Authentication succes.",
                        Toast.LENGTH_SHORT).show()
                    var customerId = firebaseAuth.currentUser?.uid!!
                    var regist = User(customerId,username,_email, phone, password, cpassword)
                    LoginFragment.LoginRefrence.rev.getReference("userLog").child(customerId).setValue(regist).addOnSuccessListener {
                        Toast.makeText(context,"Account Succes Register", Toast.LENGTH_SHORT).show()
                    }
                    findNavController().navigate(R.id.action_registerFragment_to_Login)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText( context, "Password should be at least 6 characters",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}