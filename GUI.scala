
package com.johndulin.playground

import scala.swing._ //{Panel, MainFrame, SimpleSwingApplication}
import java.awt.{Color, Graphics2D, Dimension}

object GUI extends SimpleSwingApplication {
  var build = Building(1,6)
  var picture = draw(build)

  lazy val top = {
    println("Test")

    lazy val screen = new Screen(picture) {
       preferredSize = new Dimension(800, 800)
    }

    new MainFrame {
      val button = Button("Step") {
        step()
        screen.repaint()
      }
      title = "Elevator Playground"
      background = Color.WHITE
      contents = new BoxPanel(Orientation.Vertical) {
        contents += screen
        contents += button
      }
    }
  }

  /**
   * Step the simulation by stepping the building model and expressing a new screen.
   * @param b the BuildingState to step.
   */
  def step(): Unit = {
    println("Moving elevators and pulleys... painting new pictures...")
      
    build = build.step
    picture = draw(build)
  }

  /**
   * Draws a new grid image.
   * @param building BuildingState to represent
   */
  def draw(b: BuildingState): Array[Array[Color]] = {
    val f = b.floors
    val e = b.elevators
    val n = b.elevators.length
    val dims = sketch(f, n)
    val xblocks = dims._1
    val yblocks = dims._2

    var temp = Array.ofDim[Color](xblocks, yblocks)

    // TODO: PUT IN ELEVATORS
    // TODO: PUT IN PICKUP REQUESTS

    temp(1) = wrap(f, roof(f, n))
    ////////////////////////////////////
    temp.transpose
  }

  /**
   * Defines the size of the grid screen.
   */
  def sketch(height: Int, elevators: Int): (Int, Int) = {
    (2*elevators + 5, height + 3)
  }

  /**
   * Draws elevator shafts for the grid image as an Array[Color]
   * Columns that must be expressed in the tranpose of the grid.
   * Note: Draw before drawing roof.
   */
  def shaft(height: Int): Array[Color] = {
    val top = Array[Color](Color.GREEN, Color.RED, Color.GREEN) 
    val shf = Array.ofDim[Color](height) 

    top ++ (shf map ((p:Color) => Color.ORANGE))
  }

  /**
   * Draws the building external walls. 
   * Columns that must be expressed in the tranpose of the grid.
   */
  def outline(height: Int): Array[Color] = {
    val top = Array[Color](Color.GREEN)
    val wal = Array.ofDim[Color](height + 2)

    top ++ (wal map ((p:Color) => Color.RED))
  }

  /**
   * Draws the roof of the building.
   * Row at `screen(1)`
   * Express after the shafts and outlines.
   */
  def roof(height: Int, elevs: Int): Array[Color] = {
    val margin = Array[Color](Color.GREEN)
    val roof = Array.ofDim[Color](2 * elevs + 1)

    margin ++ (roof map ((p:Color) => Color.RED)) ++ margin
  }

  /**
   * Centers a drawing of the building in the grid.
   */
  def wrap(l: Int, mid: Array[Color]): Array[Color] = {
    val space = (l - mid.length)/2
    val side = Array.ofDim[Color](space)

    (side map ((p:Color) => Color.WHITE)) ++ mid ++ (side map ((p:Color) => Color.WHITE))

  }
}
