package com.haroon.io.almarafiq.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.haroon.io.almarafiq.R
import com.haroon.io.almarafiq.ui.history.HistoryFragment
import com.haroon.io.almarafiq.ui.searchservice.SearchFragment
import com.haroon.io.almarafiq.ui.utilities.UtilitiesFragment

import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


       // val textView: TextView = root.findViewById(R.id.LblHome)

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val login = inflater.inflate(R.layout.fragment_login,container,false)
        //val textView: TextView = root.findViewById(R.id.text_gallery)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if(user!=null) {

            return root

        }
        else {
            return login
        }
          }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.hideKeyboard()
        (activity as AppCompatActivity).supportActionBar!!.setTitle("Home")

        BtnAppointments.setOnClickListener {

            val frag: Fragment = UtilitiesFragment()
            switchFragment(frag)
        }
        BtnHistory.setOnClickListener {
            val frag = HistoryFragment()
            switchFragment(frag)
        }










    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun switchFragment( frag: Fragment){
        val transaction = fragmentManager!!.beginTransaction()

        transaction.replace(
            R.id.nav_host_fragment,frag
        ) // give your fragment container id in first parameter
        transaction.addToBackStack(null)  // if written, this transaction will be added to backstack
        transaction.commit()
    }


}