package trianglepuzzle

import scala.collection.mutable.Buffer
import scala.util.Random

class Pile(givenPieces: Buffer[Piece]) {
  
  private val copied = Buffer[Piece]()
  
  for (i <- givenPieces) {  // Here the given pieces (usually the pieces of the correct solution) are being copied to the pile.
    val syms = i.symbols
    copied += new Piece(syms(0), syms(1), syms(2), None, i.upsidedown)
  }
  
  //Change the location of the pieces to None because in the pile the pieces don't have coordinates.

  var pieces: Buffer[Piece] = copied
  
  def remove(p: Piece) = this.pieces.remove(this.pieces.indexOf(p))
  
  def add(p: Piece) = this.pieces.append(p)
  
  def rotatePile() = {  //rotates the pile so that the player can go through the pile
    
    val piecess = this.pieces
    val first = piecess(0)
    piecess.remove(0)
    piecess.append(first)
    
    this.pieces = piecess
  }
  
  def rotateBack() = {  //rotates the pile to the other direction
    
    val piecess = this pieces
    val last = piecess(piecess.size - 1)
    piecess.remove(piecess.size - 1)
    piecess.prepend(last)
    
    this.pieces = piecess
  }
  
  
  def shuffle() = {   //shuffles the pile and changes the rotation and flip of the pieces
                    // This method is called when a new game is started and the pieces are set into a pile. 
    
    for (a <- 0 until 10) {
      val p = Random.nextInt(this.pieces.size)
      this.pieces(p).flip
    }
    
    for (a <- 0 until 20) {
      val p = Random.nextInt(this.pieces.size)
      this.pieces(p).rotate
    }
    
    for (a <- 0 until 20) {
      val p = Random.nextInt(this.pieces.size)
      val i = Random.nextInt(this.pieces.size)
      val piece1 = this.pieces(p)
      val piece2 = this.pieces(i)
      this.pieces.update(p, piece2)
      this.pieces.update(i, piece1)
    }
    
  }
}