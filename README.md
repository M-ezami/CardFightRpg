# What exactly does the architecture look like
## What we have so far

### General architecture
- we read and write to gamestate 
- views only read from it they are the visual representation of gamestate
- almost everything except for views is a data class or a system
- the whoel architecture is event driven
- we have to be careful with what is an event for example handchangedevent shouldntt be an event 
- handview should just be reading from gamestate  -> deck and update itself constatnly 
---

### Views
- there is a parent view which is boardview
- boardview gets its sizes from boardlayout
- boardlayout are hardcoded sizes of the board they define how the baord is split
- boardview draws and updates all other views so basically it is the entire view and there are smaller views/sections of the board which draws function it calls like handview
- there is a hierarchy of views boardview -> handview -> cardview, boardview -> monsterfieldview -> monsterview
- all of them read from gamestate
---

### Systems
- One system owns the state mutation responsibility for a given event.
#### Playing cards system
- One system owns the state mutation responsibility for a given event.
- this mentioned rule is what we try to follow this system is a good example for this 
- cardplaysystem reacts to the cardplayed event and reads and modifies gamestate
---

### Input handling
- there are different input handlers and we have an input router which sets an input router based on the stage of the game
- this is kind of bullshit but i thouhgt this would be a good idea to do it idk im not sure
- im going to roll with it for now
---


### Possible Improvements
- clearer seperation between phases maybe some sort of controller or something
