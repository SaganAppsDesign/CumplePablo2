package com.design.cumplepablo

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.design.cumplepablo.databinding.ActivityMainBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var resultText: TextView
    lateinit var textoYear: TextView
    lateinit var fecha: TextView
    lateinit var foto: ImageView
    lateinit var progressbar: ProgressBar
    lateinit var hint: String
    lateinit var welcomeText: String
    private lateinit var database: DatabaseReference
    var firebaseDatabase: FirebaseDatabase? = null

    var name: String = ""
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        firebaseDatabase = FirebaseDatabase.getInstance("https://cumplesdepablo-default-rtdb.europe-west1.firebasedatabase.app/")




        name = intent.getStringExtra("name").toString()
        hint = String.format(getString(R.string.hint), name)
        welcomeText = String.format(getString(R.string.texto_etiqueta), name)
        //Creando binding

            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            //Textos
            resultText = binding.cuadroTextoResultadoCalculo
            binding.cuadroTextoResultadoCalculo.hint = hint
            binding.etiquetaEncimaEditText.text = welcomeText
            textoYear = binding.textYear

            //Fecha
            fecha = binding.editTextFecha

            //Fotos
            foto = binding.carruselFotos
            foto.setCropToPadding(true)
            foto.setVisibility(View.VISIBLE)

            //Botón
            binding.btnCalculaEdad.setOnClickListener{ calculoEdad(it) }

            //Fondo de pantalla
            binding.ivBackground.setImageResource(R.drawable.fondocalculo)

            //Progress bar
            progressbar = binding.determinateBar

    }

//Functions

   private fun calculoEdad (view: View) {

       progressbar.visibility = View.VISIBLE
       val resultFecha = datosFecha()
       val year = datosFecha() + 2013
       val felicidades = String.format(getString(R.string.felicidades), name, resultFecha)
       val felicidades2 = String.format(getString(R.string.felicidades2), name, resultFecha)
       foto.setVisibility(View.VISIBLE)
       //Esconder teclado
       val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
       inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

        when (resultFecha) {

            in -8000..-1 -> {
                resultText.text = getString(R.string.text1)
                getBirthdayImage("pablononacido")
            }
            0 -> {
                resultText.text = getString(R.string.text2)
                getBirthdayImage(HAPPYBIRTHDAY)
                foto.setOnClickListener{yearDescription(GIUSEPPE_VERDI_NAME, year)}
            }
            1 -> {
                resultText.text = felicidades2
                getBirthdayImage("pablobebe")
                foto.setOnClickListener{yearDescription(REY_NAME, year)}
            }
            2 -> {
                resultText.text = felicidades + "\uD83D\uDE0D"
                getBirthdayImage("pablo2015")
                foto.setOnClickListener{yearDescription(ONU, year)}
            }
            3 -> {
                resultText.text = felicidades + "\uD83D\uDE0D"
                getBirthdayImage("pablo2016")
                foto.setOnClickListener{yearDescription(REAL_MADRID , year)}
            }
            4 -> {
                resultText.text = felicidades + "\uD83D\uDE0D"
                getBirthdayImage("pablo2017")
                foto.setOnClickListener{Toast.makeText(this, getString(R.string.no_data), Toast.LENGTH_LONG).show()}
                }

            5 -> {
                resultText.text = felicidades + "\uD83D\uDE0D"
                getBirthdayImage(HAPPYBIRTHDAY)
                foto.setOnClickListener{Toast.makeText(this, getString(R.string.no_data), Toast.LENGTH_LONG).show()}
                }
            6 -> {
                resultText.text = felicidades + "\uD83D\uDE0D"
                getBirthdayImage("pablo2019")
                foto.setOnClickListener{Toast.makeText(this, getString(R.string.no_data), Toast.LENGTH_LONG).show()}
            }
            7 -> {
                resultText.text = felicidades + "\uD83D\uDE0D"
                getBirthdayImage(HAPPYBIRTHDAY)
                foto.setOnClickListener{Toast.makeText(this, getString(R.string.no_data), Toast.LENGTH_LONG).show()}
            }
            8 -> {
                resultText.text = felicidades + "\uD83D\uDE0D"
                getBirthdayImage("pablo2021")
                foto.setOnClickListener{Toast.makeText(this, getString(R.string.no_data), Toast.LENGTH_LONG).show()}
            }
            9 -> {
                resultText.text = felicidades + "\uD83D\uDE0D"
                getBirthdayImage(HAPPYBIRTHDAY)
                foto.setOnClickListener{Toast.makeText(this, getString(R.string.no_data), Toast.LENGTH_LONG).show()}
            }
            10 -> {
                resultText.text = felicidades + "\uD83D\uDE0D"
                getBirthdayImage(HAPPYBIRTHDAY)
                foto.setOnClickListener{Toast.makeText(this, getString(R.string.no_data), Toast.LENGTH_LONG).show()}
            }

            11 -> {
                resultText.text = felicidades + "\uD83D\uDE0D"
                getBirthdayImage(HAPPYBIRTHDAY)
                foto.setOnClickListener{Toast.makeText(this, getString(R.string.no_data), Toast.LENGTH_LONG).show()}
            }
            12 -> {
                resultText.text = felicidades + "\uD83D\uDE0D"
                getBirthdayImage(HAPPYBIRTHDAY)
                foto.setOnClickListener{Toast.makeText(this, getString(R.string.no_data), Toast.LENGTH_LONG).show()}
            }
            in 13..17 -> {
                resultText.text =
                    "$felicidades. Estás en la etapa adolescente...\uD83D\uDE0E"
                getBirthdayImage("pabloadolescente")
                foto.setOnClickListener{Toast.makeText(this, getString(R.string.no_data), Toast.LENGTH_LONG).show()}
            }
            in 18..60 -> {
                resultText.text =
                    "$felicidades. Ya vas siendo una persona madurita... \uD83D\uDE0F"
               getBirthdayImage("pablomaduro")
               foto.setOnClickListener{Toast.makeText(this, getString(R.string.no_data), Toast.LENGTH_LONG).show()}
            }
            in 61..120 -> {
                resultText.text =
                    "$felicidades. ¡¡Ya eres un viejete!! \uD83D\uDE05"
                    getBirthdayImage("pabloanciano")
                    foto.setOnClickListener{Toast.makeText(this, getString(R.string.no_data), Toast.LENGTH_LONG).show()}
                }

            in 121..6000 -> {
                resultText.text =
                    "$felicidades. Pero es imposible con la tecnología actual..." + "\uD83D\uDE14"
                    getBirthdayImage("pablo200")
                    foto.setOnClickListener{Toast.makeText(this, "No existen datos de momento. Estamos en ello", Toast.LENGTH_LONG).show()}
            }
            else -> {
                resultText.text = getString(R.string.introduce_fecha)
                foto.visibility = View.INVISIBLE
            }
        }
    }

    //Toma la fecha del input text
    private fun datosFecha(): Int {

        if (fecha.text.isNotEmpty()) {

            val fechaString = fecha.getText().toString();
            val fechaInt = fechaString.toInt()
            textoYear.text = "Año " + fechaInt.toString()
            return fechaInt - 2013

        } else {
            textoYear.text = getString(R.string.calcula_tu_edad)
            Toast.makeText(this, "Introduce una fecha para continuar", Toast.LENGTH_SHORT).show()
            return -9999
        }
    }

    private fun getBirthdayImage (name: String){
            val storage = Firebase.storage
            val storageRef = storage.reference
            val spaceRef = storageRef.child("imagenesCumple/$name.png")
            val localfile = File.createTempFile(name, "png")

            spaceRef.getFile(localfile).addOnSuccessListener {

                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                binding.carruselFotos.setImageBitmap(bitmap)
                progressbar.visibility = View.INVISIBLE

            }.addOnFailureListener {
                Toast.makeText(this, "Error cargando imagen", Toast.LENGTH_SHORT).show()
            }

    }

    private fun yearDescription (imagen: String, year: Int){
        database = firebaseDatabase!!.getReference("efemerides").child(year.toString()).child("title")
        database.get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            val intent = Intent(this, DescriptionScreen::class.java).apply {
                putExtra("texto", it.value.toString())
                putExtra("imagen", imagen)
                putExtra("year", year)
            }
            startActivity(intent)

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }
}

