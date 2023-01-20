package com.example.studenthardlife

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {

    private lateinit var adapter: Adapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var database: SQLiteDatabase
    private val taskList = mutableListOf<Task>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        database = requireContext().openOrCreateDatabase("Tasks", Context.MODE_PRIVATE, null)
        database.execSQL(BasicCommand.SQL_CREATE_TABLE)

        val cursor = database.rawQuery("SELECT * FROM Tasks", null)
        while(cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val title = cursor.getString(1)
            val description = cursor.getString(2)
            val data = Task(id, title, description)
            taskList.add(data)
        }
        cursor.close()
        database.close()

        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        adapter = Adapter(taskList)
        recyclerView.adapter = adapter

        val buttonAdd = view.findViewById<Button>(R.id.addButton)
        buttonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_editFragment)
        }
    }
}