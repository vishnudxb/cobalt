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

package compiler.ast.packages

import compiler.ast.Block

/**
  * Represents a package
  *
  * @param directory
  */
class PackageBlock(var directory: String) extends Block(null, true, false) {

  override def getName: String = directory

  override def getValue: String = null

  override def getType: String = "<PACKAGE>"

  override def getOpeningCode: String = ""

  override def getClosingCode: String = ""

  override def toString: String = getType + directory

}