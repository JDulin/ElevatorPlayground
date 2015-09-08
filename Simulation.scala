package com.johndulin.playground

import scala.collection.mutable.Stack

trait Thing {
  val compilePlease = 1
}

/**
 * Singleton object to run the simulation.
 * Instantiates a 'Building' object with elevators and floors.
 *
 * Queries up a series of floor calls for elevators in 'calls' value.
 * 
 * Steps through the simulation by key prompts - Outputs building state to console.
 */
// object Main {
//   def runit() = {
//     println("\n Beginning building simulation. \n")
//     val scanner = new java.util.Scanner(System.in)
//     var build = Building(1, 6)
// 
//     //  Stack of incoming pickup requests.
//     val calls = Stack[(Int, Int)]((6,-1),(4,-1),(5,-1),
//       (2,1),(1,1),(1,1), (4,1), (2,-1), (5,-1), (2,1), (2,1), (3, -1))
// 
//     // The simulation's "runtime"
//     while (calls != Stack()) {
//       // Output status
//       println(build.elevators.toString)
//       scanner.nextLine
//     
//       // Send a new pickup request and increment the simulation in time
//       val p = calls.pop
//       build = build.pickup(p._1, p._2)
//       build = build.step
//     } 
//   }
// } 
// 
