package com.example.fire_manager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.viewholder.view.*


class RecyclerAdapter(private val items: ArrayList<RecycleData>,val context: Context) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener {it ->
            Toast.makeText(it.context, "Clicked:", Toast.LENGTH_SHORT).show()
        }
        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerAdapter.ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.viewholder, parent, false)
        return RecyclerAdapter.ViewHolder(inflatedView)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        fun bind(listener: View.OnClickListener, item: RecycleData) {
            view.textView2.text = item.count
            view.textView3.text = item.date
            view.textView4.text = item.time

        }
    }
    }