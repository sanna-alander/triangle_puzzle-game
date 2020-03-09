package trianglepuzzle

import scala.collection.mutable.Buffer
import scala.util.Random

class Game(val board: Board) {
  
  val pieces = Buffer[Piece]()
  
  private def randomSymbol: Char = {
    val symbols = Vector('a', 'b', 'c', 'A', 'B', 'C')
    symbols(Random.nextInt(6))
  }
  
  private def generateRandomPiece: Piece = {
    new Piece(randomSymbol, randomSymbol, randomSymbol)
  }
  
  private var solution: Board = ???
  
  def generateSolution = ???
  
  def hasEnded = this.pieces == this.solution.pieces
  
  def startGame = ???
  
}