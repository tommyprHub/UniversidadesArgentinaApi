package com.tommy.universidadesargentinaapi


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
    private var infoUni = mutableListOf<Universidad>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setGradientBackground()
        binding.svUni.setOnQueryTextListener(this)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = UniAdapter(infoUni)
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
        // Obtén la referencia al layout principal (root)
        val rootView: View = binding.root

        // Crea un drawable degradado
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(
                resources.getColor(R.color.gradientStart), // Color de inicio
                resources.getColor(R.color.gradientEnd)    // Color de fin
            )
        )

        // Establece el drawable degradado como fondo del layout principal
        rootView.background = gradientDrawable
    }

    private fun searchByName(query: String) {
        CoroutineScope(Dispatchers.IO).launch {

            try{
                val response = getRetrofit().create(APIService::class.java).getUniByName("Argentina", query).execute()

                runOnUiThread{
                    if (response.isSuccessful) {
                        val universities = response.body()
                        universities?.let {
                            infoUni.clear()
                            infoUni.addAll(it)
                            adapter.notifyDataSetChanged()
                        }
                        hideKeyboard()
                    } else {
                        showError()
                    }
                }

            }catch (e: Exception){
                runOnUiThread {
                    showError()
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