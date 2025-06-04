package com.nexgen.flexiBank.repository

import com.nexgen.flexiBank.network.ApiInterface

class AppRepository(
    private val api: ApiInterface,
) : BaseRepository() {
}
