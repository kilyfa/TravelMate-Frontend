package com.example.travelmate.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.travelmate.R
import com.example.travelmate.databinding.FragmentNotificationsBinding
import com.example.travelmate.ui.login.LoginActivity
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import kotlin.random.Random

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.buttonLogout.setOnClickListener {
            logoutUser()
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textView5.text = "Loading..."
        randomizeProfileImage() // Set gambar secara acak
        getInfo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun logoutUser() {
        val sharedPreferences = requireActivity().getSharedPreferences("AppPrefs", AppCompatActivity.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            remove("auth_token")
            apply()
        }

        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        requireActivity().finish()
    }

    private fun randomizeProfileImage() {
        // Array gambar
        val images = listOf(
            R.drawable.profile_1,
            R.drawable.profile_2,
            R.drawable.profile_3
        )
        // Pilih gambar secara acak
        val randomImage = images.random()
        // Set ke ImageView
        binding.imageView3.setImageResource(randomImage)
    }

    private fun getInfo() {
        val sharedPreferences = requireActivity().getSharedPreferences("AppPrefs", AppCompatActivity.MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", null)
        Log.e("GetInfoError", "Token: $token")

        if (token.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Token tidak ditemukan, silakan login ulang", Toast.LENGTH_SHORT).show()
            binding.textView5.text = "Gagal memuat data."
            return
        }

        val request = Request.Builder()
            .url("https://travelmate-capstone.et.r.appspot.com/home/profile")
            .addHeader("Authorization", "Bearer $token")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("GetInfoError", "Gagal terhubung: ${e.message}", e)
                requireActivity().runOnUiThread {
                    binding.textView5.text = "Gagal terhubung. Coba lagi nanti."
                    Toast.makeText(requireContext(), "Gagal terhubung, periksa koneksi Anda.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBodyString = response.body?.string() // Simpan hasil body ke variabel
                requireActivity().runOnUiThread {
                    if (response.isSuccessful && !responseBodyString.isNullOrEmpty()) {
                        try {
                            val jsonObject = JSONObject(responseBodyString)
                            val dataObject = jsonObject.getJSONObject("data")
                            val name = dataObject.getString("name")

                            binding.textView5.text = "Welcome, $name"
                        } catch (e: Exception) {
                            Log.e("GetInfoError", "Error parsing JSON: ${e.message}", e)
                            binding.textView5.text = "Terjadi kesalahan saat memuat data."
                        }
                    } else {
                        binding.textView5.text = "Gagal memuat data. Coba lagi nanti."
                    }
                }
            }
        })
    }
}
