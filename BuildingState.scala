
package com.johndulin.playground

import Math.abs

/**
 * Represents building state at an instant in time.
 * Handles the allocation of passengers to elevators.
 * 
 * @constructor
 * @param elevators array of ElevatorState objects
 */
case class BuildingState(
  floors: Int,
  elevators: Seq[ElevatorState]
) extends State with ControlSystem {

  val status: BuildingState = this

  def pickup(floor:Int, dir:Int): BuildingState = {
    val chosen = closest(floor, intersection(floor, dir))
    val picker = chosen.pickup(floor, dir)

    BuildingState(
      floors = floors, 
      elevators = elevators.updated(picker.id, picker)
      )
  }

  def step: BuildingState = {
    BuildingState(
      floors = floors,
      elevators = for (e <- elevators) yield e.step
    )
  }

  /**
   * Returns the list of elevators eligible for pickup assignment
   * I.e. The set of elevators whose ranges are supersets of the 
   * pickup request's range.
   * @param floor floor to check for intersections with
   * @param dir direction of the request
   */
  def intersection(floor:Int, dir:Int): Seq[ElevatorState] = {
    for (e <- elevators if (space(floor, dir) subsetOf space(e))) yield e 
  }

  /**
   * Returns the ID of the elevator closest to the pickup request.
   * @param poss list of possible elevators returned from intersection
   */
  def closest(floor: Int, poss: Seq[ElevatorState]): ElevatorState = {
    elevators.minBy(x => abs(x.floor - floor))
  }

  // Returns the range of an elevator 
  // (All of the floors in the direction it is currently stepping)
  def space(e: ElevatorState): Set[Int] = e.direction match {
    case 1  => (e.floor to (floors-1)).toSet
    case -1 => (0 to e.floor).toSet
    case 0  => (0 to (floors-1)).toSet
  }

  // Returns the range of a pickup request
  def space(floor:Int, dir:Int): Set[Int] = dir match {
    case 1  => (floor to (floors-1)).toSet
    case -1 => (0 to floor).toSet
    case 0  => (0 to (floors-1)).toSet
  }
}
