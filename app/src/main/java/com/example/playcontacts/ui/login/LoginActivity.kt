package com.example.playcontacts.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import com.example.playcontacts.R
import com.example.playcontacts.helpers.ModalHelper
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var btnLogin: AppCompatButton
    private lateinit var tilId: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tilId = findViewById(R.id.til_id_login)

        btnLogin = findViewById(R.id.btn_login)
        btnLogin.setOnClickListener {
            if (isIdEmpty()) {
                ModalHelper.showToast(this, "El ID está vacío")
            } else {
                ModalHelper.showLoadingDialog(this@LoginActivity, "Iniciando sesión, por favor espera...")
            }
        }

    }

    private fun isIdEmpty(): Boolean {
        return tilId.editText?.text.isNullOrEmpty()
    }
}