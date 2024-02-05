/*
 * Copyright 2023 Sirius XM Holdings Inc. All rights reserved.
 */
package com.example.injectprocessor

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
            ).use {

            }
        }
    }

}