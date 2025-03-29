package com.example.currencyconverter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private lateinit var edtValue1: EditText
    private lateinit var edtValue2: EditText
    private lateinit var spMoney1: Spinner
    private lateinit var spMoney2: Spinner
    private lateinit var txtConvert: TextView

    private val exchangeRates = mapOf(
        "USD" to 1.0,
        "VND" to 23185.0,
        "EUR" to 0.91,
        "CNY" to 7.24,
        "ARS" to 852.5
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        edtValue1 = findViewById(R.id.edtValue1)
        edtValue2 = findViewById(R.id.edtValue2)
        spMoney1 = findViewById(R.id.spMoney1)
        spMoney2 = findViewById(R.id.spMoney2)
        txtConvert = findViewById(R.id.txtConvert)

        val currencies = exchangeRates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currencies)
        spMoney1.adapter = adapter
        spMoney2.adapter = adapter

        edtValue1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                convertCurrency()
            }
        })
        spMoney1.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        spMoney2.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun convertCurrency() {
        val input = edtValue1.text.toString().toDoubleOrNull() ?: 0.0
        val fromCurrency = spMoney1.selectedItem.toString()
        val toCurrency = spMoney2.selectedItem.toString()

        val fromRate = exchangeRates[fromCurrency] ?: 1.0
        val toRate = exchangeRates[toCurrency] ?: 1.0
        val convertedValue = (input / fromRate) * toRate

        edtValue2.setText(String.format("%.2f", convertedValue))
        txtConvert.text = "1 $fromCurrency = ${String.format("%.2f", toRate / fromRate)} $toCurrency"
    }


}
