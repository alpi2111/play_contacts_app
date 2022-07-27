package com.example.playcontacts.ui.details

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.playcontacts.R
import com.example.playcontacts.helpers.FunctionsHelper
import com.example.playcontacts.helpers.ModalHelper
import com.example.playcontacts.models.User

class UserDetailActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var ivProfile: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var btnCall: AppCompatButton
    private lateinit var btnEmail: AppCompatButton
    private lateinit var btnWeb: AppCompatButton
    private lateinit var user: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        user = intent.extras?.getSerializable("user") as User

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = user.name
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        ivProfile = findViewById(R.id.iv_profile)
        FunctionsHelper.glideBuilder(
            applicationContext,
            "https://nantucket.net/wp-content/uploads/2020/09/placeholder-768x512.png",
            ivProfile
        )

        tvName = findViewById(R.id.tv_name)
        tvEmail = findViewById(R.id.tv_email)
        btnCall = findViewById(R.id.btn_call)
        btnEmail = findViewById(R.id.btn_email)
        btnWeb = findViewById(R.id.btn_web)

        tvName.text = user.phone
        tvEmail.text = user.email

        btnCall.setOnClickListener {
            onCall()
        }

        btnEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(user.email))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Hola")
            try {
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
            } catch (ex: ActivityNotFoundException) {
                ModalHelper.showToast(this@UserDetailActivity, "No tienes una app de correo. Descarga alguna", true)
            }
        }

        btnWeb.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://${user.website}")
            )
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            222 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onCall()
            } else {
                println("Call Permission Not Granted")
            }
            else -> {}
        }
    }

    private fun onCall() {
        val permissionCheck =
            ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CALL_PHONE)
        println(permissionCheck)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this@UserDetailActivity, arrayOf(Manifest.permission.CALL_PHONE),
                222
            )
        } else {
            startActivity(
                Intent(Intent.ACTION_CALL)
                    .setData(
                        Uri.parse("tel:${user.phone}")
                    )
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }
}