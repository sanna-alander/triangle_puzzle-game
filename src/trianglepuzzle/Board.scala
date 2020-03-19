package trianglepuzzle

import scala.collection.mutable.Buffer

class Board(givenPieces: Buffer[Piece]) {
  
  private var currentPieces = givenPieces
  
  def pieces = this.currentPieces
  
  // var empty = emptiness
  
  /*def clearBoard = {
    empty = true
  }*/
  
  def addPiece(piece: Piece) = {
    this.currentPieces += piece
  }
  
  def removePiece(piece: Piece) = {
    this.currentPieces.remove(this.currentPieces.indexOf(piece))
  }
  
  def getPiece(coords: (Int, Int)): Option[Piece] = {   // Method that returns a piece at a certain location wrapped in an option.
    this.currentPieces.find( _.location == Option(coords))
  }
  
}