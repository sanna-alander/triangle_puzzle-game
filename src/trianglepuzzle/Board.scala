package trianglepuzzle

import scala.collection.mutable.Buffer

class Board {
  
  val pieces = Buffer[Piece]()
  
  val empty = if (pieces.nonEmpty) false else true
  
  def addPiece(piece: Piece) = {
    this.pieces += piece
  }
  
  def removePiece(piece: Piece) = {
    ???
  }
  
}