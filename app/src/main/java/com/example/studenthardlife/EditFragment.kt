package com.example.studenthardlife

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController

class EditFragment : Fragment() {

    private lateinit var database: SQLiteDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit, container, false)
        database = requireContext().openOrCreateDatabase("Tasks", Context.MODE_PRIVATE, null)
        database.execSQL(BasicCommand.SQL_CREATE_TABLE)

        val title = view.findViewById<EditText>(R.id.edit_name)
        val description = view.findViewById<EditText>(R.id.edit_details)
        val name = view.findViewById<TextView>(R.id.task_number)
        val deleteButton = view.findViewById<Button>(R.id.deleteButton)
        val finishButton = view.findViewById<Button>(R.id.confirmButton)

        if(arguments != null){
            title.setText("${arguments?.getString("TITLE")}")
            description.setText("${arguments?.getString("DETAILS")}")
            name.text = "Task ${arguments?.getInt("ID")}"
            deleteButton.isEnabled = true
        }
        else {
            title.setText("Type title here...")
            description.setText("Type description here...")
            name.text = "New Task"
            deleteButton.isEnabled = false
        }

        finishButton.setOnClickListener {

            val value = ContentValues()
            value.put("title", title.text.toString())
            value.put("description", description.text.toString())
            if(arguments != null){
                val selection = "id = ?"
                val selectionArgs = arrayOf(arguments?.getInt("ID").toString())
                val cursor = database.rawQuery("SELECT * FROM Tasks WHERE id = ?", selectionArgs)
                database.update("Tasks", value, selection, selectionArgs)
                cursor.close()
            }
            else
                database.insert("Tasks", null, value)

            database.close()
            findNavController().navigate(R.id.action_editFragment_to_homeFragment)
        }

        deleteButton.setOnClickListener {
            val selection = "id = ?"
            val selectionArgs = arrayOf(arguments?.getInt("ID").toString())

            database.delete("Tasks", selection, selectionArgs)
            database.close()

            findNavController().navigate(R.id.action_editFragment_to_homeFragment)
        }

        return view
    }

}