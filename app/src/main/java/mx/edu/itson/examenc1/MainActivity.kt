package mx.edu.itson.examenc1

import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

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
        val editCantidad = findViewById<EditText>(R.id.editCantidad)
        val editProducto = findViewById<EditText>(R.id.editProducto)
        val editPrecio = findViewById<EditText>(R.id.editPrecio)
        val buttonAgregar = findViewById<Button>(R.id.buttonAgregar)
        val listView = findViewById<ListView>(R.id.listView)
        val textSubtotal = findViewById<TextView>(R.id.textSubtotal)
        val textIVA = findViewById<TextView>(R.id.textIVA)
        val textTotal = findViewById<TextView>(R.id.textTotal)

        val productos = mutableListOf<Double>()
        val productosTexto = mutableListOf<String>()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, productosTexto)
        listView.adapter = adapter

        buttonAgregar.setOnClickListener {


                val cantidad = editCantidad.text.toString().toIntOrNull()
                val nombre = editProducto.text.toString().trim()
                val precio = editPrecio.text.toString().toDoubleOrNull()

                if (cantidad == null || cantidad <= 0 || nombre.isEmpty() || precio == null || precio <= 0) {
                    Toast.makeText(this, "Ingrese datos válidos", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val totalPrecio = cantidad * precio

                productos.add(totalPrecio)

            val textoProducto = "$cantidad x $nombre ($%.2f c/u) - $%.2f".format(precio, totalPrecio)
            productosTexto.add(textoProducto)
                adapter.notifyDataSetChanged()

                    val subtotal = productos.sum()
                    val iva = (subtotal * 0.16)
                    val total = (subtotal + iva)

                    textSubtotal.text = "Sub-total: " + "%.2f".format(subtotal)
                    textIVA.text = "IVA (16%): " + "%.2f".format(iva)
                    textTotal.text = "Total: " + "%.2f".format(total)

                editCantidad.text.clear()
                editProducto.text.clear()
                editPrecio.text.clear()


        }
    }
}
