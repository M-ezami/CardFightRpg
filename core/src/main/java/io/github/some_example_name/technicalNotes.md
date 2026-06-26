# what exactly does the architecture look like
## what we have so far

### general architecture
- we read and write to gamestate 
- views only read from it they are the visual representation of gamestate
- almost everything except for views is a data class or a system
- the whoel architecture is event driven

### views
- there is a parent view which is boardview
- boardview gets its sizes from boardlayout
- boardlayout are hardcoded sizes of the board they define how the baord is split
- boardview draws and updates all other views so basically it is the entire view and there are smaller views/sections of the board which draws function it calls like handview
- there is a hierarchy of views boardview -> handview -> cardview, boardview -> monsterfieldview -> monsterview
- all of them read from gamestate
---
### systems
- One system owns the state mutation responsibility for a given event.
#### playing cards system
- One system owns the state mutation responsibility for a given event.
- this mentioned rule is what we try to follow this system is a good example for this 
- cardplaysystem reacts to the cardplayed event and reads and modifies gamestate
