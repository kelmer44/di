/*
 * Copyright 2023 Sirius XM Holdings Inc. All rights reserved.
 */
package com.example.ditest.inject

import com.example.ditest.factory.Factory
import com.example.ditest.module.Module

class InjectProcessorModule : Module {
    override fun <T> get(requestedType: Class<T>): Factory<T>? {
        val factoryClass = Class.forName("${requestedType.name}_Factory")
        val factoryConstructor = factoryClass.getDeclaredConstructor()
        return factoryConstructor.newInstance() as Factory<T>?
    }
}