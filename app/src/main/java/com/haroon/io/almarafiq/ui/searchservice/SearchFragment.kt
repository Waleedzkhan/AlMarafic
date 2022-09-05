package com.haroon.io.almarafiq.ui.searchservice


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

import com.haroon.io.almarafiq.R

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore


import com.haroon.io.almarafiq.ui.bookappointment.BookAppointmentFragment
import com.haroon.io.almarafiq.ui.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_book_appointment.*
import kotlinx.android.synthetic.main.fragment_login.*

import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {
    var listItems: ArrayList<String> = ArrayList<String>()
    private lateinit var auth: FirebaseAuth
    lateinit var  serviceType:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        serviceType = arguments!!.getString("serviceType").toString()
        BtnSearch.setOnClickListener {
            ReadSingleContact()
        }
        (activity as AppCompatActivity).supportActionBar!!.setTitle("Search")
        lstsearch.setOnItemClickListener { parent, view, position, id ->




            var name: String = lstsearch.getItemAtPosition(position).toString()

            val frag : Fragment = BookAppointmentFragment()

            val name2 = name.split('\n')
            val namengen = name2[0].split(',')
            val data = namengen[0]
            val email = name2[3]
            val args:Bundle = Bundle()
            args.putString("PerName",data)
            args.putString("PerEmail",email)
            frag.arguments = args
            switchFragment(frag)
        }

    }
    private fun ReadSingleContact() {
     lblErrorMsg.isVisible = false
        val db = FirebaseFirestore.getInstance()
        serviceType.replace("\\s".toRegex(), "")
        db.collection(serviceType).get().addOnSuccessListener { documents ->
                for (document in documents) {

                    val user = db.collection(serviceType).document(document.id)
                    user.get().addOnCompleteListener(OnCompleteListener<DocumentSnapshot> { task ->
                        if (task.isSuccessful) {
                            val doc = task.result
                           var SearchCriteria = spnsearch.selectedItem
                            var searchTerm = txtSearch.text
                            var searchByName:Boolean = true
                            var searchByLocation:Boolean = true
                            if(SearchCriteria.equals("Name")) {
                                searchByName = true
                                searchByLocation = false
                            }
                            else if(SearchCriteria.equals("Location")){
                                searchByLocation = true
                                searchByName = false
                            }
                            else{
                                searchByName = false
                                searchByLocation = false
                            }

                           var Fname:String
                            var Lname:String
                            val SearchQuery = txtQuery.text
                            var errorDetails:String = ""
                            val fields = StringBuilder("")
                            Fname = doc!!.get("FirstName").toString()
                            Lname = doc!!.get("LastName").toString()
                            var location:String = doc!!.get("City").toString()
                            fields.append(Fname)
                            fields.append(" ").append(Lname)
                            fields.append(", Gender: ").append(doc.get("Gender"))

                            fields.append("\nPhone Number: ").append(doc.get("PhoneNo"))
                            fields.append("\nAddress: ").append(doc.get("Address"))
                            fields.append("\nEmail:").append(doc.get("Email"))

                               if(searchByName== true){
                                  if(Fname.contains(searchTerm) || Lname.contains(Lname)){
                                      listItems.add(0, fields.toString())
                                      errorDetails = " for name "+txtSearch.text
                                  }
                               }
                               else  if(searchByLocation== true){
                                   if(location.contains(searchTerm)){
                                       listItems.add(0, fields.toString())
                                       errorDetails = " for Location "+txtSearch.text
                                   }
                               }
                            else if(!searchByLocation && !searchByName)
                                listItems.add(0, fields.toString())
                           if(listItems.count() == 0){
                               lblErrorMsg.text = ("Nothing Found "+errorDetails)
                                 lblErrorMsg.isVisible = true
//                               Toast.makeText(this!!.context,"Nothing Found",Toast.LENGTH_SHORT)
                             }
                            val adapter = ArrayAdapter(this.context!!, android.R.layout.simple_list_item_1, listItems)
                            lstsearch.adapter = adapter

                        }
                    })
                        .addOnFailureListener(OnFailureListener { })
                }
            }
            .addOnFailureListener { exception ->

            }

    }
    private fun switchFragment(fragment: Fragment){
        val transaction = fragmentManager!!.beginTransaction()

        transaction.replace(
            R.id.nav_host_fragment,fragment
        ) // give your fragment container id in first parameter
        transaction.addToBackStack(null)  // if written, this transaction will be added to backstack
        transaction.commit()
    }
}
