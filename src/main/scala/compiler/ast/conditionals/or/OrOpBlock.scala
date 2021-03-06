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

package compiler.ast.conditionals.or

import compiler.ast.Block
import compiler.ast.conditionals.ConditionalBlock

/**
  * Represents or - "||"
  *
  * @param superBlockInit The parent block
  */
class OrOpBlock(var superBlockInit: Block) extends ConditionalBlock(superBlockInit, false, false) {

  override def getName: String = ""

  override def getValue: String = ""

  override def getType: String = "<OR_OP>"

  def getOpeningCode: String = {
    ""
  }

  def getClosingCode: String = {
    ""
  }

  override def toString: String = getType

}