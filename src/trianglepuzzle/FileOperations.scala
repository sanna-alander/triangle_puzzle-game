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
  
  def getString(game: Game, board: Board, pile: Pile): Seq[String] = {
    var arr = Buffer[String]()
    
    for (i <- board.pieces) {
      var word = ""
      val coords = i.location.get
      word = word + "B" + i.symbols.mkString + coords._1 + coords._2 + i.upsidedown
      arr += word      
    }
    
    for (i <- pile.pieces) {
      var word = ""
      val coords = i.location.get
      word = word + "P" + i.symbols.mkString + coords._1 + coords._2 + i.upsidedown
      arr += word     
    }
    
    arr.toSeq
  }
  
  def writeToFile(fileName: String, arr: Seq[String]) = {
    
    try {
      val fileOut = new FileWriter(fileName)
      
      val linesOut = new BufferedWriter(fileOut)
      
      try {
        
        
        for (a <- arr) {
          var oneLine = linesOut.write(a)
          
        }
        
      } finally {
        
        fileOut.close()
        linesOut.close()
        
      }
    } catch {
      case notFound: FileNotFoundException => println("File not found.")
      case e: IOException => println("Writing finished with an error.")
    }
    
    
  }
  
  
  
}