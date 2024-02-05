/*
 * Copyright 2023 Sirius XM Holdings Inc. All rights reserved.
 */
package com.example.ditest.maker

import com.example.ditest.factory.Factory
import com.example.ditest.graph.get
import com.example.ditest.heater.Heater
import com.example.ditest.logger.CoffeLogger
import com.example.ditest.pump.Pump
import javax.inject.Inject

class CoffeMaker @Inject constructor(logger: CoffeLogger, heater: Heater, pump: Pump) {

    fun brew() {
        println("Brewing coffee!!")
    }
}

val coffeMakerFactory = Factory { objectGraph ->
    CoffeMaker(objectGraph.get(), objectGraph.get(), objectGraph.get())
}