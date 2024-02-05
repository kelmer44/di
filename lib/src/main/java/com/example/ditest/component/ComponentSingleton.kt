/*
 * Copyright 2023 Sirius XM Holdings Inc. All rights reserved.
 */
package com.example.ditest.component

import com.example.ditest.module.UNINITIALIZED

fun <T> componentSingleton(factory: () -> T): () -> T {
 var instance: Any? = UNINITIALIZED
 return {
  if (instance === UNINITIALIZED) {
   instance = factory()
  }
  instance as T
 }
}
public val UNINTIALIZED = Any()