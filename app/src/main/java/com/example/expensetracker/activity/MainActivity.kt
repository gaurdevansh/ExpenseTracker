package com.example.expensetracker.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.expensetracker.R
import com.example.expensetracker.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    /*private val viewModel: TransactionViewModel by viewModels {
        TransactionViewModelFactory((application as TransactionApplication).repository)
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var navController = findNavController(R.id.navController)
        var bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setupWithNavController(navController)

        binding.addTransactionBtn.setOnClickListener {
            startActivity(Intent(this, AddTransactionActivity::class.java))
        }

    }
}