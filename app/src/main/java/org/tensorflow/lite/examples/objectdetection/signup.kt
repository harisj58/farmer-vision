package org.tensorflow.lite.examples.objectdetection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class signup : AppCompatActivity() {
    private lateinit var  edtEmail: EditText
    private lateinit var  edtPassword: EditText
    private lateinit var  edtName: EditText
    private lateinit var  btnsignup: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.hide()

        mAuth= FirebaseAuth.getInstance()
edtName=findViewById(R.id.name)
        edtEmail=findViewById(R.id.email)
        edtPassword=findViewById(R.id.password)
        btnsignup=findViewById(R.id.btnsignup)

        btnsignup.setOnClickListener{
            val email=edtEmail.text.toString()
            val password =edtPassword.text.toString()
            val name =edtName.text.toString()

            Signup(email,password,name);
        }

    }
private fun Signup(email:String,password:String,name:String){
    mAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val intent =  Intent(this@signup,Home::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this@signup,"Some error occurred",Toast.LENGTH_SHORT).show()

            }
        }}
    private fun addUserToDatabase(name: String,email: String,uid:String){
mDbRef=FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name,email,uid))
    }
}