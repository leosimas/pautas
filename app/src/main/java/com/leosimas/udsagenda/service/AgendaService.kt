package com.leosimas.udsagenda.service

object AgendaService {

    private fun fakeRequestTime() {
        Thread.sleep(2000)
    }

    fun login(email: String, password: String) : Response {
        fakeRequestTime()
        if ("123456" != password) return Response(false, ErrorCode.WRONG_PASSWORD)
        return Response(true, null)
    }

}