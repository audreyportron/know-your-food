package com.apo.template.ui.binding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL

@BindingAdapter("autobind")
fun autobind(recycler: RecyclerView, autobindedViewModels: List<AutobindedViewModel>?) {
    if (recycler.layoutManager == null) {
        recycler.layoutManager = LinearLayoutManager(recycler.context, VERTICAL, false)
    }

    if (recycler.adapter == null || recycler.adapter !is AutobindedViewModelAdapter) {
        recycler.adapter = AutobindedViewModelAdapter(recycler.context)
    }

    (recycler.adapter as AutobindedViewModelAdapter).apply {
        replaceData(autobindedViewModels.orEmpty())
    }

}

/** **********************************
 *          Autobinded Recycler View Adapter
 *********************************** **/
class AutobindedViewModelAdapter(context: Context) : RecyclerView.Adapter<AutobinbedViewHolder>() {
    private val inflater = LayoutInflater.from(context)

    val viewModels: MutableList<AutobindedViewModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutobinbedViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, viewType, parent, false)
        return AutobinbedViewHolder(binding)
    }

    override fun getItemCount(): Int = viewModels.size

    override fun onBindViewHolder(holder: AutobinbedViewHolder, position: Int) {
        holder.data = viewModels[position]
    }

    override fun getItemViewType(position: Int): Int {
        return viewModels[position].layout
    }

    fun replaceData(models: List<AutobindedViewModel>) {
        viewModels.clear()
        viewModels.addAll(models)
        notifyDataSetChanged()
    }
}


class AutobinbedViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    var data: AutobindedViewModel? = null
        set(value) {
            field = value
            if (value != null) {
                binding.setVariable(value.variable, value)
                binding.executePendingBindings()
            }
        }

}

interface AutobindedViewModel {
    val layout: Int
    val variable: Int
}
