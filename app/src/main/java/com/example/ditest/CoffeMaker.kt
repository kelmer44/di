/*
 * Copyright 2023 Sirius XM Holdings Inc. All rights reserved.
 */
package com.example.ditest

import android.util.Log
import com.example.ditest.graph.Factory
import com.example.ditest.graph.get
import com.example.ditest.heater.Heater
import com.example.ditest.logger.CoffeLogger
import com.example.ditest.pump.Pump
import javax.inject.Inject

class CoffeMaker @Inject constructor(logger: CoffeLogger, heater: Heater, pump: Pump) {

    fun brew() {
        Log.i("COFFE", "Brewing coffee!")
    }

}

val coffeMakerFactory = Factory { objectGraph ->
    CoffeMaker(objectGraph.get(), objectGraph.get(), objectGraph.get())
}