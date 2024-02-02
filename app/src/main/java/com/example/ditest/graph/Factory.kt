/*
 * Copyright 2023 Sirius XM Holdings Inc. All rights reserved.
 */
package com.example.ditest.graph

fun interface Factory<T> {
 fun get(objectGraph: ObjectGraph): T
}