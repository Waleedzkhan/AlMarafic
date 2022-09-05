package com.haroon.io.almarafiq.ui.login


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

import com.haroon.io.almarafiq.R
import com.haroon.io.almarafiq.ui.history.HistoryFragment
import com.haroon.io.almarafiq.ui.home.HomeFragment
import com.haroon.io.almarafiq.ui.searchservice.SearchFragment

import com.haroon.io.almarafiq.ui.signup.SignUpFragment
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_login, container, false)

        auth = FirebaseAuth.getInstance()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (auth.currentUser == null) {
        super.onViewCreated(view, savedInstanceState)


        BtnSignUp.setOnClickListener {
            val transaction = fragmentManager!!.beginTransaction()
            val frag: Fragment = SignUpFragment()
            transaction.replace(
                R.id.nav_host_fragment, frag
            ) // give your fragment container id in first parameter
            transaction.addToBackStack(null)  // if written, this transaction will be added to backstack
            transaction.commit()


        }


        BtnLogin.setOnClickListener {
            val currentUser = auth.currentUser




                //imgloading.isVisible = true
                auth.signInWithEmailAndPassword(
                    TxtUserName.text.toString(),
                    TxtPassword.text.toString()
                )
                    .addOnCompleteListener(this.activity!!) { task ->
                        if (task.isSuccessful) {

                            var frag: Fragment? = HomeFragment()
                            if(frag!=null)
                            SwitchFragment(frag)
                            txtmsg.setText("Te")
                            txtmsg.addTextChangedListener(object : TextWatcher {
                                override fun afterTextChanged(p0: Editable?) {
                                   // if (!txtmsg.text.toString().equals("")) {
                                       // if (frag != null)
                                          //  SwitchFragment(frag)

                                   // }
                                }

                                override fun beforeTextChanged(
                                    p0: CharSequence?,
                                    p1: Int,
                                    p2: Int,
                                    p3: Int
                                ) {
                                }

                                override fun onTextChanged(
                                    p0: CharSequence?,
                                    p1: Int,
                                    p2: Int,
                                    p3: Int
                                ) {
                                }
                            })


                        } else {

                            Toast.makeText(
                                this.context, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

//        else
//        {
//            BtnAppointments.setOnClickListener {
//
//           val frag: Fragment = SearchFragment()
//          SwitchFragment(frag)
//      }
//       BtnHistory.setOnClickListener {
//           val frag = HistoryFragment()
//            SwitchFragment(frag)
//       }
//        }

      var test:String = ""
    }
    fun SwitchFragment(fragment: Fragment){
        val transaction = fragmentManager!!.beginTransaction()

        transaction.replace(
            R.id.nav_host_fragment,fragment
        ) // give your fragment container id in first parameter
        transaction.addToBackStack(null)  // if written, this transaction will be added to backstack
        transaction.commit()
    }

}
