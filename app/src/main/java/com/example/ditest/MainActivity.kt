package com.example.ditest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ditest.graph.Factory
import com.example.ditest.graph.FactoryHolderModule
import com.example.ditest.graph.ObjectGraph
import com.example.ditest.graph.get
import com.example.ditest.graph.install
import com.example.ditest.graph.installSingleton
import com.example.ditest.heater.ElectricHeater
import com.example.ditest.heater.Heater
import com.example.ditest.logger.CoffeLogger
import com.example.ditest.pump.Pump
import com.example.ditest.pump.Thermosiphon

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val objectGraph = prepareGraph()
        val coffeMaker = objectGraph.get<CoffeMaker>()
        coffeMaker.brew()
    }

    private fun prepareGraph(): ObjectGraph {
        val module = FactoryHolderModule()

        module.installSingleton {
            CoffeLogger()
        }
        module.installSingleton {
            ElectricHeater(get())
        }
        module.install {
            Thermosiphon(get(), get())
        }
        module.install {
            CoffeMaker(get(), get(), get())
        }
        module.bind<Heater, ElectricHeater>()
        module.bind<Pump, Thermosiphon>()
        return ObjectGraph(module)
    }

    inline fun <reified REQUESTED, reified PROVIDED : REQUESTED> FactoryHolderModule.bind() {
        install(REQUESTED::class.java) { objectGraph ->
            objectGraph[PROVIDED::class.java]
        }
    }
}