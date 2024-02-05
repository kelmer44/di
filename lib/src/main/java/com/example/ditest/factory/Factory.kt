/*
 * Copyright 2023 Sirius XM Holdings Inc. All rights reserved.
 */
package com.example.ditest.factory

import com.example.ditest.graph.ObjectGraph
import com.example.ditest.module.UNINITIALIZED

fun interface Factory<T> {
 fun get(objectGraph: ObjectGraph): T
}

fun <T> singleton(factory: Factory<T>): Factory<T> {
 var instance: Any? = UNINITIALIZED
 return Factory { linker ->
  if(instance == UNINITIALIZED){
   instance = factory.get(linker)
  }
  instance as T
 }
}
