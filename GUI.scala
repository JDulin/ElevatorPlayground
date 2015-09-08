
package com.johndulin.playground

import scala.swing._ //{Panel, MainFrame, SimpleSwingApplication}
import java.awt.{Color, Graphics2D, Dimension}

object GUI extends SimpleSwingApplication {

  val dims = generate(1, 6)
  val xblocks = dims._1
  val yblocks = dims._2

  // TODO : Make immutable
  var picture = Array.ofDim[Color](xblocks, yblocks)

  picture(0)(0) = Color.BLACK
  picture(4)(4) = Color.GREEN
  picture(0)(4) = Color.GREEN
  picture(4)(0) = Color.GREEN
    
  lazy val top = {
    println("Test")

    lazy val image = new Window(picture) {
       preferredSize = new Dimension(400, 400)
    }

    new MainFrame {
      val button = Button("Step") {
        step(1)
        image.repaint()
      }
      
      title = "Elevator Playground"
      background = Color.BLACK
      contents = new BoxPanel(Orientation.Vertical) {
        contents += image
        contents += button
      }
    }
  }

  def step(flag:Int) = {
    println("test!")

    picture(10)(10) = Color.BLACK
  }

  def generate(elevators: Int, height: Int): (Int, Int) = {
    (25, 25)
  }
}
