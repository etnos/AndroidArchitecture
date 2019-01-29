package com.example.androidcoroutines.mainui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.androidcoroutines.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        initUI()
    }


    private fun initUI() {
        button.setOnClickListener {
            viewModel.testButtonClick()
        }

        button2.setOnClickListener {
            viewModel.testButtonClick2()
        }

        viewModel.data.observe(this, Observer {
            if (it == null) return@Observer
            when (it) {
                is UIData.Success -> {
                    initUserUI(it.user)
                }
                is UIData.Error -> {
                    // todo handle errors here
                }
            }
        })
    }

    private fun initUserUI(user: UserModel) {
        textViewUserId.text = "UserId: ${user.userId}"
        textViewId.text = "ID:  ${user.id}"
        textViewTitle.text = "Title: ${user.title}"
        textViewCompleted.text = "Completed: ${user.completed}"
    }
}
