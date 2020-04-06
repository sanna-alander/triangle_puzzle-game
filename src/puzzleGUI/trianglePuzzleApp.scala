package puzzleGUI

import scala.swing._
import scala.swing.event._
import javax.swing.ImageIcon
import java.awt.{ Color, Graphics2D }
import scala.swing.BorderPanel.Position._
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File
import trianglepuzzle.Game
import trianglepuzzle.Board
import trianglepuzzle.Pile
import trianglepuzzle.Piece
import scala.collection.mutable.Buffer


object puzzleApp extends SimpleSwingApplication {
  
  def startGame = {
    val board = new Board(Buffer[Piece]())
    val game = new Game(board)
    val pile = new Pile(game.correctSolution.pieces)
  }
  
  val frame = new MainFrame
  frame.visible = true
  frame.resizable = false
  
  frame.title = "Triangle Puzzle"
  
  frame.preferredSize = new Dimension(1400, 800)
  
  val boardPic = ImageIO.read(new File("puzzle.jpg"))
  val triangle1 = ImageIO.read(new File("triangle1.jpg"))
  val triangle2 = ImageIO.read(new File("triangle2.jpg"))
  
  val saveButton = new Button("Save")
  val newGameButton = new Button("New game")
  
  val shuffleButton = new Button("Shuffle")
  val shuffleText = new Label("Press to shuffle pieces")
  
  
  val allThings = new BoxPanel(Orientation.Vertical)
  
  val buttons = new BoxPanel(Orientation.Horizontal)
  val shuffle = new BoxPanel(Orientation.Vertical)
  
  val pile = new BoxPanel(Orientation.Horizontal) 
 
  
  pile.contents += new RectPanel2
  //pile.contents += new RectPanel
  
  buttons.contents += saveButton
  buttons.contents += newGameButton
  
  shuffle.contents += shuffleButton
  shuffle.contents += shuffleText
  
  pile.contents += new triangle1
  pile.contents += shuffle

  allThings.contents += buttons
  
  
  val borderPanel = new BorderPanel {
    add(buttons, BorderPanel.Position.North)
    add(pile, BorderPanel.Position.Center)
    
    
  }

  allThings.contents += borderPanel
  allThings.border = Swing.EmptyBorder(30, 30, 30, 30)
  
  frame.contents = allThings
  
  def top = this.frame
  
  class RectPanel2 extends Panel {

  override def paintComponent(g : Graphics2D) = {
    g.setColor(Color.white)  
    g.fillRect(0, 20, 600, 600)
    g.drawImage(boardPic, 0, 20, null)

    
  }
}
  
  class RectPanel extends Panel {

  override def paintComponent(g : Graphics2D) = {
    g.setColor(Color.white)   
    g.fillRect(0, 20, 400, 400)

    
  }
}
  
  class triangle1 extends Panel {
    override def paintComponent(g: Graphics2D) = {
      g.drawImage(triangle1, 0, 0, null)
    }
  }
    
}