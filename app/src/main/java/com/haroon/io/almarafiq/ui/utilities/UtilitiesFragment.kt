package com.haroon.io.almarafiq.ui.utilities


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.haroon.io.almarafiq.R
import com.haroon.io.almarafiq.ui.searchservice.SearchFragment
import kotlinx.android.synthetic.main.fragment_utilities.*


class UtilitiesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_utilities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        
    }

    
    private fun setClickListeners(){
        btnPlumber.setOnClickListener {
            putDataAndSwitch("Plumber")
        }
        btnClean.setOnClickListener {
            putDataAndSwitch("Munciple")
        }
        btnElec.setOnClickListener {
            putDataAndSwitch("Electrician")
        }
        btnLockSmith.setOnClickListener {
            putDataAndSwitch("LockSmith")
        }
        btnPest.setOnClickListener {
            putDataAndSwitch("PestControl")
        }
        btnComp.setOnClickListener {
            putDataAndSwitch("Computer")
        }

        btnCarp.setOnClickListener {
            putDataAndSwitch("Carpenter")
        }
    }
   private fun putDataAndSwitch(data:String){
        val frag = SearchFragment()
        val args = Bundle()
        args.putString("serviceType",data)

        frag.arguments = args
        switchFragment(frag)
    }
    
   private  fun switchFragment( frag: Fragment){
        val transaction = fragmentManager!!.beginTransaction()

        transaction.replace(
            R.id.nav_host_fragment,frag
        ) // give your fragment container id in first parameter
        transaction.addToBackStack(null)  // if written, this transaction will be added to backstack
        transaction.commit()
    }

}
