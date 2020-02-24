package com.example.finger

import android.content.DialogInterface
import android.content.Intent
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.NewTheme)
        }else{
            setTheme(R.style.AppTheme)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

    }

    private fun initViews() {

        night_switch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener{ compoundButton, isChecked ->
            if (isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            val executor = Executors.newSingleThreadExecutor()

            val biometricPrompt = BiometricPrompt.Builder(this)
                .setTitle("Finger Junk")
                .setDescription("More Stuff")
                .setNegativeButton("Cancel", executor,
                    DialogInterface.OnClickListener { dialog, which -> })
                .build()

            val activity = this

            goToButton.setOnClickListener(View.OnClickListener {
                biometricPrompt.authenticate(CancellationSignal(),
                    executor, object : BiometricPrompt.AuthenticationCallback(){
                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                            activity.runOnUiThread {
                                Toast.makeText(activity, "Succeeded", Toast.LENGTH_LONG).show()
                                val intent = Intent(this@MainActivity, BioActivity::class.java)
                                startActivity(intent)

                            }
                        }

                        override fun onAuthenticationFailed() {
                            activity.runOnUiThread {
                                Toast.makeText(activity, "Failed", Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                            activity.runOnUiThread {
                                Toast.makeText(activity, errString, Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
                            activity.runOnUiThread {
                                Toast.makeText(activity, helpString, Toast.LENGTH_LONG).show()
                            }
                        }
                    })
            })
        }else{
            Toast.makeText(this, "Device is not Pie or newer.", Toast.LENGTH_LONG).show()
        }

    }

    fun clickMe(view: View) {
        val intent = Intent(this, BioActivity::class.java)
        startActivity(intent)


//        val executor = Executors.newSingleThreadExecutor()
//
//        val biometricPrompt = BiometricPrompt.Builder(this)
//            .setTitle("Finger")
//            .setSubtitle("Little finger")
//            .setDescription("Stuff")
//            .setNegativeButton("Cancel", executor,
//                DialogInterface.OnClickListener { dialog, which -> }).build()
//
//        val activity = this
//
//        val authenticate = findViewById(R.id.auth)
//
//        authenticate.setOnClickListener(View.OnClickListener {
//            biometricPrompt.authenticate(
//                CancellationSignal(),
//                executor,
//                object : BiometricPrompt.AuthenticationCallback() {
//                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
//                        activity.runOnUiThread {
//                            Toast.makeText(
//                                activity, "Jump for Joy", Toast.LENGTH_LONG
//                            ).show()
//                        }
//                    }
//                })
//        })
    }

    fun clickMe23(view: View) {
        val intent23 = Intent(this, Bio23Activity::class.java)
        startActivity(intent23)
    }


}
