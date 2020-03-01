package com.leosimas.udsagenda.ui.agendalist

import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leosimas.udsagenda.R
import com.leosimas.udsagenda.bean.Agenda
import com.leosimas.udsagenda.extension.gone
import com.leosimas.udsagenda.extension.visible
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

    private val receiver = object : AgendaListReceiver() {
        override fun onRefresh() {
            viewModel.refresh()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_agenda_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AgendaListReceiver.register(context, receiver)
        initViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = AgendaAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context, DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        context?.unregisterReceiver(receiver)
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
            adapter.update(it)
        })
    }

    class AgendaAdapter(private val fragment: AgendaListFragment) :
        RecyclerView.Adapter<AgendaAdapter.ViewHolder>() {

        private var list = ArrayList<Agenda>()
        private var expandStates = SparseBooleanArray()

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
                textDetails.text = agenda.details
                textAuthor.text = agenda.author

                agenda.uid?.let {
                    refreshExpandView(it, holder)
                }
                itemView.setOnClickListener {
                    toggleExpand(agenda, holder)
                }
                button.setOnClickListener {
                    fragment.viewModel.toggle(agenda)
                }
            }
        }

        private fun refreshExpandView(uid: Int, holder: ViewHolder) {
            val isExpanded = expandStates[uid]

            if (isExpanded) holder.viewExpand.visible()
            else holder.viewExpand.viewExpand.gone()

            val imageRes = if (isExpanded) R.drawable.ic_arrow_up
            else R.drawable.ic_arrow_down
            holder.imageArrow.setImageResource(imageRes)
        }

        private fun toggleExpand(agenda: Agenda, holder: ViewHolder) {
            agenda.uid?.let {
                val isExpanded = !expandStates[it]
                expandStates.put(it, isExpanded)
                refreshExpandView(it, holder)
            }
            val textId = if (agenda.open) R.string.finish else R.string.reopen
            holder.button.setText(textId)
        }

        fun update(newList: List<Agenda>) {
            list.clear()
            list.addAll(newList)
            notifyDataSetChanged()
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textTitle: TextView = itemView.textTitle
            val textDescription: TextView = itemView.textDescription
            val viewExpand: View = itemView.viewExpand
            val textDetails: TextView = itemView.textDetails
            val textAuthor: TextView = itemView.textAuthor
            val button: Button = itemView.buttonToggle
            val imageArrow: ImageView = itemView.imageArrow
        }

    }

}