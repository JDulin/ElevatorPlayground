
package com.johndulin.playground

import java.util.Random
import util.control.Exception._

import math.abs

/**
 * Represents elevator state at an instant in time.
 * Handles the loading and unloading of passengers.
 *
 * @constructor 
 * @param id elevator index
 * @param floor current floor
 * @param pass the destination floors of passengers on board
 * @param pick map of pickup requests and their direction
 */
case class ElevatorState(
    id:Int, 
    floors:Int,
    floor:Int, 
    pass:Set[Int],
    pick:Map[Int, Int]
  ) extends State with ControlSystem {

  val status: ElevatorState = this

  def pickup(a:Int, dir:Int): ElevatorState = {
    this.copy(pick = pick ++ Map(a -> dir))
  }

  def step: ElevatorState = {
    if ((pick == pick.empty) && (pass == pass.empty)) {
      this
    } else {
      val next = floor + direction
      if (next == 0) {
        val next = 1 
      }

      this.copy(
        id = id, 
        floors = floors,
        floor = next, 
        pass = pass -- Set(next) ++ board(next), 
        pick = pick - next
      )
    }
  }

  /**
   * Boards new passengers from pickup requests on the next floor.
   * Returns the updated set of passengers.
   * @param next floor stopped at 
   */
  def board(next:Int): Set[Int] = { 
    if(pick contains next) {
      
      val r = new Random() 
      val dir = pick(next)

      // Enforce positive bound
      if (dir < 0) { next match {
        case 0 => Set(r.nextInt(1))
        case 1 => Set(r.nextInt(1))
        case _ => Set(r.nextInt(next))
        }
      } else { 
        Set(r.nextInt(floors - floor + 1) + floor - 1)
      }
    } else {
      Set.empty
    }
  }

  /**
   * Returns the direction the elevator will go the next step.  
   * Prioritizes dropping passengers off over picking new passengers up.
   * Defaults to '0', which is stationary. 1 is up, -1 is down.
   */
  def direction: Int = this match {
    case ElevatorState(_,_,_, pass, _) 
      if ((near(pass).getOrElse(floor) - floor) > 0)                      => 1
    case ElevatorState(_,_,_, pass, _) 
      if ((near(pass).getOrElse(floor) - floor) < 0)                      => -1
    case ElevatorState(_,_,_, pass, pick) 
      if ((near(pick).getOrElse(floor) - floor) > 0 && pass == Set.empty) => 1
    case ElevatorState(_,_,_, pass, pick) 
      if ((near(pick).getOrElse(floor) - floor) < 0 && pass == Set.empty) => -1
    case ElevatorState(_,_,_, pass, _) 
      if ((near(pass).getOrElse(floor) - floor) == 0)                     => 0
    case ElevatorState(_,_,_,_, pick) 
      if ((near(pick).getOrElse(floor) - floor) == 0)                     => 0
    case ElevatorState(_,_,_, pass, pick) 
      if (pass == Set.empty && pick == Map.empty)                         => 0
  }

  // Returns the closest floor with a pickup request
  def near(target: Map[Int,Int]):Option[Int] = {
    allCatch opt target.minBy(x => abs(x._2 - floor))._1
  }

  // Returns the closest floor that's a passenger destination
  def near(target: Set[Int]):Option[Int] = {
    allCatch opt target.minBy(x => abs(x - floor))
  }
}
