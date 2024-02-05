/*
 * Copyright 2023 Sirius XM Holdings Inc. All rights reserved.
 */
package com.example.ditest.pump

import com.example.ditest.logger.CoffeLogger
import com.example.ditest.heater.Heater
import javax.inject.Inject

class Thermosiphon @Inject constructor(logger: CoffeLogger, heater: Heater) : Pump