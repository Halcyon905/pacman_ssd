# PACMAN game

## About the Game
This repository has been created as a part of the
project for Software Specification & Design Lab.
It contains the Java code for the classic arcade
game, Pacman. The player plays as pacman and 
their job is to eat all the pellets in order 
to win and attempt to gain the highest score 
possible.   
To elevate your experience, we've added
a feature that allows you to select your 
desired map before the game begins. So, 
aim for the highest score and enjoy the game!

## Design Patterns Used
The following design patterns have been implemented in the game:  
**Strategy Pattern**: We have used the Strategy pattern to create different ghost 
AI strategies to hunt down the player. Each ghost has a different strategy for 
movement. This pattern allows us to encapsulate the behavior of the 
ghosts and switch between them dynamically, depending on the game's state.

**State Pattern**: The State pattern is used to switch between normal and power-up states. 
When the player eats a power pellet, the ghosts become vulnerable, and the player can eat 
them for a limited time. This pattern allows us to encapsulate the different states of the 
game and change the behavior of the entities accordingly.

**Flyweight Pattern**: We have used the Flyweight pattern to load the game's images like ghost efficiently. 
Instead of loading all the images for each entity separately, we load them once and reuse them 
throughout the game. This pattern reduces memory usage and improves the game's performance.

## Prerequisites
- Java JDK (recommended for version 17)
- Java Swing


## Installing and Running the Game

Follow these steps to get the game running:

1. Clone this repository into your desired directory using the following command:
```git clone https://github.com/Halcyon905/pacman_ssd.git```
2. Navigate into the cloned directory.
3. Run the 'MainLoop' file to start the game

## Gameplay
To play the game, use the WASD keys on your keyboard
to navigate Pacman through the walls. Eating pellets will 
increase your score, and eating power pellets will make the ghosts
vulnerable for a short period. Avoid contact with the ghosts, 
as they will cause you to lose a life.

## Team Members

Punn Chunwimaleung: 6410545533  
Supakrit Aphonmaeklong: 6410545789  
Pakorn Laohakanniyom: 6410545550  