/*
 * Copyright 2023 Sirius XM Holdings Inc. All rights reserved.
 */
package com.example.ditest.module

import com.example.ditest.factory.Factory
import com.example.ditest.factory.ReflectiveFactory
import com.example.ditest.factory.singleton
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