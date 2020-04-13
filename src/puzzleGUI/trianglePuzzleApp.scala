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
import trianglepuzzle.FileOperations
import scala.collection.mutable.Buffer
import scala.math._


object puzzleApp extends SimpleSwingApplication {
  
  
  val triangle1 = ImageIO.read(new File("triangle1.png"))
  val triangle2 = ImageIO.read(new File("triangle2.png"))
  
  val boardwidth = 800.0
  val boardheight = 4 * sqrt(pow(boardwidth/7, 2) - pow(boardwidth/14,2))
  
  val positions = Map((1,1) -> ((boardwidth/8).toInt+10, 0), (1,2) -> (2*((boardwidth/8).toInt)+10,0), (1,3) -> (3*((boardwidth/8).toInt)+10,0),
                 (1,4) -> (4*((boardwidth/8).toInt)+10,0), (1,5) -> (5*((boardwidth/8).toInt)+10,0), (2,1) -> (10, (boardheight/4).toInt),
                 (2,2) -> (10+(boardwidth/8).toInt, (boardheight/4).toInt), (2,3) -> (10+2*(boardwidth/8).toInt, (boardheight/4).toInt),
                 (2,4) -> (10+3*(boardwidth/8).toInt, (boardheight/4).toInt), (2,5) -> (10+4*(boardwidth/8).toInt, (boardheight/4).toInt),
                 (2,6) -> (10+5*(boardwidth/8).toInt, (boardheight/4).toInt), (2,7) -> (10+6*(boardwidth/8).toInt, (boardheight/4).toInt), 
                 (3,1) -> (10, 2*(boardheight/4).toInt), (3,2) -> (10+(boardwidth/8).toInt, 2*(boardheight/4).toInt), 
                 (3,3) -> (10+2*(boardwidth/8).toInt, 2*(boardheight/4).toInt),
                 (3,4) -> (10+3*(boardwidth/8).toInt, 2*(boardheight/4).toInt), (3,5) -> (10+4*(boardwidth/8).toInt, 2*(boardheight/4).toInt),
                 (3,6) -> (10+5*(boardwidth/8).toInt, 2*(boardheight/4).toInt), (3,7) -> (10+6*(boardwidth/8).toInt, 2*(boardheight/4).toInt))
      
  
  var board = new Board(Buffer[Piece]())
  var game = new Game(board)
  var pile = new Pile(game.correctSolution.pieces)
  pile.shuffle
  var ended = false
  
  for (i <- game.correctSolution.pieces) {
    board.addPiece(i)
  }
  
  def startGame() = {
    board = new Board(Buffer[Piece]())
    game = new Game(board)
    pile = new Pile(game.correctSolution.pieces)
    pile.shuffle
    
  }
  
  def update = {
    
    ended = this.game.solutionFound(this.board)
  }
  
  def endGame = if (ended) ???
  
  def mouseClick(x: Int, y: Int) = {
    
  }
  
  def drawTriangles(g: Graphics2D) = {
    g.setFont(new Font("Times New Roman", java.awt.Font.BOLD, 20))
    
    for (a <- this.board.pieces.filterNot(_.location.get._1 == 4)) {
      val coords = positions.get(a.location.get).get
      val rotation = a.upsidedown
      if (rotation) {
        g.drawImage(triangle2, coords._1,coords._2, null)
        g.drawString(a.symbols(0).toString, coords._1+(boardwidth/8).toInt-10, coords._2+30)
        g.drawString(a.symbols(1).toString, coords._1+(boardwidth/8).toInt+10, coords._2+60)
        g.drawString(a.symbols(2).toString, coords._1+(boardwidth/12).toInt-10, coords._2+60)
      } else {
        g.drawImage(triangle1, coords._1,coords._2, null)
        g.drawString(a.symbols(0).toString, coords._1+(boardwidth/8).toInt-10, coords._2+90)
        g.drawString(a.symbols(1).toString, coords._1+(boardwidth/12).toInt-10, coords._2+60)
        g.drawString(a.symbols(2).toString, coords._1+(boardwidth/8).toInt+10, coords._2+60)
      }
      
    }
    val pileCoords = (boardwidth.toInt+100, (boardheight/2).toInt)
    val a = pile.pieces(0)
    if (pile.pieces(0).upsidedown) {
      g.drawImage(triangle2, pileCoords._1,pileCoords._2, null)
      g.drawString(a.symbols(0).toString, pileCoords._1+(boardwidth/8).toInt-10, pileCoords._2+30)
      g.drawString(a.symbols(1).toString, pileCoords._1+(boardwidth/8).toInt+10, pileCoords._2+60)
      g.drawString(a.symbols(2).toString, pileCoords._1+(boardwidth/12).toInt-10, pileCoords._2+60)
    } else {
      g.drawImage(triangle1, pileCoords._1,pileCoords._2, null)
      g.drawString(a.symbols(0).toString, pileCoords._1+(boardwidth/8).toInt-10, pileCoords._2+90)
      g.drawString(a.symbols(1).toString, pileCoords._1+(boardwidth/12).toInt-10, pileCoords._2+60)
      g.drawString(a.symbols(2).toString, pileCoords._1+(boardwidth/8).toInt+10, pileCoords._2+60)      
    }
  }

  
  val saveButton = new Button("Save")
  val newGameButton = new Button("New game")
  val loadButton = new Button("Load game")
  
  val shuffleButton = new Button("Shuffle")
  val shuffleText = new Label("Press to shuffle pieces")
  
  listenTo(newGameButton)
  listenTo(shuffleButton)
  listenTo(saveButton)
  listenTo(loadButton)
  
  
  val allThings = new BoxPanel(Orientation.Vertical)
  
  val buttons = new BoxPanel(Orientation.Horizontal)
  val shuffle = new BoxPanel(Orientation.Vertical)
  val pilePic = new BoxPanel(Orientation.Horizontal) 
  
  
  pilePic.contents += new boardPic
  pilePic.contents += shuffle
  
  listenTo(pilePic.mouse.clicks, pilePic.mouse.moves)
  
  buttons.contents += saveButton
  buttons.contents += newGameButton
  buttons.contents += loadButton
  buttons.border = Swing.EmptyBorder(40, 40, 40, 40)
  buttons.background = Color.WHITE
  
  shuffle.contents += shuffleButton
  shuffle.contents += shuffleText
  shuffle.background = Color.WHITE

  allThings.contents += buttons
  pilePic.background = Color.WHITE
  
  val borderPanel = new BorderPanel {
    add(buttons, BorderPanel.Position.North)
    add(pilePic, BorderPanel.Position.Center)
    
    border = Swing.EmptyBorder(60, 30, 30, 30)
    background = Color.WHITE
  }

  allThings.contents += borderPanel
  allThings.border = Swing.EmptyBorder(30, 30, 30, 30)
  allThings.background = Color.CYAN

  def top = new MainFrame {
    contents = allThings
    visible = true
    resizable = true
    title = "Triangle Puzzle"
    size = new Dimension(1400, 800)
    
    
  }
  
  class boardPic extends Panel {
    val width = boardwidth
    val height = boardheight
    
  override def paintComponent(g : Graphics2D) = {

    g.drawLine(0, (height/2).toInt, width.toInt, (height/2).toInt)
    g.drawLine((width/4).toInt, 0, (3/4.0*width).toInt, height.toInt)
    g.drawLine((3/4.0*width).toInt, 0, (width/4).toInt, height.toInt)
    g.drawLine(0, (height/2).toInt, (width/4).toInt, 0)
    g.drawLine((width/4).toInt, 0, (3/4.0*width).toInt, 0)
    g.drawLine((width/4).toInt, height.toInt, (3/4.0*width).toInt, height.toInt)
    g.drawLine(0, (height/2).toInt, (width/4).toInt, height.toInt)
    g.drawLine((3/4.0*width).toInt, height.toInt, width.toInt, (height/2).toInt)
    g.drawLine(width.toInt, (height/2).toInt, (3/4.0*width).toInt, 0)
    g.drawLine((width/8).toInt, (height/4).toInt, (7/8.0*width).toInt, (height/4).toInt)
    g.drawLine((7/8.0*width).toInt, (height/4).toInt, (width/2).toInt, height.toInt)
    g.drawLine((width/2).toInt, height.toInt, (width/8).toInt, (height/4).toInt)
    g.drawLine((width/8).toInt, (3/4.0*height).toInt, (7/8.0*width).toInt, (3/4.0*height).toInt)
    g.drawLine((7/8.0*width).toInt, (3/4.0*height).toInt, (width/2).toInt, 0)
    g.drawLine((width/2).toInt, 0, (width/8).toInt, (3/4.0*height).toInt)
    
    g.drawLine((width + 50).toInt, 0, (width + 50).toInt, height.toInt)  //line separating the board from the pile
    
    // triangle size: bottom = 180 pixels, height: 108
    
    def drawTriangle(syms: Buffer[Char], img: BufferedImage) = {
      g.drawImage(img, 10, (height/4).toInt, null)
      g.setFont(new Font("Times New Roman", java.awt.Font.BOLD, 20))
      
    }
    
    //drawTriangle(pile.pieces(0).symbols, triangle1)
    drawTriangles(g)
    
  }
    

  preferredSize = new Dimension (1000, height.toInt)
  border = Swing.EmptyBorder(40, 40, 40, 40)

}
  
  reactions += {
    case ButtonClicked(`newGameButton`) =>
      startGame
      println("Game started")
      pilePic.repaint()
    case ButtonClicked(`shuffleButton`) =>
      this.pile.rotatePile
      pilePic.repaint()
    case ButtonClicked(`loadButton`)    =>
      ???
    case ButtonClicked(`saveButton`)    =>
      val toSave = FileOperations.getString(this.game, this.board, this.pile)
      FileOperations.writeToFile("???", toSave)
    case e: MouseClicked => println("Mouse clicked at " + e.point)
  }
  
    
}