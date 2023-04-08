package org.tensorflow.lite.examples.objectdetection

import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Setname : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var button: Button
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setname)
        name = findViewById(R.id.cameraname)
        button = findViewById(R.id.button)
        fun getDeviceTd(context: Context): String? {
            return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        }
        toolbar = findViewById(R.id.home_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        var deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        var mDbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        Toast.makeText(this@Setname, deviceId, Toast.LENGTH_SHORT).show()
        val currentuser = FirebaseAuth.getInstance().currentUser!!.uid
        button.setOnClickListener {
            var nammmmm = name.text.toString()
            mDbRef.child("name").child(currentuser).child(deviceId)
                .setValue(nammmmm)
        }
    }

}