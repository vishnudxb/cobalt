/*
 * Cobalt Programming Language Compiler
 * Copyright (C) 2017  Michael Haywood
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

package compiler.parser.structures.methods

import compiler.block.Block
import compiler.block.structures.methods.MethodBlock
import compiler.parser.Parser
import compiler.structure.parameters.Parameters
import compiler.tokenizer.Tokenizer

class MethodParser extends Parser[MethodBlock] {

  def shouldParse(line: String): Boolean = line.matches("def[ ]+[a-zA-Z][a-zA-Z0-9]*[ ]*\\((.*)*\\)[ ]*<-[ ]*[a-zA-Z][a-zA-Z0-9]*[ ]*:")

  def parse(superBlock: Block, tokenizer: Tokenizer): MethodBlock = {

    tokenizer.nextToken // skip "def"
    val name: String = tokenizer.nextToken.token // method name
    tokenizer.nextToken // "("
    var nextToken = tokenizer.nextToken.token
    var paramString = ""
    while (!nextToken.equals(")")) {
      paramString += nextToken
      nextToken = tokenizer.nextToken.token
    }

    val parameters = new Parameters().getParameters(paramString)

    tokenizer.nextToken // skip "<"

    tokenizer.nextToken // skip "-"

    val returnType: String = tokenizer.nextToken.token // method return type
    return new MethodBlock(superBlock, name, returnType, parameters.toArray)
  }
}
