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

package compiler.parser.packages

import compiler.block.Block
import compiler.block.packages.PackageBlockTest
import compiler.parser.ParserTest
import compiler.tokenizer.TokenizerTest

class PackageParserTest extends ParserTest[PackageBlockTest] {

  def shouldParse(line: String): Boolean = line.matches("package [a-zA-Z][a-zA-Z0-9]*(\\.[a-zA-Z][a-zA-Z0-9]*)*")

  def parse(superBlock: Block, tokenizer: TokenizerTest): PackageBlockTest = {
    tokenizer.nextToken
    // skip "package"
    var directory: String = tokenizer.nextToken.token
    // Get the string value of the next token.;
    var nextToken: String = tokenizer.nextToken.token
    while (nextToken != "") {
      {
        if (nextToken == ".") {
          directory += "/"
        }
        else {
          directory += nextToken
        }
        nextToken = tokenizer.nextToken.token
      }
    }
    return new PackageBlockTest(directory)
  }
}