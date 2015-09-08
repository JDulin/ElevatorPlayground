
package com.johndulin.playground

import scala.swing._ //{Panel, MainFrame, SimpleSwingApplication}
import java.awt.{Color, Graphics2D, Dimension}

object GUI extends SimpleSwingApplication {
  val build = Building(6,1)

  // TODO : Generate from BuildingState
  var picture = draw(build)

  lazy val top = {
    println("Test")

    lazy val screen = new Screen(picture) {
       preferredSize = new Dimension(400, 400)
    }

    new MainFrame {
      val button = Button("Step") {
        step()
        screen.repaint()
      }
      
      title = "Elevator Playground"
      background = Color.BLACK
      contents = new BoxPanel(Orientation.Vertical) {
        contents += screen
        contents += button
      }
    }
  }

  /**
   * Step the simulation by stepping the building model and expressing a new screen.
   * @param building the BuildingState to step.
   */
  def step(building: BuildingState): Unit = {

    println("Moving elevators and pulleys... painting new pictures...")

    picture = draw(building)
  }

  /**
   * Draws the 
   * @param building BuildingState to represent
   */
  def draw(building: BuildingState): Array(Array[Color]) = {
    // TODO : Generate 
    // 1) elevator shafts + columns they are in.
    // 2) elevators
    // 3) requests
    // 4) building outline
    val f = building.floors
    val e = building.elevators

    // Currently tranpose -> alter columns -> transpose
    // TODO: Better way? XXX: this, shaft, generate -> UNTESTED
    var trans_pic = picture.transpose
    trans_pic(12) = shaft(height)
    picture = trans_pic.transpose


  }

  /**
   * Defines the size of the grid screen.
   */
 def sketch(elevators: Int, height: Int): (Int, Int) = {
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
  def outline(height: Int, elevators: Int): Array[Color] = {
    val top = Array[Color](Color.GREEN)
    val wal = Array.ofDim[Color](height + 2)

    top ++ (wal map ((p:Color) => Color.RED))
  }

  /**
   * Draws the roof of the building.
   * Row at `screen(1)`
   * Express after the shafts and outlines.
   */
  def roof(height: Int, elevators: Int): Array[Color] = {
    val margin = Array[Color](Color.GREEN)
    val roof = Array.ofDim[Color](2 * elevators + 1)

    margin ++ (roof map ((p:Color) => Color.RED) ++ margin
  }
}
