package com.ltbth.recyclerviewlinear

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {

    private val users = arrayListOf<User>()
    private lateinit var rcvUser: RecyclerView
    private fun getList() {
        for (i in 1..20) {
            users.add(User(R.drawable.me,"Username $i"))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getList()
        val userAdapter = UserAdapter(users)
        rcvUser = findViewById(R.id.rcv_user)

        val linearLayoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        rcvUser.layoutManager = linearLayoutManager
        rcvUser.adapter = userAdapter

        val divider: RecyclerView.ItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        rcvUser.addItemDecoration(divider)


        val callback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val a: Int = viewHolder.adapterPosition
                val b: Int = target.adapterPosition
                Collections.swap(users,a,b)
                recyclerView.adapter?.notifyItemMoved(a,b)
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos: Int = viewHolder.adapterPosition
                val adapter = rcvUser.adapter
                users.removeAt(pos)
                adapter?.notifyItemRemoved(pos)
                Toast.makeText(this@MainActivity,"User deleted",Toast.LENGTH_LONG).show()
            }
        }
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(rcvUser)
    }
}