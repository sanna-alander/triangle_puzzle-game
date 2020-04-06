package trianglepuzzle

import scala.collection.mutable.Buffer

class Piece(symbol1: Char, symbol2: Char, symbol3: Char, coordinates: Option[(Int, Int)], down: Boolean) {
  
  var symbols = Buffer[Char](symbol1, symbol2, symbol3)  // The symbols of the triangle. Their index determines their place. Index 0 is the bottom.
  
  var upsidedown: Boolean = down  // A variable that determines whether the triangle is upside down or not.
  
  var location = coordinates  // The first coordinate represents the row and the second represents the column that the piece is in. 
  
  
  def rotate = {    // Method which rotates the piece once, meaning it changes the order of the symbols which determines the rotation.
                    // Rotates the symbols clockwise, meaning that the char at place 0 will move to 1.
    
    val symbolss = this.symbols
    val last = symbolss(2)
    symbolss.remove(2)
    symbolss.prepend(last)
    
    this.symbols = symbolss
  }
  
  def flip = {  // This method flips the triangle.
    if (upsidedown) upsidedown = false else if (!upsidedown) upsidedown = true
  }
  
  def updateLocation(newLocation: Option[(Int, Int)]) = location = newLocation
  
  def equals(piece: Piece): Boolean = {
    
    val values = Buffer[Boolean]() 
    
    for (s <- 0 until 3) {
      values += this.symbols == piece.symbols
      piece.rotate
    }
    
    values.exists( _ == true ) 
  }
  
}  