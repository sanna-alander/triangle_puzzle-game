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


object PuzzleApp extends SimpleSwingApplication {

  
  val triangle1 = ImageIO.read(new File("triangle1.png"))
  val triangle2 = ImageIO.read(new File("triangle2.png"))
  
  val boardwidth = 800.0
  val boardheight = 4 * sqrt(pow(boardwidth/7, 2) - pow(boardwidth/14,2))
  
  // The map below is for finding the position for a triangle based on the coordinates give.
  val positions = Map((1,1) -> ((boardwidth/8).toInt+10, 0), (1,2) -> (2*((boardwidth/8).toInt)+10,0), (1,3) -> (3*((boardwidth/8).toInt)+10,0),
                 (1,4) -> (4*((boardwidth/8).toInt)+10,0), (1,5) -> (5*((boardwidth/8).toInt)+10,0), (2,1) -> (10, (boardheight/4).toInt),
                 (2,2) -> (10+(boardwidth/8).toInt, (boardheight/4).toInt), (2,3) -> (10+2*(boardwidth/8).toInt, (boardheight/4).toInt),
                 (2,4) -> (10+3*(boardwidth/8).toInt, (boardheight/4).toInt), (2,5) -> (10+4*(boardwidth/8).toInt, (boardheight/4).toInt),
                 (2,6) -> (10+5*(boardwidth/8).toInt, (boardheight/4).toInt), (2,7) -> (10+6*(boardwidth/8).toInt, (boardheight/4).toInt), 
                 (3,1) -> (10, 2*(boardheight/4).toInt), (3,2) -> (10+(boardwidth/8).toInt, 2*(boardheight/4).toInt), 
                 (3,3) -> (10+2*(boardwidth/8).toInt, 2*(boardheight/4).toInt),
                 (3,4) -> (10+3*(boardwidth/8).toInt, 2*(boardheight/4).toInt), (3,5) -> (10+4*(boardwidth/8).toInt, 2*(boardheight/4).toInt),
                 (3,6) -> (10+5*(boardwidth/8).toInt, 2*(boardheight/4).toInt), (3,7) -> (10+6*(boardwidth/8).toInt, 2*(boardheight/4).toInt),
                 (4,1) -> ((boardwidth/8).toInt+10, 3*(boardheight/4).toInt), (4,2) -> (2*((boardwidth/8).toInt)+10,3*(boardheight/4).toInt), 
                 (4,3) -> (3*((boardwidth/8).toInt)+10,3*(boardheight/4).toInt),
                 (4,4) -> (4*((boardwidth/8).toInt)+10,3*(boardheight/4).toInt), (4,5) -> (5*((boardwidth/8).toInt)+10,3*(boardheight/4).toInt))
                 
                 
  // The areas where the mouse click can happen.
  val x1 = 170 to 230
  val y1 = 10 to 90
  val x2 = 270 to 330
  val x3 = 370 to 430
  val x4 = 470 to 530
  val x5 = 570 to 630
  
  val x6 = 70 to 130
  val y6 = 100 to 190
  val x7 = 170 to 230
  val x8 = 270 to 330
  val x9 = 370 to 420
  val x10 = 470 to 530
  val x11 = 570 to 630
  val x12 = 670 to 730
  
  val x13 = 70 to 130
  val y13 = 200 to 290
  val x14 = 170 to 230
  val x15 = 270 to 330
  val x16 = 370 to 420
  val x17 = 470 to 530
  val x18 = 570 to 630
  val x19 = 670 to 730
  
  val x20 = 170 to 230
  val y20 = 300 to 390
  val x21 = 270 to 330
  val x22 = 370 to 430
  val x23 = 470 to 530
  val x24 = 570 to 630
  
  
  
  
  
  def getCoords(p: (Int, Int)): Option[(Int, Int)] = {  // this method is for finding the coordinates of the board that match the clicked point
    
    var coords: Option[(Int, Int)] = None
    if ((x1.contains(p._1)) && (y1.contains(p._2))) coords = Option((1,1))
    if ((x2.contains(p._1)) && (y1.contains(p._2))) coords = Option((1,2))
    if ((x3.contains(p._1)) && (y1.contains(p._2))) coords = Option((1,3))
    if ((x4.contains(p._1)) && (y1.contains(p._2))) coords = Option((1,4))
    if ((x5.contains(p._1)) && (y1.contains(p._2))) coords = Option((1,5))
    if ((x6.contains(p._1)) && (y6.contains(p._2))) coords = Option((2,1))
    if ((x7.contains(p._1)) && (y6.contains(p._2))) coords = Option((2,2))
    if ((x8.contains(p._1)) && (y6.contains(p._2))) coords = Option((2,3))
    if ((x9.contains(p._1)) && (y6.contains(p._2))) coords = Option((2,4))
    if ((x10.contains(p._1)) && (y6.contains(p._2))) coords = Option((2,5))
    if ((x11.contains(p._1)) && (y6.contains(p._2))) coords = Option((2,6))
    if ((x12.contains(p._1)) && (y6.contains(p._2))) coords = Option((2,7))
    if ((x13.contains(p._1)) && (y13.contains(p._2))) coords = Option((3,1))
    if ((x14.contains(p._1)) && (y13.contains(p._2))) coords = Option((3,2))
    if ((x15.contains(p._1)) && (y13.contains(p._2))) coords = Option((3,3))
    if ((x16.contains(p._1)) && (y13.contains(p._2))) coords = Option((3,4))
    if ((x17.contains(p._1)) && (y13.contains(p._2))) coords = Option((3,5))
    if ((x18.contains(p._1)) && (y13.contains(p._2))) coords = Option((3,6))
    if ((x19.contains(p._1)) && (y13.contains(p._2))) coords = Option((3,7))
    if ((x20.contains(p._1)) && (y20.contains(p._2))) coords = Option((4,1))
    if ((x21.contains(p._1)) && (y20.contains(p._2))) coords = Option((4,2))
    if ((x22.contains(p._1)) && (y20.contains(p._2))) coords = Option((4,3))
    if ((x23.contains(p._1)) && (y20.contains(p._2))) coords = Option((4,4))
    if ((x24.contains(p._1)) && (y20.contains(p._2))) coords = Option((4,5))
    
    coords
  }
      
  
  var board = new Board(Buffer[Piece]())
  var game = new Game(board)
  var pile = new Pile(game.correctSolution.pieces)
  pile.shuffle()
  var ended = false
 
  var selected: Option[Piece] = None  // This variable stores the current selected piece wrapped in an option.
  
  def startGame() = {
    board = new Board(Buffer[Piece]())
    game = new Game(board)
    pile = new Pile(game.correctSolution.pieces)
    pile.shuffle()
    ended = false
    selected = None
    
  }
  
  def update() = {
    selected = None
    ended = this.game.solutionFound(this.board)
    pilePic.repaint()
  }
  
  def update2() = {
    ended = this.game.solutionFound(this.board)
    pilePic.repaint()
  }

  val ups = Buffer((1,1), (1,3), (1,5), (2,1), (2,3), (2,5), (2,7), (3,2), (3,4), (3,6), (4,2), (4,4))  // List of all the coordinates where the triangle isn't upsidedown.
  
  def drawTriangles(g: Graphics2D) = { // This is the method for drawing the triangles.
    g.setFont(new Font("Times New Roman", java.awt.Font.BOLD, 20))
    
    for (a <- this.board.pieces) {
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
    
    if (this.pile.pieces.nonEmpty) {
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

  }

  // Adding the buttons
  val saveButton = new Button("Save")
  val newGameButton = new Button("New game")
  val loadButton = new Button("Load game")
  val hintButton = new Button("Show Answer")  // I decided to add a button which makes the correct answer visible.
  val rotateButton = new Button("Rotate Piece") 
  
  // Buttons for going through the pile:
  val shuffleButton = new Button("-->")
  val shuffleBack = new Button("<--")
  val shuffleText = new Label("Press to shuffle pieces")
  
  listenTo(newGameButton)
  listenTo(shuffleButton)
  listenTo(shuffleBack)
  listenTo(saveButton)
  listenTo(loadButton)
  listenTo(rotateButton)
  listenTo(hintButton)
  
  
  val allThings = new BoxPanel(Orientation.Vertical)
  
  val buttons = new BoxPanel(Orientation.Horizontal)
  val shuffle = new BoxPanel(Orientation.Vertical)
  val pilePic = new BoxPanel(Orientation.Horizontal) 
  
  
  pilePic.contents += new boardPic
  pilePic.contents += shuffle
  
  listenTo(pilePic.mouse.clicks, pilePic.mouse.moves)
  
  // Adding all the contents into panels.
  buttons.contents += saveButton
  buttons.contents += newGameButton
  buttons.contents += loadButton
  buttons.contents += hintButton
  buttons.contents += rotateButton
  buttons.border = Swing.EmptyBorder(40, 40, 40, 40)
  buttons.background = Color.WHITE
  
  shuffle.contents += shuffleButton
  shuffle.contents += shuffleBack
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
      
    // Starting by drawing the board
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
    
    
    drawTriangles(g)  //This draws all the triangles currently visible on the board and pile.
    
    if (ended) {  // Let's the player know when the correct solution is found.
      g.setFont(new Font("Times New Roman", java.awt.Font.BOLD, 40))
      g.drawString("You figured out the puzzle!", (width/2).toInt, height.toInt+50)
      g.setFont(new Font("Times New Roman", java.awt.Font.BOLD, 20))
      g.drawString("Start new game by clicking the New Game button", (width/2).toInt, height.toInt + 80)
    }
    
    if (selected != None) {
      g.setFont(new Font("Times New Roman", java.awt.Font.BOLD, 14))
      val loc = positions.get(selected.get.location.get).get
      g.drawString("Selected", loc._1+70, loc._2+45)
      
    }
  }
    

  preferredSize = new Dimension (1000, height.toInt)
  border = Swing.EmptyBorder(40, 40, 40, 40)

}
  
  
  
  
  reactions += {
    case e@ MouseClicked(src,point,mod,1,_) => { //If the mouse is clicked
      
      val coords = getCoords((point.x, point.y))
      if ((e.peer.getButton == java.awt.event.MouseEvent.BUTTON1) && (selected == None)) { // If there isn't a selected piece then a piece from the pile is added or a piece is selected.
        
        if (coords != None && (this.board.getPiece(coords.get) == None)) { // if the clicked place doesn't have a triangle then the top piece from the pile is placed there.
          val p = this.pile.pieces(0)
          val u: Boolean = {
            if (ups.contains(coords.get)) false else true
          }
          this.board.addPiece(new Piece(p.symbols(0), p.symbols(1), p.symbols(2), coords, u)) 
          this.pile.remove(p)
          update()
          
          
        } else if (coords != None && (this.board.getPiece(coords.get) != None)) { // If there's a piece in the place, then that piece gets selected.
          selected = this.board.getPiece(coords.get)
          update2()
        }
      } else if ((e.peer.getButton == java.awt.event.MouseEvent.BUTTON1) && (selected != None)) { // If there's already a piece selected
        
        if (coords != None && (this.board.getPiece(coords.get) == None)) { // If there's no piece in the clicked area then the selected piece is moved there.
          selected.get.upsidedown = if (ups.contains(coords.get)) false else true
          selected.get.updateLocation(coords)
          update()
          
        } else if (coords != None && (this.board.getPiece(coords.get) != None)) { // If there is a piece in the clicked place then the two pieces switch places. 
          val sCoords = selected.get.location
          val sel = selected.get
          val other = this.board.getPiece(coords.get).get
          
          sel.upsidedown = if (ups.contains(coords.get)) false else true
          other.upsidedown = if (ups.contains(sCoords.get)) false else true
          
          sel.updateLocation(coords)
          other.updateLocation(sCoords)
          
          update()
          
        } else if (coords == None) { // If the click doesn't happen in any place but somewhere out of the board for example, then the piece is removed and moved back to the pile.
          this.board.removePiece(selected.get)
          this.pile.add(selected.get)
          update()
        }
        
      } else if (e.peer.getButton == java.awt.event.MouseEvent.BUTTON2) {  // the pieces on the board can be rotated by clicking the right button of the mouse on the piece
        if (coords != None && (this.board.getPiece(coords.get) != None)) {
          this.board.getPiece(coords.get).get.rotate
          update2()
          
        }
      }
      
    }
    case ButtonClicked(`rotateButton`)  => {  // the right button click doesn't work on my computer and I don't know if it works on other computers
                                              // but here's an alternative option for rotatinga specific slected piece on the board.
      
      if (selected != None) selected.get.rotate; update2()
    }
    case ButtonClicked(`newGameButton`) =>
      startGame
      println("Game started")
      pilePic.repaint()
    case ButtonClicked(`shuffleButton`) =>
      this.pile.rotatePile()
      pilePic.repaint()
    case ButtonClicked(`shuffleBack`)   =>
      this.pile.rotateBack()
      pilePic.repaint()
    case ButtonClicked(`loadButton`)    =>
      val loaded = FileOperations.loadGame(FileOperations.readFile("trianglePuzzle.txt"))
      this.game = loaded._2
      this.board = loaded._1
      this.pile = loaded._3
      update()
    case ButtonClicked(`saveButton`)    =>
      val toSave = FileOperations.getString(this.game, this.board, this.pile)
      FileOperations.writeToFile("trianglePuzzle.txt", toSave)
    case ButtonClicked(`hintButton`)    =>
      println(this.game.correctSolution.pieces.map(_.symbols) zip (this.game.correctSolution.pieces.map(_.location.get)))
      
      this.board.empty()
      for (i <- this.game.correctSolution.pieces) {
        this.board.addPiece( new Piece(i.symbols(0), i.symbols(1), i.symbols(2), i.location, i.upsidedown) )
      }
      
      this.pile.pieces = Buffer[Piece]()
      update()
    

  }
  
    
}