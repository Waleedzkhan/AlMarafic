package com.haroon.io.almarafiq.ui.bookappointment


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.haroon.io.almarafiq.R

import androidx.core.view.isVisible
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.haroon.io.almarafiq.ui.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_book_appointment.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_complete_profile.*
import kotlinx.android.synthetic.main.fragment_search.*
import java.lang.StringBuilder
import java.time.LocalDateTime

/**
 * A simple [Fragment] subclass.
 */
class BookAppointmentFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var date : String
    private lateinit var Pname: String
    private lateinit var Pemail:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_appointment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.setTitle("Book an appointment")
        Pname = arguments!!.getString("PerName").toString()
        Pemail = arguments!!.getString("PerEmail").toString()
        textView10.text ="Appointment Slots for "+ Pname

        caldates.setOnDateChangeListener { view, year, month, dayOfMonth ->
            var mn = month + 1
            date = dayOfMonth.toString()+"/ "+mn.toString()+"/ "+year.toString()
        }
        btnbook.setOnClickListener {
            readProfile()
            txtinfo.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    if(!txtinfo.text.toString().equals("")){
                        val patdetails = txtinfo.text.toString()
                        val details = patdetails.split("\n")
                        val formattedpatientdetails = details[0]+" "+details[1] +" "+details[2]
                        BookAppointment(formattedpatientdetails)
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

        }
    }
    fun BookAppointment(patdetails:String){

        val recname: String = LocalDateTime.now().toString()+auth.currentUser!!.uid
        val record = hashMapOf(
            "Details" to patdetails,
            "Date" to date,
            "With" to Pname,
            "Date" to date,
            "Email" to Pemail,
            "ID" to  auth.currentUser!!.uid

        )


        val db = FirebaseFirestore.getInstance()
        db.collection("Appointment").document(recname).set(record).addOnSuccessListener { documentReference ->
            Toast.makeText(this.context!!,"Success", Toast.LENGTH_SHORT)
            txtresult.text = "Appointment for "+date+" has been booked"
//            val transaction = fragmentManager!!.beginTransaction()
//            val frag : Fragment = HomeFragment()
//            transaction.replace(
//                R.id.nav_host_fragment,frag
//            ) // give your fragment container id in first parameter
//            transaction.addToBackStack(null)  // if written, this transaction will be added to backstack
//            transaction.commit()
        }.addOnFailureListener { e ->
            Toast.makeText(this.context!!,"Failure", Toast.LENGTH_SHORT)
        }
    }

    fun readProfile():String{
        var ret:String  = ""
        val db = FirebaseFirestore.getInstance()
        db.collection("Users")

            .get()
            .addOnSuccessListener { documents ->
               // for (document in documents) {

                    val user = db.collection("Users").document(auth.currentUser!!.uid)
                    user.get().addOnCompleteListener(OnCompleteListener<DocumentSnapshot> { task ->
                        if (task.isSuccessful) {
                            val doc = task.result

                            val s  =StringBuilder("")
                            s.append(doc!!.get("FirstName").toString())
                            s.append("\n").append(doc!!.get("LastName").toString())
                            s.append("\n").append(doc!!.get("PhoneNo")).toString()
                            txtinfo.setText(s)




                        }
                    })
                        .addOnFailureListener(OnFailureListener { })
              //  }
            }
            .addOnFailureListener { exception ->

            }

        return ret }


}
