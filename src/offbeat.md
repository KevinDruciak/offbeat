# offbeat

offbeat

Author: Kevin Druciak

Email: kevintdruciak@gmail.com

This is my first java game. It is a rhythm game similar to the popular rhythm game "osu!". 
Current implemented gamemodes include 4-key mania and standard osu. Beatmaps are parsed
"osu!" beatmap files.

Currently looking into better methods of coding a rhythm game.
I am leaning towards coding it in C# or learning C# and Unity should I have to. 

```
![offbeat](./ex/offbeat.gif)
```

## TODO

* center fullscreen (works on mac idk lol)  
* mods implementation  
* user specified beatmap offsets  
* saving scores/offsets to file  
* finish user interface  
* online profile saving (gotta learn how to make a website 0.o)  
* local settings saving  
* redo window resizing?  
 - make global variables to reduce calculations in image proccessing  
 - maybe dont use Jframe? idk  
* fix osu gametype  
 - need to optimize code to process input accurately  
* redo notespawners by mapping spawner to bpm instead of System time  
* redo songselect background transition  
* streamline beatmap importing by filtering unnecessary files  
* implement hitsounds  
* make mp3 to wav converter so I dont have to use the horrible javafx MediaPlayer library  
* detect user's operating system and update game coordinates accordingly  
 - not only is top-left not (0, 0) for both windows and osx, it is a different coordinate for   
 	both operating systems  
* create gui assets  
* support 16*9 aspect ratio bc 4*3 was not a smart idea  
* optimize  
* optimize  
* optimize  
* why is my fps so low  