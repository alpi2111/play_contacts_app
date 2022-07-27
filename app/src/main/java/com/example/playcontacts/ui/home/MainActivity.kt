package com.example.playcontacts.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.playcontacts.R
import com.example.playcontacts.adapters.ItemUsersAdapter
import com.example.playcontacts.controllers.MainActivityController
import com.example.playcontacts.models.User
import com.example.playcontacts.models.UsersResponseModel
import com.example.playcontacts.ui.login.LoginActivity
import com.google.android.material.progressindicator.CircularProgressIndicator
import java.util.Observer
import java.util.Observable

class MainActivity : AppCompatActivity(), Observer {

    private lateinit var btnLogout: ImageView
    private lateinit var tvWelcomeMessage: TextView
    private lateinit var cpiLoading: CircularProgressIndicator
    private lateinit var rvUsers: RecyclerView
    private lateinit var usersAdapter: ItemUsersAdapter
    private lateinit var mainActivityController: MainActivityController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvWelcomeMessage = findViewById(R.id.tv_welcome)
        btnLogout = findViewById(R.id.iv_logout)
        cpiLoading = findViewById(R.id.cpi_loading)
        rvUsers = findViewById(R.id.rv_users)
        usersAdapter = ItemUsersAdapter(this@MainActivity)
        rvUsers.adapter = usersAdapter


        val user = intent.extras?.getSerializable("user") as User
        mainActivityController = MainActivityController()
        mainActivityController.addObserver(this)
        mainActivityController.updateUser(user)

        btnLogout.setOnClickListener {
            val intent = Intent(this@MainActivity, LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
        }

    }

    override fun update(observable: Observable?, p1: Any?) {
        val userInfo: User? = mainActivityController.userInfo
        val allUsers: UsersResponseModel? = mainActivityController.allUsers
        println(userInfo)
        if (userInfo != null) {
            tvWelcomeMessage.text = "Bienvenido, ${userInfo.username}"
        }

        if (allUsers != null) {
            usersAdapter = ItemUsersAdapter(this@MainActivity, allUsers)
            rvUsers.adapter = usersAdapter
            val dividerItemDecoration = DividerItemDecoration(
                rvUsers.context,
                DividerItemDecoration.VERTICAL
            )
            rvUsers.addItemDecoration(dividerItemDecoration)
            rvUsers.animation = AnimationUtils.loadAnimation(applicationContext, android.R.anim.fade_in)
            cpiLoading.animation = AnimationUtils.loadAnimation(applicationContext, android.R.anim.fade_out)
            cpiLoading.visibility = View.GONE
            rvUsers.visibility = View.VISIBLE
        }
    }
}