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
    val solutionBoard = new Board(Buffer[Piece]()) 
    
    def check(p: Piece): Boolean = solutionBoard.pieces.exists( _.equals(p) ) // With this method the program checks if there's already a piece 
                                                                              // added with the same symbols in the same order.
    
    def addFirst(row: Int, down: Boolean): Piece = {
      var piece = randomFirstPiece(row, down)
      while (check(piece)) {
        piece = randomFirstPiece(row, down)
      }
      piece
    }
    
    // adding the first pieces
    solutionBoard.addPiece(addFirst(1, false))
    solutionBoard.addPiece(addFirst(4, true))
    solutionBoard.addPiece(addFirst(2, false))
    
    var row3First = randomPieceWith1Req(solutionBoard.getPiece((2, 1)).get.symbols(0), Option(3, 1), 0, true)
    while (check(row3First)) {
      row3First = randomPieceWith1Req(solutionBoard.getPiece((2, 1)).get.symbols(0), Option(3, 1), 0, true)
    }
    solutionBoard.addPiece(row3First)
    
    //adding pieces of the first row
    for (i <- 2 until 6) {
      val symbol: Char = solutionBoard.getPiece((1, i-1)).get.symbols(if (i % 2 == 0) 2 else 1)
      val placing = if (i % 2 != 0) 1 else 2 
      val down = i % 2 == 0
      var piece = randomPieceWith1Req(symbol, Option(1, i), placing, down)
      if (check(piece)) piece = randomPieceWith1Req(symbol, Option(1, i), placing, down)
      solutionBoard.addPiece(piece)  
    }
    
    //adding pieces of the fourth row
    for (i <- 2 until 6) {
      val symbol: Char = solutionBoard.getPiece((4, i-1)).get.symbols(if (i % 2 != 0) 2 else 1)
      val placing = if (i % 2 == 0) 1 else 2 
      val down = i % 2 != 0
      var piece = randomPieceWith1Req(symbol, Option(4, i), placing, down)
      if (check(piece)) piece = randomPieceWith1Req(symbol, Option(4, i), placing, down)
      solutionBoard.addPiece(piece)  
    }
    
    //adding pieces of the second row
    for (i <- 2 until 8) {
      if (i % 2 == 0) {
        val symbols: (Char, Char) = {
            (solutionBoard.getPiece(1, i-1).get.symbols(0), solutionBoard.getPiece(2, i-1).get.symbols(2))
        }
        val placings = (0, 2)
        val down = true
        var piece = randomPieceWith2Req(symbols, Option(2, i), placings, down)
        if (check(piece)) piece = randomPieceWith2Req(symbols, Option(2, i), placings, down)
        solutionBoard.addPiece(piece)
        
      } else {
        val symbol = solutionBoard.getPiece(2, i-1).get.symbols(1)
        val placing = 1
        val down = false
        var piece = randomPieceWith1Req(symbol, Option(2, i), placing, down)
        if (check(piece)) piece = randomPieceWith1Req(symbol, Option(2, i), placing, down)
        solutionBoard.addPiece(piece)
      }
        
    }
    
    //adding pieces of the third row
    for (i <- 2 until 8) {
      val symbols = if (i % 2 == 0) (solutionBoard.getPiece(4, i-1).get.symbols(0), solutionBoard.getPiece(3, i-1).get.symbols(1)) else {
        (solutionBoard.getPiece(2, i).get.symbols(0), solutionBoard.getPiece(3, i-1).get.symbols(2))
      }
      val placings = if (i % 2 == 0) (0, 1) else (0, 2)
      val down = i % 2 != 0
      var piece = randomPieceWith2Req(symbols, Option(3, i), placings, down)
      if (check(piece)) piece = randomPieceWith2Req(symbols, Option(3, i), placings, down)
      solutionBoard.addPiece(piece)
    }

    
    solutionBoard
    
  }
  
  private val solution: Board = { // Here the solution is finally created and saved.
    var solution = generateSolution
    while (!solution.allDifferent) solution = generateSolution
    solution
  }
  
  def correctSolution: Board = this.solution  // Method for getting the correct solution.
  
  private def someSolutionFound(givenBoard: Board): Boolean = {  // It is possible to find other solutions to the puzzle than just the one that the
                                                                 // program has come up with. This method checks if any answer has been found. 
    val vals = Buffer[Boolean]()
    
    def shouldMatch(n: Char, m: Char): Boolean = {
      if (n == 'A' && m == 'a') true
      else if (n == 'B' && m == 'b') true 
      else if (n == 'C' && m == 'c') true
      else if (n == 'a' && m == 'A') true
      else if (n == 'b' && m == 'B') true
      else if (n == 'c' && m == 'C') true
      else false
    }
    
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
    
    for (a <- givenBoard.pieces) {
      val l = a.location.get
      if (a.upsidedown) {
        vals += shouldMatch(a.symbols(0), if (l._1 == 1) findSym(a.symbols(0))
        else if (l._1 == 2) givenBoard.getPiece((1, l._2 - 1)).get.symbols(0)
        else if (l._1 == 3) givenBoard.getPiece((2, l._2)).get.symbols(0)
        else givenBoard.getPiece((3, l._2 + 1)).get.symbols(0))
        vals += shouldMatch(a.symbols(1), if (l._1 == 3 && l._2 == 7 || l._1 == 4 && l._2 == 5) findSym(a.symbols(1))
        else givenBoard.getPiece((l._1, l._2 + 1)).get.symbols(1))
        vals += shouldMatch(a.symbols(2), if (l._1 == 3 && l._2 == 1 || l._1 == 4 && l._2 == 1) findSym(a.symbols(2))
        else givenBoard.getPiece((l._1, l._2 - 1)).get.symbols(2))
      } else {
        vals += shouldMatch(a.symbols(0), if (l._1 == 4) findSym(a.symbols(0))
        else if (l._1 == 1) givenBoard.getPiece((2, l._2 + 1)).get.symbols(0)
        else if (l._1 == 2) givenBoard.getPiece((3, l._2)).get.symbols(0)
        else givenBoard.getPiece((4, l._2 - 1)).get.symbols(0))
        vals += shouldMatch(a.symbols(1), if (l._1 == 1 && l._2 == 1 || l._1 == 2 && l._2 == 1) findSym(a.symbols(1))
        else givenBoard.getPiece((l._1, l._2 - 1)).get.symbols(1))
        vals += shouldMatch(a.symbols(2), if (l._1 == 1 && l._2 == 5 || l._1 == 2 && l._2 == 7) findSym(a.symbols(2))
        else givenBoard.getPiece((l._1, l._2 + 1)).get.symbols(2))
      }

    }
    
    vals.forall( _ == true )
  }
  
  def solutionFound(givenBoard: Board): Boolean = { //Checks whether a correct solution has been found.
    ( givenBoard.pieces.size == 24 && givenBoard.equals(this.solution) ) || ( givenBoard.pieces.size == 24 && someSolutionFound(givenBoard) )
  }

  
  
  
}


