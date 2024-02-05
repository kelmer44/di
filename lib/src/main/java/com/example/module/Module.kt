/*
 * Copyright 2023 Sirius XM Holdings Inc. All rights reserved.
 */
package com.example.module

import com.example.ditest.factory.Factory

interface Module {

    operator fun <T> get(requestedType: Class<T>): Factory<T>?
}