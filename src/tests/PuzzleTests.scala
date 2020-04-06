package tests


import org.scalatest._
import trianglepuzzle._
import scala.collection.mutable.Buffer


class PuzzleTests extends FlatSpec() {
  
  val board = new Board(Buffer[Piece]())
  val game = new Game(board) 
  
    val pieceA = new Piece('a', 'C', 'b', None, false)
    val pieceB = new Piece('b', 'a', 'C', None, false)
    val pieceC = new Piece('b', 'b', 'b', None, false)
  
  "game.generateSolution" should "create a random solution" in {
    val solution = game.correctSolution
    assert(solution.pieces.size === 24)
  }
  
  
  "pile.shuffle" should "shuffle the pile" in { //not a working test
    val pile1 = new Pile (Buffer(pieceA, pieceB, pieceC))
    pile1.shuffle
    val pile2 = new Pile (Buffer(pieceA, pieceB, pieceC))
    assert(pile1.pieces != pile2.pieces)
  }
  
  "piece.rotate" should "rotate the symbols" in { 
    
    val piece1 = new Piece('a', 'C', 'b', None, false)
    val piece2 = new Piece('a', 'C', 'b', None, false)
    val syms = piece1.symbols
    piece1.rotate
    val syms2 = piece2.symbols
    
    assert(syms != syms2)
    
  }
  
  "piece.equals" should "check if pieces are equal" in { 
    val piece1 = new Piece('a', 'C', 'b', None, false)
    val piece2 = new Piece('b', 'a', 'C', None, false)
    val piece3 = new Piece('b', 'b', 'b', None, false)
    
    assert (piece1.equals(piece2) === true)
    assert (piece1.equals(piece3) === false)
    
  }
  
  "game.solutionFound" should "check if correct solution has been found" in {
    val board = new Board(Buffer[Piece]())
    val game = new Game(board)
    val solution = game.correctSolution
    
    assert(game.solutionFound(solution) === true)
  }
  
  
}