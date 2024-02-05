/*
 * Copyright 2023 Sirius XM Holdings Inc. All rights reserved.
 */
package com.example.ditest.factory

import com.example.graph.ObjectGraph

fun interface Factory<T> {
 fun get(objectGraph: ObjectGraph): T
}