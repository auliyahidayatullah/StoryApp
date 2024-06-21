package com.dicoding.storyapp.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.storyapp.databinding.ActivityLandingBinding
import com.dicoding.storyapp.ui.main.MainActivity
import com.dicoding.storyapp.ui.signin.SignInActivity
import com.dicoding.storyapp.ui.signup.SignUpActivity

class LandingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences(USER_PREFERENCE, MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean(LOGIN_STATUS, false)

        if (isLoggedIn) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            binding.btnSignIn.setOnClickListener {
                startActivity(Intent(this, SignInActivity::class.java))
            }

            binding.btnSignUp.setOnClickListener {
                startActivity(Intent(this, SignUpActivity::class.java))
            }
        }

        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imgLogo, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val login = ObjectAnimator.ofFloat(binding.btnSignIn, View.ALPHA, 1f).setDuration(300)
        val signup = ObjectAnimator.ofFloat(binding.btnSignUp, View.ALPHA, 1f).setDuration(300)
        val title = ObjectAnimator.ofFloat(binding.landingTittle, View.ALPHA, 1f).setDuration(300)
        val desc = ObjectAnimator.ofFloat(binding.landingDesc, View.ALPHA, 1f).setDuration(300)

        AnimatorSet().apply {
            playSequentially(title, desc, login, signup)
            start()
        }
    }

    companion object {
        const val USER_PREFERENCE = "user_prefs"
        const val LOGIN_STATUS = "is_logged_in"

    }
}