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
  
  def getString(game: Game, board: Board, pile: Pile): Seq[String] = { //This method changes the pieces to a format that can be saved into a text file. 
                                                                       //Each piece is saved in the following format: BCbB110 
    var arr = Buffer[String]()
    
    for (i <- board.pieces) {
      var word = ""
      val coords = i.location.get
      val rotation = if (i.upsidedown) 1 else 0
      word = word + "B" + i.symbols.mkString + coords._1 + coords._2 + rotation
      arr += word      
    }
    
    for (i <- pile.pieces) {
      var word = ""
      val rotation = if (i.upsidedown) 1 else 0
      word = word + "P" + i.symbols.mkString + 0 + 0 + rotation
      arr += word     
    }
    
    for (i <- game.correctSolution.pieces) {
      var word = ""
      val coords = i.location.get
      val rotation = if (i.upsidedown) 1 else 0
      word = word + "S" + i.symbols.mkString + coords._1 + coords._2 + rotation
      arr += word
    }
    
    arr.toSeq
  }
  
  def writeToFile(fileName: String, arr: Seq[String]) = {  // This method writes into a given file.
    
    try {
      val fileOut = new FileWriter(fileName)
      
      val linesOut = new BufferedWriter(fileOut)
      
      try {
        
        
        for (a <- arr) {
          linesOut.write(a)
          linesOut.newLine()
                   
        }
        
      } finally {

        linesOut.close()
        fileOut.close()
        
      }
    } catch {
      case notFound: FileNotFoundException => println("File not found.")
      case e: IOException => println("Writing finished with an error.")
    }
    
    
  }
  
  def readFile(sourceFile: String): Seq[String] = {  // This method reads data from a given file.
    
    var sequence = Seq[String]()
    var oneLine: String = null
    
    val fileReader = try {
      new FileReader(sourceFile);
      
    } catch {
      case e: FileNotFoundException => println("File not found.")
        return sequence
    }
    
    val lineReader = new BufferedReader(fileReader)
    
    try {
      while ({oneLine = lineReader.readLine(); oneLine != null}) {
      sequence = sequence :+ oneLine
      
    }
      
    } catch {
      case e: IOException => println("Reading finished with an error.")
    }
    
    sequence
  }
  
  def loadGame(data: Seq[String]) = {  // This method changes the given data into pieces in the game and returns the board, game and pile that have been loaded.
    
    val board = new Board(Buffer[Piece]())
    val game = new Game(board)
    val pile = new Pile(Buffer[Piece]())
    val solution = game.correctSolution
    solution.empty()
    
    for (i <- data) {
      val syms = (i(1), i(2), i(3))
      val loc: Option[(Int, Int)] = Option(i(4).asDigit, i(5).asDigit)
      val flip = (if(i(6).asDigit == 0) false else true)
      i(0) match {
        case 'B' => board.addPiece(new Piece(syms._1, syms._2, syms._3, loc, flip))
        case 'P' => pile.add(new Piece(syms._1, syms._2, syms._3, None, flip))
        case 'S' => solution.addPiece(new Piece(syms._1, syms._2, syms._3, loc, flip))
      }
    }

    (board, game, pile)
    
  }
  
  
  
}