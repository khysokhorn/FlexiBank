package com.nexgen.core.resourceprovider

import android.content.Context
import com.nexgen.core.factory.Factory

class ResourcesProviderFactory(private val context: Context) : Factory<ResourcesProvider> {
    override fun create(): ResourcesProvider {
        return ResourcesProviderImpl(context)
    }
}
