package com.example.studenthardlife

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController

class DetailFragment : Fragment() {

    private lateinit var database: SQLiteDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        database = requireContext().openOrCreateDatabase("Tasks", Context.MODE_PRIVATE, null)
        database.execSQL(BasicCommand.SQL_CREATE_TABLE)

        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        view.findViewById<TextView>(R.id.task_number).text = "Task ${ arguments?.getInt("ID") }"
        view.findViewById<TextView>(R.id.detail_name).text = arguments?.getString("TITLE")
        view.findViewById<TextView>(R.id.details).text = arguments?.getString("DETAILS")


        val button = view.findViewById<Button>(R.id.returnButton)
        val editButton = view.findViewById<Button>(R.id.editButton)

        button.setOnClickListener{
            findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
        }

        editButton.setOnClickListener{
            findNavController().navigate(R.id.action_detailFragment_to_editFragment, arguments)
        }

        return view
    }

}