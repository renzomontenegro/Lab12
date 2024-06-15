package dev.renzomont.lab12

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import dev.renzomontenegro.lab12.EquipoModel


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = FirebaseFirestore.getInstance()
        val etNombre: EditText = findViewById(R.id.etNombre)
        val etAñoFund: EditText = findViewById(R.id.etAñoFund)
        val etTitulos: EditText = findViewById(R.id.etTitulos)
        val etURLEquipo: EditText = findViewById(R.id.etURLEquipo)
        val btnGuardar: Button = findViewById(R.id.btnGuardar)
        val collectionRef = db.collection("equipos")

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString()
            val año = etAñoFund.text.toString()
            val numTit = etTitulos.text.toString()
            val url = etURLEquipo.text.toString()


            val equipoModel = EquipoModel(año, nombre, numTit, url)

            collectionRef.add(equipoModel)
                .addOnSuccessListener { documentReference ->
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Se REGISTRO el equipo",
                        Snackbar.LENGTH_LONG
                    ).show()
                    val intent = Intent(this, EquiposRegistrados::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { e ->
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "ERROR EN EL REGISTRO: ${e.message}",
                        Snackbar.LENGTH_LONG
                    ).show()
                    Log.e("EquipoRegistro", "Error al registrar equipo", e)
                }
                    }
                }
        }
