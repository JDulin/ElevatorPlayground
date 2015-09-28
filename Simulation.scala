package com.johndulin.playground

import scala.collection.mutable.Stack

import util.Random

/**
 * Singleton object to launch and run the simulation.
 * Instantiates a 'Building' object with elevators and floors.
 *
 * Queries up a series of floor calls for elevators in 'calls' value.
 * 
 * Steps through the simulation by key prompts - Outputs building state to console.
 */
object Simulation extends App {
    println("\n Beginning building simulation. \n")
    val random = new Random()
    val scanner = new java.util.Scanner(System.in)
    var build = Building(args(0).toInt, args(1).toInt)

    while (true) {
      // Output status, increment step
      output(build) 
      scanner.nextLine
    
      // Send a new pickup request and increment the simulation in time
      val p = request(build.floors)
      build = build.pickup(p._1, p._2)
      build = build.step
    }

    /** 
     *  Produce a random pickup request
     */
    def request(f: Int): (Int, Int) = {
      (random.nextInt(f), direction)
    }

    /**
     * Produce a random direction of a pickup request
     */
    def direction = {
      val dir = random.nextInt(2)
      if (dir == 1) {
        1 } else (-1)
    }

    /**
     * Pretty printing of BuildingState.
     * @param building BuildingState to represent
     */
    def output(building: BuildingState): Unit = {
      println("   Elevators' Pickup Requests   ")
      for (e <- building.elevators) {
        println(e.id + " : " + e.pick)
      }
      println("")

      println("*" * 5 * building.elevators.length)

      println(building.elevators.toString)
    }
} 

