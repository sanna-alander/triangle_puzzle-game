package puzzleGUI

import scala.swing._
import scala.swing.event._
import javax.swing.ImageIcon
import java.awt.{ Color, Graphics2D }
import scala.swing.BorderPanel.Position._

object puzzleApp extends SimpleSwingApplication {
  
  val frame = new MainFrame
  frame.visible = true
  frame.resizable = false
  
  frame.title = "Triangle Puzzle"
  
  frame.preferredSize = new Dimension(1000, 800)
  
  val saveButton = new Button("Save")
  val newGameButton = new Button("New game")
  
  val shuffleButton = new Button("Shuffle")
  val shuffleText = new Label("Press to shuffle pieces")
  
  
  val allThings = new BoxPanel(Orientation.Vertical)
  
  allThings.contents += saveButton
  allThings.contents += newGameButton

  allThings.contents += new RectPanel
  allThings.contents += shuffleButton
  allThings.contents += shuffleText

  
  allThings.border = Swing.EmptyBorder(30, 30, 10, 30)


  
  frame.contents = allThings
  
  def top = this.frame
  
  class RectPanel extends Panel {

  override def paintComponent(g : Graphics2D) = {
    g.setColor(Color.white)   
    g.fillRect(20, 20, 200, 600)

    
  }
}
    
}