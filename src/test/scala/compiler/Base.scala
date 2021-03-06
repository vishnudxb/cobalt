/*
 * Cobalt Programming Language Compiler
 * Copyright (C) 2017  Cobalt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package compiler

import java.io.File

import compiler.runtime.Main
import compiler.utilities.Utils
import org.apache.log4j.PropertyConfigurator

trait Base {
   val cobaltFile = new File("src/test/resources/source/primitives/"+getClass.getSimpleName+".cobalt")
   val asmFile = new File("src/test/resources/asm/primitives/"+getClass.getSimpleName+".java")
   val buildFile = new File("src/test/resources/generated/primitives/"+getClass.getSimpleName+".class")
   val classPath = new File("src/test/resources/asm")


  def compile(): Unit ={
    PropertyConfigurator.configure("src/test/log4j.properties")
    Main.start(Array(cobaltFile, asmFile, buildFile, classPath))
  }

  /**
    * Executes with the classpath and returns the output
    */
  def executeOutput(): List[String] ={
    Utils.executionOutput("src/test/resources/generated", asmFile.getPath.replace(".java", "").replace("\\", ".").replace((classPath.getPath + "\\")replace("\\","."),""))
  }

  /**
    * Deletes all generated files
    */
  def cleanup(): Unit ={
    if(Constants.DELETE_GENERATED){
      asmFile.delete()
      buildFile.delete()
    }
  }

}
