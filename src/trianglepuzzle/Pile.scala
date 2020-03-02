package trianglepuzzle

import scala.collection.mutable.Buffer

class Pile {
  
  val pieces = Buffer[Piece]()
  
  def remove(p: Piece) = this.pieces.remove(this.pieces.indexOf(p))
  
  def add(p: Piece) = this.pieces.append(p)
  
  
  def shuffle = ???
}