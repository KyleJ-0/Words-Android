package com.kylerjackson.words

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.android.volley.toolbox.Volley
import com.kylerjackson.words.ui.main.MainFragment
import com.kylerjackson.words.ui.main.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        //replace the main activity container with MainFragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        //Initializes view model, a request queue for volley, and passes the request queue to the view models repository
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        var requestQueue = Volley.newRequestQueue(this)
        viewModel.repository.requestQueue = requestQueue

    }

}