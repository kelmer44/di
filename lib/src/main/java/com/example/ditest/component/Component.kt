package com.example.ditest.component

import kotlin.reflect.KClass
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class Component(
    val modules: Array<KClass<*>> = []
)