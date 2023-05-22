package com.jgarcia.flowzerotwohero.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jgarcia.flowzerotwohero.R
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        viewModel.example()

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect{uiState ->
                    when(uiState){
                        MainUiState.Loading -> {
                            progressBar.isVisible = true
                        }
                        is MainUiState.Succes -> {
                            progressBar.isVisible = false
                            Toast.makeText(this@MainActivity, "Numero de Suscriptores : ${uiState.numSuscribers}", Toast.LENGTH_SHORT).show()
                        }
                        is MainUiState.Error -> {
                            progressBar.isVisible = false
                            Toast.makeText(this@MainActivity, "Ha ocurrido un error : ${uiState.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        viewModel.example3()

    }
}