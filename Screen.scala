
package com.johndulin.playground

import scala.swing._
import java.awt.{Color, Graphics2D, Dimension}

class Screen(data: Array[Array[Color]]) extends Panel {

    override def paintComponent(g: Graphics2D) {
      val dx = g.getClipBounds.width.toFloat  / data.length
      val dy = g.getClipBounds.height.toFloat / data.map(_.length).max
      for {
        x <- 0 until data.length
        y <- 0 until data(x).length
        x1 = (x * dx).toInt
        y1 = (y * dy).toInt
        x2 = ((x + 1) * dx).toInt
        y2 = ((y + 1) * dy).toInt
      } {
        data(x)(y) match {
        case c: Color => g.setColor(c)
        case _ => g.setColor(Color.WHITE)
      }
      g.fillRect(x1, y1, x2 - x1, y2 - y1)
   }
 }
}
