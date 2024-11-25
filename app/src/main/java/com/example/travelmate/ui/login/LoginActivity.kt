package com.example.travelmate.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.travelmate.MainActivity
import com.example.travelmate.api.ApiClient
import com.example.travelmate.api.SignInRequest
import com.example.travelmate.api.SignInResponse
import com.example.travelmate.databinding.ActivityLoginBinding
import com.example.travelmate.ui.register.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tombol untuk berpindah ke halaman Register
        binding.textView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Tombol untuk Login
        binding.button.setOnClickListener {
            val name = binding.emailEt.text.toString() // Diubah ke "name" sesuai API
            val password = binding.passET.text.toString()

            if (name.isNotEmpty() && password.isNotEmpty()) {
                login(name, password)
            } else {
                Toast.makeText(this, "Empty Fields Are Not Allowed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun login(name: String, password: String) {
        val request = SignInRequest(name, password)

        ApiClient.userApiService.signIn(request).enqueue(object : Callback<SignInResponse> {
            override fun onResponse(call: Call<SignInResponse>, response: Response<SignInResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status == "success") {
                        Toast.makeText(this@LoginActivity, body.message, Toast.LENGTH_SHORT).show()

                        // Simpan token jika diperlukan (gunakan SharedPreferences)
                        saveToken(body.token)

                        // Berpindah ke MainActivity
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Login failed: ${body?.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveToken(token: String) {
        val sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("auth_token", token)
        editor.apply()
    }

    override fun onStart() {
        super.onStart()

        // Periksa apakah token sudah ada (sudah login sebelumnya)
        val sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", null)

        if (token != null) {
            // Jika sudah login, langsung berpindah ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
