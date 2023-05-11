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

Getting this game up and running is a breeze! You've got two options to get the game files: either clone the repository or download it as a ZIP file.

### Cloning the Repository

If you're comfortable using a terminal, here's how you can clone the repository:

1. Fire up your terminal.
2. Execute the following command to clone this repository into a directory of your choice:

    ```bash
    git clone https://github.com/Halcyon905/pacman_ssd.git
    ```

### Downloading as a ZIP File

If you're not a fan of terminals, no problem! You can download the project as a ZIP file:

1. Head over to the repository page on GitHub: [https://github.com/Halcyon905/pacman_ssd](https://github.com/Halcyon905/pacman_ssd).
2. Locate the green "Code" button on the right side of the page and give it a click.
3. A dropdown menu will appear. From there, click on "Download ZIP".
4. Once the ZIP file is downloaded, you can extract it wherever you like.

### Running the Game with IntelliJ IDEA

Once you have the project files, running the game in IntelliJ IDEA is as simple as these steps:

1. Open up IntelliJ IDEA.
2. Head to "File" in the menu, then click on "Open..."
3. Now, you'll need to find the directory where you cloned or extracted the project. Once you've found the project folder, select it and click "OK".
4. Give IntelliJ a little bit of time to load the project and sort out the dependencies. Shouldn't take too long!
5. When the project is ready to go, find the 'MainLoop' file in the Project explorer (it's on the left sidebar).
6. Give that 'MainLoop' file a right-click and select "Run 'MainLoop.main()'" to kick off the game.


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
