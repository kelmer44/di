/*
 * Copyright 2023 Sirius XM Holdings Inc. All rights reserved.
 */
package com.example.ditest.module

import com.example.ditest.factory.Factory
import com.example.ditest.factory.singleton
import com.example.ditest.graph.ObjectGraph

class FactoryHolderModule : Module {
    private val factories = mutableMapOf<Class<out Any?>, Factory<out Any?>>()
    override fun <T> get(requestedType: Class<T>): Factory<T>? =
        factories[requestedType] as Factory<T>?

    fun <T> install(
        requestedType: Class<T>,
        factory: Factory<T>
    ) {
        factories[requestedType] = factory
    }
}

inline fun <reified T> FactoryHolderModule.install(
    noinline factory: ObjectGraph.() -> T
) = install(T::class.java, factory)

inline fun <reified T> FactoryHolderModule.installSingleton(
    noinline factory: ObjectGraph.() -> T
) {
    install(T::class.java, singleton(factory))
}

public val UNINITIALIZED = Any()