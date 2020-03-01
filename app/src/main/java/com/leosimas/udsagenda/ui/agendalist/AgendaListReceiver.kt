package com.leosimas.udsagenda.ui.agendalist

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

abstract class AgendaListReceiver : BroadcastReceiver() {

    companion object {
        private const val ACTION_REFRESH = "refresh"

        fun sendRefresh(context: Context) {
            context.sendBroadcast(Intent(ACTION_REFRESH))
        }

        fun register(context: Context?, receiver: AgendaListReceiver) {
            context?.registerReceiver(receiver, IntentFilter().apply {
                addAction(ACTION_REFRESH)
            })
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (ACTION_REFRESH == intent?.action) {
            onRefresh()
        }
    }

    abstract fun onRefresh()
}