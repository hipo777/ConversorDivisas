package org.demre.conversordivisas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import org.demre.conversordivisas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val divisas = listOf<String>("USD", "CLP", "EURO")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.spinnerDe.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,divisas)
        binding.spinnerPara.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,divisas)

        initListerner()
    }

    private fun initListerner() {
        binding.btnConvertir.setOnClickListener(){
            val monto = binding.eTingreso.text.toString().toDouble()
            if (monto != null) {
                val deMoneda = binding.spinnerDe.selectedItem.toString()
                val aMoneda = binding.spinnerPara.selectedItem.toString()
                val result = convert(monto, deMoneda, aMoneda)
                binding.tVresultado.text = "Resultado: $result"
            }
        }
        binding.btnLimpiar.setOnClickListener{
            limpiar()
        }
    }

    private fun convert(cantidad: Double, deCantidad: String, aCantidad: String): Double {

        val euroParaUsd = 1.11
        val euroParaClp = 909.93

        // Convertir la cantidad a Euro como moneda base
        val montoEnEuro = when (deCantidad) {
            "EURO" -> cantidad
            "USD" -> cantidad / euroParaUsd
            "CLP" -> cantidad / euroParaClp
            else -> 0.0 // Moneda no válida, retorna 0.0
        }

        // Convertir de Euro a la moneda deseada
        return when (aCantidad) {
            "EURO" -> montoEnEuro
            "USD" -> montoEnEuro * euroParaUsd
            "CLP" -> montoEnEuro * euroParaClp
            else -> 0.0 // Moneda no válida, retorna 0.0
        }
    }
    fun limpiar(){
        binding.tVresultado.text = ""
        binding.eTingreso.setText("")

    }
}