package com.haroon.io.almarafiq.ui.CompleteProfile


import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.haroon.io.almarafiq.R

import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore


import com.haroon.io.almarafiq.ui.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_complete_profile.*
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

/**
 * A simple [Fragment] subclass.
 */
class CompleteProfileFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    var cal = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_complete_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        (activity as AppCompatActivity).supportActionBar!!.setTitle("Profile Completion")

        TxtBDate.setOnClickListener {


            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(
                activity!!,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    var mn = monthOfYear +1
                    // Display Selected date in textbox
                    TxtBDate.setText("" + dayOfMonth + " / " + mn + " / " + year)
                },
                year,
                month,
                day
            )

            dpd.show()


        }


        BtnSave.setOnClickListener {


            var gen: String = ""
            if (GndFMale.isChecked) {
                gen = "Female"
            } else if (GndMale.isChecked) {
                gen = "Male"
            }

            var Role: String = ""

            val emailaddress = auth.currentUser!!.email
            val record = hashMapOf(
                "UserID" to auth.currentUser!!.uid,
                "FirstName" to TxtFname.text.toString(),
                "LastName" to TxtLName.text.toString(),
                "BirthDate" to TxtBDate.text.toString(),
                "Gender" to gen,
                "PhoneNo" to TxtPhoneNo.text.toString(),
                "Address" to TxtAddress.text.toString(),


                "Email" to emailaddress

            )


            val db = FirebaseFirestore.getInstance()
            db.collection("Users").document(auth.currentUser!!.uid).set(record)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this.context!!, "Success", Toast.LENGTH_SHORT)


                    val transaction = fragmentManager!!.beginTransaction()
                    val frag: Fragment = HomeFragment()
                    transaction.replace(
                        R.id.nav_host_fragment, frag
                    ) // give your fragment container id in first parameter
                    transaction.addToBackStack(null)  // if written, this transaction will be added to backstack
                    transaction.commit()
                }.addOnFailureListener { e ->
                    Toast.makeText(this.context!!, "Failure", Toast.LENGTH_SHORT)
                }

        }
    }
}
