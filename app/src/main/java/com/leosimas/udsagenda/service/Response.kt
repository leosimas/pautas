package com.leosimas.udsagenda.service

class Response(val success: Boolean,
               val error: ErrorCode?) {

    companion object {
        val SUCCESS = Response(true, null)
    }

}