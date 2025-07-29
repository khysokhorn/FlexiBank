package com.nexgen.core.factory

interface Factory<T> {
    fun create(): T
}
