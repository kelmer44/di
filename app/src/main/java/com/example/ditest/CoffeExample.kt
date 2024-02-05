/*
 * Copyright 2023 Sirius XM Holdings Inc. All rights reserved.
 */
package com.example.ditest

import com.example.ditest.bind.bind
import com.example.ditest.heater.ElectricHeater
import com.example.ditest.heater.Heater
import com.example.ditest.maker.CoffeMaker
import com.example.ditest.pump.Pump
import com.example.ditest.pump.Thermosiphon
import com.example.ditest.graph.ObjectGraph
import com.example.ditest.graph.get
import com.example.ditest.inject.InjectProcessorModule
import com.example.ditest.module.FactoryHolderModule
import com.example.ditest.module.ReflectiveModule

val bindingModule = FactoryHolderModule().apply {
    bind<Heater, ElectricHeater>()
    bind<Pump, Thermosiphon>()
}

fun main() {
    val objectGraph = prepareGraph()
    val coffeMaker = objectGraph.get<CoffeMaker>()
    coffeMaker.brew()
}

private fun prepareGraph(): ObjectGraph {

    return ObjectGraph(
        bindingModule,
        InjectProcessorModule()
    )
}