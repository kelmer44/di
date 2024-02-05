/*
 * Copyright 2023 Sirius XM Holdings Inc. All rights reserved.
 */
package com.example.ditest

import com.example.ditest.component.Binds
import com.example.ditest.component.Component
import com.example.ditest.heater.ElectricHeater
import com.example.ditest.heater.Heater
import com.example.ditest.maker.CoffeMaker
import com.example.ditest.pump.Pump
import com.example.ditest.pump.Thermosiphon

@Component(modules = [CoffeeBindsModule::class])
interface CoffeeComponent {
    val coffeMaker: CoffeMaker
}

interface CoffeeBindsModule {
    @Binds fun bindHeater(heater: ElectricHeater) : Heater
    @Binds fun bindPump(pump: Thermosiphon): Pump
}