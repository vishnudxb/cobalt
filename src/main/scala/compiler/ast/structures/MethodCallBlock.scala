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

package compiler.ast.structures

import compiler.ast.Block
import compiler.data.parameters.Parameter

/**
  * Represents calling a method within a class
  */
class MethodCallBlock(var superBlockInit: Block, var name: String, var `type`: String, var params: Array[Parameter]) extends Block(superBlockInit, false, false) {

  def getParameters: Array[Parameter] = params

  override def getName: String = name

  override def getValue: String = ""

  override def getType: String = `type`

  override def getOpeningCode: String = name + "();"

  override def getClosingCode: String = ""

  override def toString: String = {
    var paramString: String = ""
    if (params != null)
    for (parameter <- params) {
      paramString += parameter.getType + ":" + parameter.getName + "; "
    }
    "method call: " + name + " ( " + paramString + ")"
  }
}