
package com.johndulin.playground

/**
 * ElevatorControlSystem interface for both Building(s) of many elevators
 * and individual Elevator(s).
 *
 * Inheritors specify how to
 *  - Query the status of the elevator or set of elevators
 *  - Time-step the simulation
 *  - Receive a pickup request
 */
trait ControlSystem {
  // Query the system status
  def status: State
  
  /**
   * Creates a new pickup request and assigns it to an elevator.
   * @param floor the floor making the pickup request
   * @param dir the direction of the request 
   */
  def pickup(floor:Int, dir:Int): State

  //  Increment the system in time.
  def step: State
}
