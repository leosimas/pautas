package com.leosimas.udsagenda.ui.agendalist

import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leosimas.udsagenda.R
import com.leosimas.udsagenda.bean.Agenda
import kotlinx.android.synthetic.main.fragment_agenda_list.*
import kotlinx.android.synthetic.main.item_agenda.view.*

class AgendaListFragment : Fragment() {

    companion object {

        private const val ARG_FILTER = "filter"

        @JvmStatic
        fun newInstance(filterType: AgendaFilter): AgendaListFragment {
            return AgendaListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_FILTER, filterType.ordinal)
                }
            }
        }
    }

    private lateinit var adapter: AgendaAdapter
    private lateinit var viewModel: AgendaListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_agenda_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = AgendaAdapter()
        recycleView.adapter = adapter
        recycleView.layoutManager = LinearLayoutManager(context)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(AgendaListViewModel::class.java)
            .apply {
                val ordinal = arguments?.getInt(ARG_FILTER)
                val filter = AgendaFilter.values().find { ordinal == it.ordinal }
                    ?: AgendaFilter.OPEN
                setFilter(filter)
            }
        viewModel.listAgendas.observe(this, Observer {
            Log.d("Agenda", "$it")
            adapter.update(it)
        })
    }

    class AgendaAdapter : RecyclerView.Adapter<AgendaAdapter.ViewHolder>() {
        private var list = ArrayList<Agenda>()
        private var openStates = SparseBooleanArray()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_agenda, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = list.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val agenda = list[position]
            holder.apply {
                textTitle.text = agenda.title
                textDescription.text = agenda.description
            }
        }

        fun update(newList: List<Agenda>) {
            list.clear()
            list.addAll(newList)
            notifyDataSetChanged()
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textTitle = itemView.textTitle
            val textDescription = itemView.textDescription
        }

    }

}