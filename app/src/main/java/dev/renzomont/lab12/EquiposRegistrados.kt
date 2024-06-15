package dev.renzomont.lab12

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class EquiposRegistrados : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_equipos_registrados)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val lvEquipos: ListView = findViewById(R.id.lvEquipos)
        val btnVolver: Button = findViewById(R.id.btnVolver)


        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("equipos")


        val equiposList = mutableListOf<String>()


        collectionRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val nombre = document.getString("nombre")
                    val año = document.getString("año")
                    val numTit = document.getString("numTit")
                    val url = document.getString("url")


                    nombre?.let {
                        equiposList.add("$it - Año: $año, Títulos: $numTit, URL: $url")
                    }
                }


                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, equiposList)


                lvEquipos.adapter = adapter
            }
            .addOnFailureListener { exception ->

                println("Error obteniendo documentos: $exception")
            }


        btnVolver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}