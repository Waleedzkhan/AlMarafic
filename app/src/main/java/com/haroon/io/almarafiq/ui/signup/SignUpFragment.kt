package com.haroon.io.almarafiq.ui.signup


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.haroon.io.almarafiq.R

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import com.haroon.io.almarafiq.ui.CompleteProfile.CompleteProfileFragment

import kotlinx.android.synthetic.main.fragment_sign_up.*

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment() {
private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        container!!.removeAllViews()

        auth = FirebaseAuth.getInstance()
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.setTitle("Sign Up")
        BtnSignup.setOnClickListener {


            auth.createUserWithEmailAndPassword(TxtSignUpEmail.text.toString(), TxtPasswordSignUp.text.toString())
                .addOnCompleteListener(this.activity!!) { task ->
                    if (task.isSuccessful) {

                        val frag: Fragment = CompleteProfileFragment()
                        SwitchFragment(frag)
                        //updateUI(user)
                    } else {

                        Toast.makeText(
                            this.context, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()


                    }
                }
        }
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
