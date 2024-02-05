/*
 * Copyright 2023 Sirius XM Holdings Inc. All rights reserved.
 */
@file:OptIn(KspExperimental::class, KspExperimental::class)

package com.example.injectprocessor

import append
import appendLine
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.validate
import javax.inject.Inject
import javax.inject.Singleton

class InjectProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return InjectProcessor(environment.codeGenerator)
    }
}

class InjectProcessor(
    val codeGenerator: CodeGenerator
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val annotatedSymbols = resolver.getSymbolsWithAnnotation(Inject::class.java.name)
        val unprocessedSymbols = annotatedSymbols.filter { !it.validate() }.toList()
        annotatedSymbols
            .filter { it is KSFunctionDeclaration && it.validate() }
            .forEach { it.accept(InjectConstructorVisitor(), Unit) }
        return unprocessedSymbols
    }

    inner class InjectConstructorVisitor : KSVisitorVoid() {
        override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
            val injectedClass = function.parentDeclaration as KSClassDeclaration
            val injectedClassSimpleName = injectedClass.simpleName.asString()
            val packageName = injectedClass.containingFile!!.packageName.asString()
            val className = "${injectedClassSimpleName}_Factory"
            codeGenerator.createNewFile(
                Dependencies(true, function.containingFile!!), packageName, className
            ).use { ktFile ->
                ktFile.appendLine("package $packageName")
                ktFile.appendLine()
                ktFile.appendLine("import com.example.ditest.factory.Factory")
                ktFile.appendLine("import com.example.ditest.graph.ObjectGraph")

                if(injectedClass.isAnnotationPresent(Singleton::class)) {
                    ktFile.appendLine("import com.example.ditest.factory.singleton")
                }

                if(function.parameters.isNotEmpty()){
                    ktFile.appendLine("import com.example.ditest.graph.get")
                }

                ktFile.appendLine()
                ktFile.appendLine("class $className : Factory<$injectedClassSimpleName> {")

                val constructorInvocation = "${injectedClassSimpleName}(" + function.parameters.joinToString(", ") {
                    "objectGraph.get()"
                } + ")"

                if(injectedClass.isAnnotationPresent(Singleton::class)){
                    val linkerParameter = if(function.parameters.isNotEmpty()) "objectGraph ->" else ""
                    ktFile.appendLine(" private val singletonFactory = singleton { $linkerParameter")
                    ktFile.appendLine("     $constructorInvocation")
                    ktFile.appendLine("     }")
                    ktFile.appendLine()
                    ktFile.appendLine(
                        "   override fun get(objectGraph: ObjectGraph) = singletonFactory.get(objectGraph)"
                    )
                } else {
                    ktFile.appendLine(
                        "   override fun get(objectGraph: ObjectGraph) = $constructorInvocation"
                    )
                }
                ktFile.appendLine("}")
            }
        }
    }

}