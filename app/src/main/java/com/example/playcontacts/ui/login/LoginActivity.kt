package com.example.playcontacts.ui.login

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import com.example.playcontacts.R
import com.example.playcontacts.api.ApiConfig
import com.example.playcontacts.api.ApiInterface
import com.example.playcontacts.helpers.FunctionsHelper
import com.example.playcontacts.helpers.ModalHelper
import com.example.playcontacts.models.ErrorModel
import com.example.playcontacts.ui.home.MainActivity
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

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
                return@setOnClickListener
            }
            val dialog = ModalHelper.showLoadingDialog(
                this@LoginActivity,
                "Iniciando sesión, por favor espera..."
            )
            login(getId(), dialog)
        }

    }

    private fun isIdEmpty(): Boolean {
        return tilId.editText?.text.isNullOrEmpty()
    }

    private fun getId(): String {
        return tilId.editText?.text.toString()
    }

    private fun login(id: String, dialog: Dialog) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val service = ApiConfig.getRetrofit().create(ApiInterface::class.java)
                val response = service.getUserById(id) ?: return@launch
                println("response: $response")
                if (!response.isSuccessful) {
                    val errorData = FunctionsHelper.errorResponseToObject(response)
                    withContext(Dispatchers.Main) {
                        ModalHelper.dismissDialog(dialog)
                        ModalHelper.showToast(
                            this@LoginActivity,
                            "Error (${errorData.code}): ${errorData.message} -> ${errorData.url}",
                            true
                        )
                    }
                    return@launch
                }

                val data = response.body()!!
                val intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    putExtra("user", data)
                }
                withContext(Dispatchers.Main) {
                    ModalHelper.dismissDialog(dialog)
                }
                startActivity(intent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ModalHelper.dismissDialog(dialog)
        }
    }
}