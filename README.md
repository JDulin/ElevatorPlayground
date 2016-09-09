## Elevator Playground:  A Simple Scala Scheduler

This elevator simulation provides an interface for the simplest 
elevator commands and an implementation of the SCAN algorithm 
to improve on the naive first-come, first-served (FCFS) 
elevator scheduling algorithm.  

Fun fact: The SCAN algorithm is qualitatively similar to the 
most basic disk-scheduling algorithm!  Reading in some data is similar to picking up a person, 
and writing data can be thought of as dropping the person 
off somewhere. Regular disk hard-drives 
have large cylinders of addresses which can be thought of as a rolled up building.

Data Structures:

-- The `trait ControlSystem` is given as the system interface.
It defines 3 basic functions for elevator control: pickup, step, and status.
This is implemented by both state case classes.

-- State is managed in two case classes, a `BuildingState` and an `ElevatorState`.
Both of these inherit from the empty class `State`, which exposes
the 3 control functions.  They are then ovveridden for the `BuildingState` 
and `ElevatorState`.

-- `BuildingState` holds a list of `ElevatorStates` which correspond to all 
of the building's elevators as well as the number of floors in the building.

-- `ElevatorState` holds: an elevator ID which corresponds to the elevator's index 
in the building's vector, the number of floors it reaches, the floor it is currently on, 
a Set[Int] object which abstracts all of the destination floors of passengers, 
and a Map[Int, Int] object which abstracts the floors of pickup requests and their 
direction.

-- Both state objects are initialized in the script by the `Building` and `Elevator` 
factory objects by the apply function.

-- The script `Simulation` contains the main thread for the simulation and 
the genuinely mutable building state.

Improvements on FCFS:

-- First passed, first delivered:  When carrying multiple passengers with different 
destinations, we drop them off in the order of the floors passed.  I.e.: A person 
going to the 3rd floor from the 2nd with another person going to the 4th will not 
have to make the trip to the 4th, even if the other person got on first.

-- Greedy pickup:  The same 'greedy' logic of dropping riders off the
elevator applies to loading them on.  When a new pickup request is received,
the control system excludes any elevators from possible assignment which are
going the other way from the passenger - It considers the range of floors
each elevator is currently scheduled to pass over, and the range of floors
a person requesting to be picked up could possibly request to go to,
and only assigns elevators whose possible destinations intersect with the
possible requested destinations of a person to be picked up.
Essentially, pickup requests are only assigned to elevators whose set of possible destinations
is a superset of the set of possible destinations of the elevator.

(Note: I have heard disturbing rumors of bugs in this code.)

The simplest aspiration of the elevator dispatch algorithm is to never have a
passenger in an elevator pass the same floor twice.

## Running code

Build and run using Scala Build Tool (sbt) by specifying parameters for 
the number of elevators and the height of the building you want to simulate.
For example,

`$ sbt "run 1 6"`

will launch the program with 1 elevator spanning 6 floors.

In `Simulation.scala` there is a stack of incoming pickup requests.  They can be edited to 
test a wider range of elevator system behavior.  By default, the program produces a pickup
request at every step and ends when the stack is depleted.  Press a key at each 
step to produce the next step output in the command line.

Cancel prematurely with Ctrl-C.

##  Missing Features and the Complexity of Elevator Algorithms

This algorithm misses many of the subtleties and possible 
optimization methods of real elevators.  Some current problems with the simulation:

- The simulation is not based on actual time-series data.  
What is the rate of pickup requests to steps? 
- Does not consider multiple passengers with different destinations picked up at the 
same floor.
- Does not simulate different rates of elevator demand (At the beginning of the day, end of the day.)
- Cannot model tiered elevators in the Fixed Sectoring Common Sector System.
- Cannot model 'express' elevators in supertall buildings.
- Cannot model 'falling' elevators which rush to the top to drop 
passengers off as they go down (One way around the 'double-stopping' problem.).
- Cannot let pickup requests include a floor destination
- Does not program waiting positions for elevators (Which would be different for 
different buildings.). 
- Assumes equal likelihood of pickup requests for all floors.
I.e.: The simulation does not weight the ground floor for both pickups and destinations.
- Does not consider energy usage, elevator wear, or out of order elevators.  

## Some More Theory

Standards - https://www.quora.com/Is-there-any-public-elevator-scheduling-algorithm-standard

Elevator calls are assigned to the elevator best 
placed to answer that call according to three criteria 
that are used to compute a figure of suitability (FS)
for each elevator. 
(1) If an elevator is 
moving towards a call, and the call is in the same 
direction, FS = (N + 2) - d, where N is one less 
than the number of floors in the building, and d is 
the distance in floors between the elevator and the passenger call.  
(2) If the elevator is moving towards the call, 
but the call is in the opposite direction, FS = (N + 1) - d.  
(3) If the elevator is moving away from the point of call, 
FS = 1. The elevator with the highest FS for each call is 
sent to answer it. The search for the "nearest car" 
is performed continuously until each call is serviced.

Dynamic Sectoring - Floors are grouped into sectors which are served by different 
elevators.  These change as elevator positions change. 

Fixed Sectoring Common Sector System - Different sets of elevators for differenet 
elevators.

Fixed Sectoring Priority Sector System - Divided into up and down sectors 
rated with priorities.


