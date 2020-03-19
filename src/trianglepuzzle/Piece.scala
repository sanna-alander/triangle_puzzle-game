package trianglepuzzle

import scala.collection.mutable.Buffer

class Piece(symbol1: Char, symbol2: Char, symbol3: Char, coordinates: Option[(Int, Int)], down: Boolean) {
  
  val symbols = Buffer[Char](symbol1, symbol2, symbol3)  // The symbols of the triangle. Their index determines their place. Index 0 is the bottom.
  
  var upsidedown: Boolean = down  // A variable that determines whether the triangle is upside down or not.
  
  var location = coordinates  // The first coordinate represents the row and the second represents the column that the piece is in. 
  
  
  def rotate = {    // Method which rotates the piece once, meaning it changes the order of the symbols which determines the rotation.
                    // Rotates the symbols clockwise, meaning that the char at place 0 will move to 1.
    
    for (s <- 0 until 3) {   
      if (s == 0) this.symbols.update(s, this.symbols(2))
      if (s == 1) this.symbols.update(s, this.symbols(0))
      if (s == 2) this.symbols.update(s, this.symbols(1))
    }
  }
  
  def rotateTimes(times: Int) {
    ???
  }
  
  def flip = {  // This method flips the triangle.
    if (upsidedown) upsidedown = false else if (!upsidedown) upsidedown = true
  }
  
  def movePiece = ???
  
}