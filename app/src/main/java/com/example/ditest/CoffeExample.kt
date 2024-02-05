/*
 * Copyright 2023 Sirius XM Holdings Inc. All rights reserved.
 */
package com.example.ditest

import com.example.bind.bind
import com.example.ditest.heater.ElectricHeater
import com.example.ditest.heater.Heater
import com.example.ditest.pump.Pump
import com.example.ditest.pump.Thermosiphon
import com.example.graph.ObjectGraph
import com.example.graph.get
import com.example.module.FactoryHolderModule
import com.example.module.ReflectiveModule

fun main() {
    println("Manual DI")


    val objectGraph = prepareGraph()
    val coffeMaker = objectGraph.get<CoffeMaker>()
    coffeMaker.brew()

}
private fun prepareGraph(): ObjectGraph {
    val bindingModule = FactoryHolderModule().apply {
        bind<Heater, ElectricHeater>()
        bind<Pump, Thermosiphon>()
    }

    return ObjectGraph(
        bindingModule,
        ReflectiveModule()
    )
}