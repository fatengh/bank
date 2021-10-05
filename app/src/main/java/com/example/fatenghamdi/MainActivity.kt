package com.example.fatenghamdi

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var tvbalnce: TextView
    private lateinit var etWithdraw: EditText
    private lateinit var etDeposit: EditText
    private lateinit var btnWithdraw: Button
    private lateinit var btnDeposit: Button
    private lateinit var rvAmount: RecyclerView
    private lateinit var monye: ArrayList<String>
    var balance = 0f
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        monye = arrayListOf()
        rvAmount = findViewById(R.id.rvAmount)
        tvbalnce = findViewById(R.id.tvbalnce)
        etWithdraw = findViewById(R.id.etWithdraw)
        btnDeposit = findViewById(R.id.btnDeposit)
        btnWithdraw = findViewById(R.id.btnWithdraw)
        etWithdraw = findViewById(R.id.etWithdraw)
        etDeposit = findViewById(R.id.etDeposit)


        // in the outout recording ,,, i can see that t vysorhe title is "belt exam one"
        //from where it is coming?
        rvAmount.adapter = NewAdap(monye)
        rvAmount.layoutManager = LinearLayoutManager(this)

        btnDeposit.setOnClickListener {
            deposit()

        }
        btnWithdraw.setOnClickListener {
            withdraw()

        }

        var balnceStr = balance.toString()
        sharedPreferences = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
        balnceStr = sharedPreferences.getString("your balance", "")
            .toString()  // --> retrieves data from Shared Preferences
        // We can save data with the following code
        with(sharedPreferences.edit()) {
            putString("myMessage", balnceStr)
            apply()
        }



       // title = "Belt Exam One"

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        monye.clear()
        rvAmount.adapter?.notifyDataSetChanged()
        return super.onOptionsItemSelected(item)
    }






    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        tvbalnce.text = "Current Balance : ${balance}"

    }


    fun withdraw(){
        val amount = etWithdraw.text.toString().toFloat()
        if (balance < 0f ){
            tvbalnce.setTextColor(Color.parseColor("#ff0000"))
            tvbalnce.text = "Current Balance : ${balance}"
            Toast.makeText(
                applicationContext,
                "Sorry you cant withdraw",
                Toast.LENGTH_LONG
            ).show()
        }else {
            var i = amount.toInt()
            monye.add("Withdraw ${i.toString()}")
            balance = balance - amount
            if (balance <= 0f) {
                tvbalnce.setTextColor(Color.parseColor("#ff0000"))
                balance =  balance - 20
                monye.add("Negative balance fee: 20")
                tvbalnce.text = "Current Balance : ${balance}"

            }else {
                tvbalnce.setTextColor(Color.parseColor("#000000"))
                tvbalnce.text = "Current Balance : ${balance}"

            }
            rvAmount.adapter?.notifyDataSetChanged()
            etWithdraw.text.clear()
            etWithdraw.clearFocus()
        }

    }
    fun deposit(){
        tvbalnce.setTextColor(Color.parseColor("#000000"))
        val amount = etDeposit.text.toString().toFloat()
        var i = amount.toInt()
        monye.add("Deposit ${i.toString()}")
        balance= balance+amount
        tvbalnce.text = "Current Balance : ${balance}"
        rvAmount.adapter?.notifyDataSetChanged()
        etDeposit.text.clear()
        etDeposit.clearFocus()
    }
}