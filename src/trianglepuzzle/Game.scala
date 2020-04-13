package trianglepuzzle

import scala.collection.mutable.Buffer
import scala.util.Random

class Game(val board: Board) {
  
  
  private def randomSymbol: Char = {    // A helper method for generating a random symbol.
    val symbols = Vector('a', 'b', 'c', 'A', 'B', 'C')
    symbols(Random.nextInt(6))
  }
  
  private def randomFirstPiece(row: Int, down: Boolean): Piece = {  // A method for creating the first piece on a board.
    new Piece(randomSymbol, randomSymbol, randomSymbol, Option(row,1), down)
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
  
  private def randomPieceWith2Req(symbols: (Char, Char), coordinates: Option[(Int, Int)], placings: (Int, Int), down: Boolean): Piece = {
    

    def findSym(sym: Char) = {
      sym match {
        case 'a' => 'A'
        case 'b' => 'B'
        case 'c' => 'C'
        case 'A' => 'a'
        case 'B' => 'b'
        case 'C' => 'c'
      }
    }
    
    var sym1 = findSym(symbols._1)
    var sym2 = findSym(symbols._2)
    
    placings match {
      case (0, 1) => new Piece(sym1, sym2, randomSymbol, coordinates, down)
      case (0, 2) => new Piece(sym1, randomSymbol, sym2, coordinates, down)
      case (1, 2) => new Piece(randomSymbol, sym1, sym2, coordinates, down)
      case _      => new Piece(randomSymbol, randomSymbol, randomSymbol, coordinates, down) //
    }   
    
  }

  
  def generateSolution: Board = {
    val board = new Board(Buffer[Piece]()) 
    
    def check(p: Piece): Boolean = board.pieces.exists( _.equals(p) )
    
    def addFirst(row: Int, down: Boolean): Piece = {
      var piece = randomFirstPiece(row, down)
      while (check(piece)) {
        piece = randomFirstPiece(row, down)
      }
      piece
    }
    
    // adding the first pieces
    board.addPiece(addFirst(1, false))
    board.addPiece(addFirst(4, true))
    board.addPiece(addFirst(2, false))
    
    var row3First = randomPieceWith1Req(board.getPiece((2, 1)).get.symbols(0), Option(3, 1), 0, true)
    while (check(row3First)) {
      row3First = randomPieceWith1Req(board.getPiece((2, 1)).get.symbols(0), Option(3, 1), 0, true)
    }
    board.addPiece(row3First)
    
    //adding pieces of the first row
    for (i <- 2 until 6) {
      val symbol: Char = board.getPiece((1, i-1)).get.symbols(if (i % 2 == 0) 2 else 1)
      val placing = if (i % 2 != 0) 1 else 2 
      val down = i % 2 == 0
      var piece = randomPieceWith1Req(symbol, Option(1, i), placing, down)
      
      board.addPiece(piece)  
    }
    
    //adding pieces of the fourth row
    for (i <- 2 until 6) {
      val symbol: Char = board.getPiece((4, i-1)).get.symbols(if (i % 2 == 0) 2 else 1)
      val placing = if (i % 2 == 0) 1 else 2 
      val down = i % 2 == 0
      var piece = randomPieceWith1Req(symbol, Option(4, i), placing, down)
      
      board.addPiece(piece)  
    }
    
    //adding pieces of the second row
    for (i <- 2 until 8) {
      if (i % 2 == 0) {
        val symbols: (Char, Char) = {
            (board.getPiece(1, i-1).get.symbols(0), board.getPiece(2, i-1).get.symbols(2))
        }
        val placings = (0, 2)
        val down = true
        var piece = randomPieceWith2Req(symbols, Option(2, i), placings, down)

        board.addPiece(piece)
        
      } else {
        val symbol = board.getPiece(2, i-1).get.symbols(1)
        val placing = 1
        val down = false
        var piece = randomPieceWith1Req(symbol, Option(2, i), placing, down)

        board.addPiece(piece)
      }
        
    }
    
    //adding pieces of the third row
    for (i <- 2 until 8) {
      val symbols = if (i % 2 == 0) (board.getPiece(4, i-1).get.symbols(0), board.getPiece(3, i-1).get.symbols(1)) else {
        (board.getPiece(2, i).get.symbols(0), board.getPiece(3, i-1).get.symbols(2))
      }
      val placings = if (i % 2 == 0) (0, 1) else (0, 2)
      val down = i % 2 != 0
      var piece = randomPieceWith2Req(symbols, Option(3, i), placings, down)

      board.addPiece(piece)
    }
    
    //board.pieces.foreach(n => println(n.location))
    //board.pieces.foreach(n => println(n.upsidedown))
    // board.pieces.foreach(n => println(n.symbols))
    
    board
    
  }
  
  private val solution: Board = {
    var solution = generateSolution
    while (!solution.allDifferent) solution = generateSolution
    solution
  }
  
  def correctSolution = this.solution
  
  def solutionFound(givenBoard: Board): Boolean = { //Checks whether the correct solution has been found.
    givenBoard.pieces.size == 24 && givenBoard.equals(this.solution)
  }

  
  
  
}


