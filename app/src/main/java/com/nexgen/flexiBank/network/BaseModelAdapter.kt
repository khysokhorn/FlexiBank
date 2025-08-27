package com.nexgen.flexiBank.network

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.nexgen.flexiBank.model.BaseModel
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
class BaseModelAdapterFactory : TypeAdapterFactory {
    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        if (type.rawType != BaseModel::class.java) {
            return null
        }
        val dataType = (type.type as ParameterizedType).actualTypeArguments[0]
        @Suppress("UNCHECKED_CAST")
        return BaseModelAdapter<Any>(gson, dataType) as TypeAdapter<T>
    }
}

private class BaseModelAdapter<T>(
    private val gson: Gson,
    private val dataType: Type
) : TypeAdapter<BaseModel<T>>() {

    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: BaseModel<T>?) {
    }

    @Throws(IOException::class)
    override fun read(reader: JsonReader): BaseModel<T>? {
        val data = gson.fromJson<T>(reader, dataType)
        return BaseModel(data, 200)
    }
}
