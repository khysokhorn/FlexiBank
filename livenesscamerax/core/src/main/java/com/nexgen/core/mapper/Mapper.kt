package com.nexgen.core.mapper

interface Mapper<IN, OUT> {
    fun map(item: IN): OUT
}
