package trianglepuzzle

import scala.collection.mutable.Buffer
import scala.util.Random

class Pile(givenPieces: Buffer[Piece]) {
  
  val copied = Buffer[Piece]()
  
  for (i <- givenPieces) {
    val syms = i.symbols
    copied += new Piece(syms(0), syms(1), syms(2), None, i.upsidedown)
  }
  
  //copied.foreach( _.updateLocation(None)) //Change the location of the pieces to None because in the pile the pieces don't have coordinates.

  var pieces: Buffer[Piece] = copied
  
  def remove(p: Piece) = this.pieces.remove(this.pieces.indexOf(p))
  
  def add(p: Piece) = this.pieces.append(p)
  
  def rotatePile = {  //rotates the pile so that the player can go through the pile
    
    val piecess = this.pieces
    val first = piecess(0)
    piecess.remove(0)
    piecess.append(first)
    
    this.pieces = piecess
  }
  
  
  def shuffle = {   //shuffles the pile and changes the rotation and flip of the pieces
    
    for (a <- 0 until 10) {
      val p = Random.nextInt(this.pieces.size)
      this.pieces(p).flip
    }
    
    for (a <- 0 until 20) {
      val p = Random.nextInt(this.pieces.size)
      this.pieces(p).rotate
    }
    this.pieces.reverse
    
  }
}