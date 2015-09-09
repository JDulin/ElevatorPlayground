
package com.johndulin.playground

import Array._

/**
 *  Instantiates a Building with state - 
 *  BuildingState Factory object to boostrap the simulation.
 *
 *  @constructor creates a building of 'e' elevators, 'floors' floors
 *  @param e number of elevators in the building
 *  @param floors number of floors 
 */
object Building {
  def apply(e:Int, floors:Int) = {

      val elevators = for (id <- 0 to (e-1)) yield Elevator(id, floors)

      BuildingState(floors, elevators)
  }
}
