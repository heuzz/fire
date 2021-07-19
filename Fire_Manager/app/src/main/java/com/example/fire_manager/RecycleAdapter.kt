package com.example.fire_manager

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.viewholder.view.*


class RecyclerAdapter(val items: JsonObj,val context: Context, val name : String) :
    RecyclerView.Adapter<ViewHolder>(){

    override fun getItemCount() = items.result.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.viewholder, parent, false)
        return ViewHolder(inflatedView, name)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.bind(items.result[position])
    }
    }

class ViewHolder(v: View, val name: String) : RecyclerView.ViewHolder(v) {
    private var view: View = v
    fun bind(item: RecycleData) {
        Log.d("Recycler test","${item}")
        if(name == "소화전 1번"){
            view.textView2.text = item.idx
            view.textView3.text = item.regist_date
        }else{
            view.textView2.text = item.idx
            view.textView3.text = item.regist_date
        }


        }

    }