package trianglepuzzle

import scala.collection.mutable.Buffer

class Pile(val game: Game) {
  
    
  val pieces = game.correctSolution.pieces
  
  def remove(p: Piece) = this.pieces.remove(this.pieces.indexOf(p))
  
  def add(p: Piece) = this.pieces.append(p)
  
  
  def shuffle = ???
}