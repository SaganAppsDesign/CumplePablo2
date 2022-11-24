package com.design.cumplepablo

import android.R
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.design.cumplepablo.databinding.ActivityOnBoardingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.util.ArrayList


class OnBoarding : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var name = ""
    var birthday = ""
    private var yearSelected: Int = 0
    private lateinit var spinner: Spinner
    private lateinit var binding : ActivityOnBoardingBinding
    private lateinit var auth: FirebaseAuth
    private var storage = Firebase.storage
    private val storageRef = storage.reference
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var goToMain: Intent
    private lateinit var inputMethodManager: InputMethodManager
    private lateinit var yearList: ArrayList<String>
    private var yearList2: ArrayList<String> = arrayListOf()
    //Subir imagen a Storage de firebase
    private var imagePickerActivityResult: ActivityResultLauncher<Intent> =
          registerForActivityResult( ActivityResultContracts.StartActivityForResult()) { result ->
            if (result != null) {
               val imageUri: Uri? = result.data?.data
               val uploadTask = imageUri?.let { storageRef.child("imagenesCumple/1902.png").putFile(it) }
                // On success, download the file URL and display it
                uploadTask?.addOnSuccessListener {
                    Toast.makeText(this, "imagen subida correctamente", Toast.LENGTH_LONG).show()

                }?.addOnFailureListener {
                    Log.e("Firebase", "Image Upload fail")
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Spinner
        spinner = binding.spYears
        spinner.onItemSelectedListener = this

        for (i in 1910..2022){
           yearList2 = (yearList2.plus(i) as ArrayList<String>)
        }

        ArrayAdapter(
            this,
            R.layout.simple_spinner_item,
            yearList2
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        // Initialize Firebase Auth
        auth = Firebase.auth

        yearList = intent.getStringArrayListExtra("yearList") as ArrayList<String>

        val pref = getSharedPreferences("datos", MODE_PRIVATE)
        binding.etPersonName.setText(pref.getString("name", ""))
        binding.etBirthday.setText(pref.getString("birthday", ""))

        //content://com.android.providers.downloads.documents/document/244
        binding.btOpenPhoto.setOnClickListener{
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)
                 imagePickerActivityResult.launch(intent)
            }

        binding.btSiguiente.setOnClickListener{

            //Esconder teclado
            inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)

//            auth.signInAnonymously()
//                .addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        // Sign in success, update UI with the signed-in user's information
//                        Log.i("SUCCESS authentication", "Authentication success.")
//                        val user = auth.currentUser
//                        //updateUI(user)
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Log.e("ERROR authentication", "Authentication failed.")
//                        Toast.makeText(baseContext, "Authentication failed.",
//                            Toast.LENGTH_SHORT).show()
//                        //updateUI(null)
//                    }
//                }
            name = binding.etPersonName.text.toString()
            birthday = binding.etBirthday.text.toString()

            if (birthday.isEmpty() || birthday.contains(".") || birthday.contains("/") || birthday.contains("*")
                || birthday.contains("-")|| birthday.contains("+")){
               Toast.makeText(this, "Fecha no válida", Toast.LENGTH_SHORT).show()
            } else {
                editor = pref.edit()
                editor.putString("name", name)
                editor.putString("birthday", birthday)
                editor.apply()
                goToMain = Intent(this, MainActivity::class.java)
                goToMain.putStringArrayListExtra("yearList", yearList)
                startActivity(goToMain)
                finish()
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        yearSelected = parent?.getItemAtPosition(pos).toString().toInt()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Log.i("Error", "Error")
    }
}