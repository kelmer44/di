/*
 * Copyright 2023 Sirius XM Holdings Inc. All rights reserved.
 */
package com.example.ditest.bind

import com.example.ditest.module.FactoryHolderModule

inline fun <reified REQUESTED, reified PROVIDED : REQUESTED> FactoryHolderModule.bind() {
 install(REQUESTED::class.java) { objectGraph ->
  objectGraph[PROVIDED::class.java]
 }
}