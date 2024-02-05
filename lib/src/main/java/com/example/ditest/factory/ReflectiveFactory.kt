/*
 * Copyright 2023 Sirius XM Holdings Inc. All rights reserved.
 */
package com.example.ditest.factory

import com.example.graph.ObjectGraph
import java.lang.reflect.Constructor
import javax.inject.Inject

class ReflectiveFactory<T>(requestedType: Class<T>) : Factory<T> {
    private val injectConstructor = requestedType.constructors.single {
        it.isAnnotationPresent(Inject::class.java)
    } as Constructor<T>

    override fun get(objectGraph: ObjectGraph): T {
        val parameters = injectConstructor.parameterTypes.map { paramType ->
            objectGraph[paramType]
        }.toTypedArray()
        return injectConstructor.newInstance(*parameters)
    }

}