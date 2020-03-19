package trianglepuzzle

import scala.collection.mutable.Buffer
import scala.util.Random

class Game(val board: Board) {
  
  // val pieces = Array.ofDim[Piece](24)
  
  private def randomSymbol: Char = {    // A helper method for generating a random symbol.
    val symbols = Vector('a', 'b', 'c', 'A', 'B', 'C')
    symbols(Random.nextInt(6))
  }
  
  private def randomFirstPiece: Piece = {  // A method for creating the first piece on a board.
    new Piece(randomSymbol, randomSymbol, randomSymbol, Option(1,1), false)
  }
  
  private def randomPieceWith1Req(symbol:Char, coordinates: Option[(Int,Int)], placing: Int, down: Boolean): Piece = {  
  // A method for creating a random piece that has requirements for one symbol.
    require(placing >= 0 && placing <= 2)
    
    var sym = ' '
    
    symbol match {
      case 'a' => sym = 'A'
      case 'b' => sym = 'B'
      case 'c' => sym = 'C'
      case 'A' => sym = 'a'
      case 'B' => sym = 'b'
      case 'C' => sym = 'c'
    }
    
    placing match {
      case 0 => new Piece(sym, randomSymbol, randomSymbol, coordinates, down)
      case 1 => new Piece(randomSymbol, sym, randomSymbol, coordinates, down)
      case 2 => new Piece(randomSymbol, randomSymbol, sym, coordinates, down)
    }
  }
  
  private def randomPieceWith2Req = {
    
  }
  
  private var solution: Board = ???  // maybe a board maybe just pieces idk yet
  
  def generateSolution = {
    val board = new Board(Buffer[Piece]()) 
    
    def check(p: Piece): Boolean = board.pieces.contains(p)
    
    board.addPiece(randomFirstPiece)
    
    for (i <- 2 until 5) {
      val symbol: Char = board.getPiece((1, i-1)).get.symbols(if (i % 2 != 0) 2 else 1)
      val placing = if (i % 2 != 0) 1 else 2 
      val down = i % 2 != 0
      var piece = randomPieceWith1Req(symbol, Option(1, i), placing, down)
      
      while (!check(piece)) {
        piece = randomPieceWith1Req(symbol, Option(1, i), placing, down)
      }
      
      board.addPiece(piece)  
    }
    
    println(board.pieces)
    
    
  }
  
  def hasEnded = this.board.pieces == this.solution.pieces
  
  def startGame = ???
  
  
  
}

// new Game(new Board(Buffer[Piece]())).generateSolution
