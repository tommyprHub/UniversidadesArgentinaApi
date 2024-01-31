package com.tommy.universidadesargentinaapi


import android.content.Intent
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.tommy.universidadesargentinaapi.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), androidx.appcompat.widget.SearchView.OnQueryTextListener{

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UniAdapter
    private var infoUniversidades = mutableListOf<Universidad>()


    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(1300)
        setTheme(androidx.appcompat.R.style.Theme_AppCompat)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setGradientBackground()

        binding.svUni.setOnQueryTextListener(this)
        initRecyclerView()

        // cargar todas las universidades
        loadAllUniversities()
    }

    private fun initRecyclerView() {
        adapter = UniAdapter(infoUniversidades){ Universidad ->
            val intent = Intent(this@MainActivity, DatosUniversidad::class.java)
            intent.putExtra("image_url", Universidad)
            startActivity(intent)
        }
        binding.rvUni.layoutManager = LinearLayoutManager(this)
        binding.rvUni.adapter = adapter
    }

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://universities.hipolabs.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun setGradientBackground() {
        val rootView: View = binding.root

        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(
                resources.getColor(R.color.gradientStart),
                resources.getColor(R.color.gradientEnd)
            )
        )
        rootView.background = gradientDrawable
    }

    private fun searchByName(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = getRetrofit().create(APIService::class.java)
                    .getUniByName("Argentina", query).execute()

                runOnUiThread {
                    if (response.isSuccessful) {
                        val universities = response.body()
                        universities?.let {
                            infoUniversidades.clear()
                            infoUniversidades.addAll(it)
                            adapter.notifyDataSetChanged()
                        }
                        hideKeyboard()
                    } else {
                        showError()
                    }
                }

            } catch (e: Exception) {
                runOnUiThread {
                    showError()
                }
            }
        }
    }

    private fun loadAllUniversities() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = getRetrofit().create(APIService::class.java)
                .getAllUniversities("Argentina").execute()

            runOnUiThread {
                val universities = response.body()
                universities?.let {
                    infoUniversidades.clear()
                    infoUniversidades.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }
    private fun showError() {
        Toast.makeText(this, "No se ha encontrado la universidad", Toast.LENGTH_SHORT).show()
    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            searchByName(query.toLowerCase())
        }
        return true
    }
    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}