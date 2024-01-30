package com.example.ditest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ditest.heater.ElectricHeater
import com.example.ditest.heater.Heater
import com.example.ditest.logger.CoffeLogger
import com.example.ditest.pump.Pump
import com.example.ditest.pump.Thermosiphon

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buildMachine()
    }

    private fun buildMachine() {

        val logger = CoffeLogger()
        val heater: Heater =  ElectricHeater(logger)
        val pump: Pump = Thermosiphon(logger, heater)
        val coffeMaker = CoffeMaker(logger, heater, pump)

        coffeMaker.brew()
    }
}