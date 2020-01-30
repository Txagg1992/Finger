package com.example.finger

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat

class Bio23Activity : AppCompatActivity() {

    private lateinit var biometricPrompt: BiometricPrompt
    private val TAG: String = "23Activity"

    override fun onCreate(savedInstanceState: Bundle?) {

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.NewTheme)
        } else {
            setTheme(R.style.AppTheme)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bio23)

        Log.d(TAG, "SUPER.onCreate")

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        biometricPrompt = bioMet23()

    }

    private fun bioMet23(): BiometricPrompt{

        val executor = ContextCompat.getMainExecutor(this)
        val callback = object : BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Log.d(TAG, "$errorCode :: $errString")

                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON){
                    finish()
                }
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Log.d(TAG, "Authentication Failed")
                Toast.makeText(this@Bio23Activity, "Authentication Failed", Toast.LENGTH_LONG).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Log.d(TAG, "***SUCCESS***")
            }

        }
        return BiometricPrompt(this@Bio23Activity, executor, callback)
    }

    private fun createPromptInfo(): BiometricPrompt.PromptInfo{
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle("23Title")
            .setSubtitle("23SubTitle")
            .setNegativeButtonText("Cancel")
            .build()

    }

    fun openBio(view: View) {
        Log.d(TAG, "<+++ Biometric opened +++>")
        val promptInfo = createPromptInfo()
        if (BiometricManager
                .from(applicationContext)
                .canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS){
            biometricPrompt.authenticate(promptInfo)
        }else{
            loginWithPassword()
        }
    }

    private fun loginWithPassword(){
        Log.d(TAG, "** Authenticated**")
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
