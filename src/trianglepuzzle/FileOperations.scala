package trianglepuzzle

import java.io.IOException
import java.io.Reader
import java.io.FileReader
import java.io.FileNotFoundException
import java.io.BufferedReader
import java.io.FileWriter
import java.io.BufferedWriter
import java.io.BufferedReader
import scala.collection.mutable.Buffer

object FileOperations {
  
  def loadGame: Game = {
    
    var pieces = Buffer[Piece]()
    
    
    
    val board = new Board(pieces)
    val game  = new Game(board)
    
    game
  }
  
  
  
}