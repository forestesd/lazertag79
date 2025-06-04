package com.example.comon.server.domain

import com.example.comon.models.TaggerInfo

interface ServerViewModelInterface {
    fun update(newData: TaggerInfo)
}