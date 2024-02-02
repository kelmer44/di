/*
 * Copyright 2023 Sirius XM Holdings Inc. All rights reserved.
 */
package com.example.ditest.graph

import javax.inject.Singleton

class ReflectiveModule : Module {
    override fun <T> get(requestedType: Class<T>): Factory<T> {
        val reflectiveFactory = ReflectiveFactory(requestedType)
        return if (requestedType.isAnnotationPresent(Singleton::class.java)) {
            singleton(reflectiveFactory)
        } else {
            reflectiveFactory
        }
    }
}