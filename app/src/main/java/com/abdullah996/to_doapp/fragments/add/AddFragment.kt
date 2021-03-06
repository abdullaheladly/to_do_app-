package com.abdullah996.to_doapp.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.abdullah996.to_doapp.R
import com.abdullah996.to_doapp.data.models.Priority
import com.abdullah996.to_doapp.data.models.ToDoData
import com.abdullah996.to_doapp.data.viewmodel.ToDoViewModel
import com.abdullah996.to_doapp.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*


class AddFragment : Fragment() {
    private val mToDoViewModel:ToDoViewModel by viewModels()
    private val mSharedViewModel:SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_add, container, false)

        //set menu
        setHasOptionsMenu(true)
        view.priorities_spinner.onItemSelectedListener=mSharedViewModel.listener

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.menu_add){
            insertDataToDatabase()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDatabase() {
        val mTitle=title_et.text.toString()
        val mPriority=priorities_spinner.selectedItem.toString()
        val mDescription=description_et.text.toString()
        val validation =mSharedViewModel.verifyDataFromUser(mTitle,mDescription)
        if (validation){
            // insert data to database
            val newData=ToDoData(
                    0,
                    mTitle,
                    mSharedViewModel.parsePriority(mPriority),
                    mDescription
            )
            mToDoViewModel.insertData(newData)
            Toast.makeText(requireContext(),"Successfully Added",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else
        {
            Toast.makeText(requireContext()," Please fill all fields",Toast.LENGTH_SHORT).show()

        }


    }




}