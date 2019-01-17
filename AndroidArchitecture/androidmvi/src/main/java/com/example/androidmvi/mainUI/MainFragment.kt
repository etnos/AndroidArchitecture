package com.example.androidmvi.mainUI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.androidmvi.R
import kotlinx.android.synthetic.main.fragment_main.*
import timber.log.Timber

/**
 * Keeps main UI. connects UI with ViewModel
 */
class MainFragment : Fragment() {


    companion object {

        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.i("onCreateView")
        return inflater.inflate(R.layout.fragment_main, container, false)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        initUI()
    }

    private fun initUI() {
        textView.text = "Some title form code"
        button.setOnClickListener {
            Timber.i("Button click")
            viewModel.refreshUsers()
        }

        viewModel.mainData.observe(this, Observer {
            it?.let {
                editText.setText("Users amount = ${it.size}\n$it", TextView.BufferType.EDITABLE)
            }
        })
    }
}