package com.nexgen.domain.repository

interface ResultLivenessRepository<RESULT> {
    fun success(photoResult: RESULT, filesPath: List<String>)
    fun error(exception: Exception)
}
