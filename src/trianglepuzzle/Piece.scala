package trianglepuzzle

import scala.collection.mutable.Buffer

class Piece(symbol1: Char, symbol2: Char, symbol3: Char) {
  
  var coordinates = ???
  
  val symbols = Buffer[Char](symbol1, symbol2, symbol3)
  
  var upsidedown: Boolean = false
  
  private var location = ???
  
  
  def rotate = {    // Method which rotates the piece, meaning it changes the order of the symbols which determines the rotation.
                    // Rotates the symbols clockwise, meaning that the char at place 0 will move to 1.
    
    for (s <- 0 until 3) {   
      if (s == 0) this.symbols.update(s, this.symbols(2))
      if (s == 1) this.symbols.update(s, this.symbols(0))
      if (s == 2) this.symbols.update(s, this.symbols(1))
    }
  }
  
  def flip = {
    if (upsidedown) upsidedown = false else if (!upsidedown) upsidedown = true
  }
  
  def movePiece = ???
  
}