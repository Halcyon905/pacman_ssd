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
There are two options to get the game files: either clone the repository or download it as a ZIP file.

### Cloning the Repository

1. Fire up your terminal.
2. Execute the following command to clone this repository into a directory of your choice:

    ```bash
    git clone https://github.com/Halcyon905/pacman_ssd.git
    ```

### Downloading as a ZIP File

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
Upon launching the game, click on the "Play" button to proceed. You will then be prompted to select a map of your choice to begin gameplay.

The player assumes control of the character Pacman, whose objective is to navigate through the selected map with the aim of consuming all the pellets. The character's movements can be controlled using the WASD keys on your keyboard, with 'W' for turn north, 'A' for turn left, 'S' for turn down, and 'D' for turn right.

However, the game also presents challenges in the form of ghosts that roam the map. Contact with these entities results in the loss of a life, thus it is imperative to maintain vigilance and avoid these characters.

The consumption of pellets does not merely serve the objective of clearing the map but also contributes towards your overall score. Additionally, there are special power pellets scattered around the map. Consuming a power pellet temporarily makes the ghosts vulnerable, providing an opportunity for Pacman to consume the ghosts for extra points.

## Game Demo
If you'd like to get a sense of the gameplay before diving in, we've got you covered. We've prepared a demo of the game for you to enjoy. See Pacman in action and get a feel for the different maps available. Click on the link below to watch the game demo on YouTube:  
**[Watch the Game Demo Here](https://youtu.be/7qwLEeWbusI)**


## Team Members

Punn Chunwimaleung: 6410545533  
Supakrit Aphonmaeklong: 6410545789  
Pakorn Laohakanniyom: 6410545550  
