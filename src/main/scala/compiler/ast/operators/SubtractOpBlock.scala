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

package compiler.ast.operators

import compiler.ast.Block
import compiler.utilities.Utils

/**
  * Represents a subtract operator
  *
  * @param superBlockInit
  */
class SubtractOpBlock(var superBlockInit: Block) extends Block(superBlockInit, false, false) {

  override def getName: String = ""

  override def getValue: String = ""

  override def getType: String = "<SUBTRACT_OP>"

  override def getOpeningCode: String = {
    "mv.visitInsn(" + Utils.getASMType(superBlockInit.getType) + "SUB);"
  }

  override def getClosingCode: String = {
    ""
  }

  override def toString: String = {
    getType
  }




}