/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.codegen

import org.jetbrains.kotlin.test.ConfigurationKind




class SpecializationTest : CodegenTestCase() {

    fun testSpec() {
        var a: IntArray = IntArray(10) { it }
        a.last()
        var b: Array<Int> = Array(10) {it}
        
        val sourceText = """
//            class B { var x: Int? = null } 
            class A<T> { var x: T? = null }
        
        """.trimIndent()
        createEnvironmentWithMockJdkAndIdeaAnnotations(ConfigurationKind.JDK_ONLY)
        myFiles = CodegenTestFiles.create("App.kt", sourceText, myEnvironment!!.project)
        val classes = generateClassesInFile().asList()
        println(classes)
        val classFileForObjects = classes.filter { it.relativePath == "A.class" || it.relativePath == "B.class"}
        println(classFileForObjects[0].asText())
//        println(classFileForObjects[1].asText())
    }
}
