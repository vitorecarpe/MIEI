## Script 00
------------
Script of exercises previous to the classes subjects itself as a reminder of the use of C pointers, GNU debugger and Valgrind.

### Used data structure
So to try and follow it as closely as possible to the script, making the structure as a linked list, where each node keeps the information about the initial free space and how many spaces free it covers.

![Script00-1](https://github.com/Pirata156/Operating-Systems/blob/master/Images/guiao00-structintvcode.png "struct intv")

### In use
At the beginning we have all places free:

![Script00-2](https://github.com/Pirata156/Operating-Systems/blob/master/Images/guiao00-allempty-res.png "Empty seats")

So let's just use a big chunk of places, let's say, 5'000 places:

![Script00-3](https://github.com/Pirata156/Operating-Systems/blob/master/Images/guiao00-ocupiedblock-res.png "Ocupied block of seats")

And then, make some free spaces in it by freeing 1'500 spaces starting from position 1'000 and then free another 1'000 spaces from position 3'000:

![Script00-4](https://github.com/Pirata156/Operating-Systems/blob/master/Images/guiao00-freeinblocks-res.png "Freeing blocks inside bigger block")

Then let's say we want to occupy 900 places again:

![Script00-5](https://github.com/Pirata156/Operating-Systems/blob/master/Images/guiao00-addtosmaller-res.png "Occupying a small portion")

That as we can see, it's been taken from the smaller free space, of all of the spaces.
