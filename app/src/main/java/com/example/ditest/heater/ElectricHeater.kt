/*
 * Copyright 2023 Sirius XM Holdings Inc. All rights reserved.
 */
package com.example.ditest.heater

import com.example.ditest.logger.CoffeLogger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ElectricHeater @Inject constructor(logger: CoffeLogger): Heater {
}