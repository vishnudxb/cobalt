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

package compiler.utilities

import java.io.{BufferedReader, File, InputStreamReader}

import compiler.ast.empty.EmptyBlock
import compiler.ast.imports.ImportBlock
import compiler.ast.packages.PackageBlock
import compiler.ast.structures.FileBlock
import compiler.ast.structures.kinds.{ClassBlock, ObjectBlock}
import compiler.ast.structures.methods.{ConstructorBlock, MethodBlock}
import compiler.ast.{Block, Parsers}
import compiler.tokenizer.Tokenizer

import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex

object Utils extends Meta[Base]{

  def meta = Utils

 // val logger: Logger = LoggerFactory.getLogger(getClass)

  /**
    * Returns the method a blocks is within
    *
    * @param block
    * @return
    */
  def getClass(block: Block): Option[Block] = {
    var result: Block = block
    while (!(result.isInstanceOf[ClassBlock] || result.isInstanceOf[ObjectBlock])) {
      {
        if (block.superBlock == null) {
          return Option.apply(new EmptyBlock())
        }
        result = result.superBlock
        if (result == null)
          return Option.apply(result)
      }
    }
    Option.apply(result)
  }

  /**
    * Returns the method a blocks is within
    *
    * @param block
    * @return
    */
  def getMethod(block: Block): Option[Block] = {
    var result: Block = block
    while (!(result.isInstanceOf[MethodBlock] || result.isInstanceOf[ConstructorBlock])) {
      {
        if (block.superBlock == null) {
          return Option.apply(new EmptyBlock())
        }
        result = result.superBlock
        if (result == null)
          return Option.apply(result)
      }
    }
    Option.apply(result)
  }

  /**
    * Gets the PackageBlock
    *
    * @param block
    * @return
    */
  def packageBlock(block: Block): PackageBlock = Utils.getFileBlock(block).subBlocks.find(_.isInstanceOf[PackageBlock]).getOrElse(new PackageBlock("")).asInstanceOf[PackageBlock]

  /**
    * Gets the directory of the class using the Imports. Otherwise assumes class is  in the same package
    * @param block
    * @return
    */
  def getDirectory(blockInit: Block, className: String): String = {
    // Get the FileBlock to find the imports
    val block = getFileBlock(blockInit)

    // Get the directory of the Object
    for (sub <- block.subBlocks) {
      sub match {
        case block1: ImportBlock if block1.fileName == className =>
          return block1.directory
        case _ =>
      }
    }
    ""
  }

  /**
    * Gets the directory of the class using the package. Otherwise assumes class is  in the same package
    * @return
    */
  def getPackage(blockInit: Block): String = {
    // Get the FileBlock to find the imports
    var block: Block = blockInit
    while (!block.isInstanceOf[FileBlock]) {
      {
        block = block.superBlock
      }
    }
    // Get the directory of the Object
    for (sub <- block.subBlocks) {
      sub match {
        case block1: PackageBlock =>
          return block1.directory
        case _ =>
      }
    }
    ""
  }

  /**
    * Returns whether a int value, string value, or variable reference
    * int value = 0
    * string value = 1
    * variable ref = 2
    *
    * @param name
    * @return
    */
  def getType(name: String): Int = {

    if (name.charAt(0).isDigit) return 0

    if (name.startsWith("\"")) return 1

    2
  }

  /**
    * Returns the indentation of the blocks
    *
    * @param line
    * @return
    */
  def getIndentation(line: String): Int = {
    var amount: Int = 0
    var indentation: Int = 0
    for (character <- line.toCharArray) {
      if (character != ' ') return indentation
      else {
        amount += 1
        if (amount % 4 == 0) indentation += 1
      }
    }
    indentation
  }

  /**
    * Prints blocks information
    *
    * @param block
    * @param indentation
    */
  def printBlockInfo(block: Block, indentation: Int = 0) {
    var indentationString: String = ""
    var i: Int = 0
    while (i < indentation) {
      {
        indentationString += "    "
      }
      {
        i += 1
        i - 1
      }
    }
    logger.debug(indentationString + block.toString)
    for (sub <- block.subBlocks) {
      printBlockInfo(sub, indentation + 1)
    }
  }

  def isClass(blockInit : Block) : Boolean = {

      var block: Block = blockInit
      while (!block.isInstanceOf[ClassBlock] && !block.isInstanceOf[ObjectBlock]) {
        block = block.superBlock
      }

      if (block.isInstanceOf[ClassBlock]) {
        true
      }else {
        false
      }

  }

  /**
    * Returns a list of all files in the directory
    * @param File directory
    * @return
    */
  def recursiveListFiles(f: File, r: Regex): Array[File] = {
    val these = f.listFiles
    val good = these.filter(f => r.findFirstIn(f.getName).isDefined)
    good ++ these.filter(_.isDirectory).flatMap(recursiveListFiles(_,r))
  }

  /**
    * Gets an array of blocks from a string and sets the superblock
    *
    * @param superBlock
    * @param line
    * @return
    */
  def getBlocksAndStack(superBlock: Block, line: String, lineNumber: Int = 0): Block = {

    val result: ListBuffer[Block] = ListBuffer[Block]()

    var previousLineLeft = ""
    // var tuple = null
    var lineLeft: String = line

    while (lineLeft != "" && lineLeft != previousLineLeft) {
      var found = false
      previousLineLeft = lineLeft

      for (parser <- Parsers.parsers) {
        if (!found) {

          lineLeft = lineLeft.trim

          if (parser.shouldParse(lineLeft)) {


            // Get the regex that matched
            val regex: String = parser.getRegexs.find(_.r.findFirstIn(lineLeft).nonEmpty).getOrElse("")

            // Get the section of the line that matched the regex
            val first: String = regex.r.findFirstIn(lineLeft).getOrElse("").trim

            // If the line started with the section then parse it
            if (lineLeft.trim.startsWith(first)) {
              found = true

              if (result.nonEmpty) {
                result.head.stack += parser.parse(result.head, new Tokenizer(first))
              } else {
                result += parser.parse(superBlock, new Tokenizer(first))
              }
              lineLeft = lineLeft.substring(first.length)

            }
          }
        }
      }
      if (!found) {
        throw new RuntimeException("Error parsing: '" + Utils.getFileBlock(superBlock) + "' '" + line.trim + "' section: '" + lineLeft + "' Line:" + lineNumber)
      }
    }

    result.head

  }

  def getAllBlocks(superBlock: Block, line: String, lineNumber: Int = 0): List[Block] = {

    val result: ListBuffer[Block] = ListBuffer[Block]()

    var previousLineLeft = ""
    // var tuple = null
    var lineLeft: String = line

    while (lineLeft != "" && lineLeft != previousLineLeft) {
      var found = false
      previousLineLeft = lineLeft

      for (parser <- Parsers.parsers) {
        if (!found) {


          if (parser.shouldParse(lineLeft)) {


            // Get the regex that matched
            val regex: String = parser.getRegexs.find(_.r.findFirstIn(lineLeft).nonEmpty).getOrElse("")

            // Get the section of the line that matched the regex
            val first: String = regex.r.findFirstIn(lineLeft).getOrElse("").trim

            // If the line started with the section then parse it
            if (lineLeft.trim.startsWith(first)) {
              found = true


              result += parser.parse(superBlock, new Tokenizer(first))

              lineLeft = lineLeft.substring(first.length).trim

            }
          }
        }
      }
      if (!found) {
        throw new RuntimeException("Error parsing: '" + Utils.getFileBlock(superBlock) + "' '" + line.trim + "' section: '" + lineLeft + "' Line:" + lineNumber)
      }
    }


      result.toList

  }

  def getFileBlock(blockInit: Block): Block = {

    val fileBlock: Block = {
      var block: Block = blockInit
      while (!block.isInstanceOf[FileBlock]) {
        block = block.superBlock
      }
      block
    }
    fileBlock
  }

  def getWrapperType(varType: String): String ={
    varType match {
      case "Byte" => "Ljava/lang/Byte;"
      case "Short" => "Ljava/lang/Short;"
      case "Int" => "Ljava/lang/Integer;"
      case "Long" =>"Ljava/lang/Long;"
      case "Float" => "Ljava/lang/Float;"
      case "Double" => "Ljava/lang/Double;"
      case "Char" => "Ljava/lang/Character;"
      case _ => "Ljava/lang/Object;"
    }
  }

  def getASMType(varType: String): String = {
    varType match {
      case "byte" => "B"
      case "short" => "S"
      case "int" => "I"
      case "long" => "J"
      case "float" => "F"
      case "double" => "D"
      case "char" => "C"
      case "Byte" => "I"
      case "Short" => "I"
      case "Int" => "I"
      case "Long" => "J"
      case "Float" => "F"
      case "Double" => "D"
      case "Char" => "C"
      case "String" => "Ljava/lang/String;"
      case _ => "Ljava/lang/Object;"
    }
  }


  /**
    * Executes cmd "java" with the classpath and file location and returns the output
    *
    * @param classPath
    * @param directory
    * @return process output
    */
  def executionOutput(classPath: String, directory: String): List[String] = {
    val result = new ListBuffer[String]
    val runtime = Runtime.getRuntime
    val command = "java -cp " + classPath + " " + directory
    val process = runtime.exec(command)
    val input = new BufferedReader(new InputStreamReader(process.getInputStream()))
     var line = input.readLine()
    while (line != null) {
      result += line
      line = input.readLine()
    }
    input.close()

    result.toList
  }

  /**
    * Escapes a String
    *
    * @param raw
    * @return
    */
  def escapeString(raw: String): String = {
    import scala.reflect.runtime.universe._
    Literal(Constant(raw)).toString
  }

}
