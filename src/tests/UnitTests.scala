package tests


import org.scalatest._
import trianglepuzzle._
import scala.collection.mutable.Buffer


class UnitTests extends FlatSpec {
  
  val board = new Board(Buffer[Piece]())
  val game = new Game(board) 
  val pile = new Pile(game)
  
  "game.generateSolution" should "create a random solution" in {
    val solution = game.correctSolution
    assert(solution.pieces.size === 5)
  }
  
}