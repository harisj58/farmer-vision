/*
 * Copyright 2022 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gdscbbditm.farmervision

import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.gdscbbditm.farmervision.R
import org.gdscbbditm.farmervision.databinding.ActivityMainBinding

/**
 * Main entry point into our app. This app follows the single-activity pattern, and all
 * functionality is implemented in the form of fragments.
 */
class MainActivity : AppCompatActivity(), CameraNameDialog.CameraNameDialogListener {

    private lateinit var activityMainBinding: ActivityMainBinding
    var deviceId = ""
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private val mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        //Toolbar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            // Workaround for Android Q memory leak issue in IRequestFinishCallback$Stub.
            // (https://issuetracker.google.com/issues/139738913)
            finishAfterTransition()
            destroyCamFromDatabase()
        }
        else if (isTaskRoot
            && supportFragmentManager.primaryNavigationFragment
                ?.childFragmentManager?.backStackEntryCount == 0
            && supportFragmentManager.backStackEntryCount == 0
        ) {
            finishAfterTransition()
            destroyCamFromDatabase()
        }
        else if (onBackPressedDispatcher.hasEnabledCallbacks()) {
            super.onBackPressed()
            destroyCamFromDatabase()
        } else {
            finishAfterTransition()
            destroyCamFromDatabase()
        }
    }

    override fun onDestroy() {
        if (isTaskRoot) {
            finishAfterTransition()
            destroyCamFromDatabase()
        }
        super.onDestroy()
        destroyCamFromDatabase()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.edit_cam) {
            openDialog();
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.set_cam_menu, menu)
        return true
    }

    fun openDialog() {
        val cameraNameDialog = CameraNameDialog()
        cameraNameDialog.show(supportFragmentManager, "example dialog")
    }

    override fun applyTexts(cameraname: String?) {
        Log.i("NewCameraName", cameraname!!)
        var deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        var mDbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference()
//        Toast.makeText(this@Setname, deviceId, Toast.LENGTH_SHORT).show()
        val accountUID = FirebaseAuth.getInstance().currentUser!!.uid
        var new_name = cameraname.toString()
        mDbRef.child("user").child(accountUID).child("cameras").child(deviceId).child("name")
            .setValue(new_name)

    }

    fun destroyCamFromDatabase() {
        val accountUID = FirebaseAuth.getInstance().currentUser!!.uid
        val deviceId = Settings.Secure.getString(
            this.contentResolver,
            Settings.Secure.ANDROID_ID
        )

        var dbRef = FirebaseDatabase.getInstance().reference.child("user").child(accountUID)
            .child("cameras")

        dbRef.child(deviceId).removeValue()
    }
}
