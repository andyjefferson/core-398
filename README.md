core-398
========

Project demonstrating some operations on use of a Map field using the JDO API.
The issue revolved around how an update to the Map was performed. 
With versions such as v6.0.0-m1 it ended up with an incorrect number of
elements in the Map due to not knowing what was already there.

