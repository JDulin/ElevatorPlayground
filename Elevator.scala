
package com.johndulin.playground

/**
 * Instantiates an Elevator with state.
 * Only builds one elevator for now.
 *
 * @constructor creates an elevator with the specified index, on the first floor, and 
 * with no pickup assignemnts
 * @param id elevator index
 * @param floors number of floors it reaches
 */
object Elevator {
  def apply(id:Int, floors:Int) = {
    ElevatorState(id, floors, floors/2, Set.empty, Map.empty)
  }
}
