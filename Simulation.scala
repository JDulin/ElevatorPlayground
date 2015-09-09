package com.johndulin.playground

import scala.collection.mutable.Stack

import util.Random

// class Simulation {
//   val place = "holder"
// }

/**
 * Singleton object to run the simulation.
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

    //  Stack of sample pickup requests (Broken example)
    //  XXX val calls = Stack[(Int, Int)]((6,-1),(4,-1),(5,-1),
    //  XXX  (2,1),(1,1),(1,1), (4,1), (2,-1), (5,-1), (2,1), (2,1), (3, -1))

    // The simulation's "runtime"
    // XXX: while (calls != Stack()) {
    while (true) {
      // Output status
      println(build.elevators.toString)
      scanner.nextLine
    
      // Send a new pickup request and increment the simulation in time
      // XXX val p = calls.pop
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
} 

