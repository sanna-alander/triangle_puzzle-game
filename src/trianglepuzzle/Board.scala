package trianglepuzzle

import scala.collection.mutable.Buffer

class Board(givenPieces: Buffer[Piece]) {
  
  private var currentPieces = givenPieces
  
  def pieces: Buffer[Piece] = this.currentPieces
  
  def empty() = { // this method empties the board.
    currentPieces = Buffer[Piece]()
  }
  
  def addPiece(piece: Piece) = {
    this.currentPieces += piece
  }
  
  def removePiece(piece: Piece) = {
    this.currentPieces.remove(this.currentPieces.indexOf(piece))
  }
  
  def getPiece(coords: (Int, Int)): Option[Piece] = {   // Method that returns a piece at a certain location wrapped in an option.
    this.currentPieces.find( _.location == Option(coords))
  }
  
  def equals(another: Board): Boolean = { //checks if two boards have the same pieces at same locations
    val coords = Vector((1,1),(1,2),(1,3),(1,4),(1,5),(2,1),(2,2),(2,3),(2,4),(2,5),(2,6),(2,7),(3,1),(3,2),(3,3),(3,4),(3,5),(3,6),(3,7),(4,1),(4,2),(4,3),(4,4),(4,5))
    val values = Buffer[Boolean]()
    
    for (i <- coords) {
      values += (this.getPiece(i).get.specificEquals(another.getPiece(i).get)) && (this.getPiece(i).get.upsidedown == another.getPiece(i).get.upsidedown)
    }
    values.forall(_ == true)
  }
  
  def allDifferent: Boolean = {  //Checks if all of the pieces in this board are different.
    
    val values = Buffer[Boolean]()
    var pieces2 = this.pieces
    
    for (i <- this.pieces) {
      
      pieces2.remove(this.pieces.indexOf(i))
      values += pieces2.exists( _.equals(i) )
      pieces2.append(i)
      
    }
    values.foreach(println(_))
    println(values.forall( _ == false ))
    values.forall( _ == false )
  }
  
}