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

package compiler.symbol_table


class Row {
  private var id: Int = 0
  private var blockType: String = ""
  private var name: String = ""
  private var value: String = ""
  private var methodName: String = ""
  private var className: String = ""
  private var immutable: Boolean = false

  def getId: Int = id

  def setId(id: Int): Row = {
    this.id = id
    this
  }

  def getType: String = blockType

  def setType(blockType: String): Row = {
    this.blockType = blockType
    this
  }

  def getName: String = name

  def setName(name: String): Row = {
    this.name = name
    this
  }

  def getValue: String = value

  def setValue(value: String): Row = {
    this.value = value
    this
  }

  def getMethodName: String = methodName

  def setMethodName(methodName: String): Row = {
    this.methodName = methodName
    this
  }

  def getClassName: String = className

  def setClassName(className: String): Row = {
    this.className = className
    this
  }

  def getImmutable: Boolean = immutable

  def setImmutable(immutable : Boolean) : Row = {
    this.immutable = immutable
    this
  }

  override def toString: String = {
    id + " : " + name + " : " + blockType + " : " + value + " " + methodName + " " + className
  }
}