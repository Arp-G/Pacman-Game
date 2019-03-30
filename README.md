# Pacman-Game
This is a recreation of the original retro super hit game Pacman with a fresh new look and feel.
The game currently contains 3 working levels.
The game is completely designed in Java and GUI is designed using swing. 
To use the desktop swing app you must have JRE(Java Runtime Environment) installed on your machine.

Download Link: https://drive.google.com/file/d/1DPMBofyiVqeiEN2uz6mta-EPEh3JLaZH/view

# Gameplay:

  The game is simple and can be enjoyed by players of any age group. The player must control pacman the arrow keys on their keyboard.
  In order to win a level, Pacman must eat all the food items in the maze. Pacman will eat the food once he is over it. If the player manages to win all the 3 levels he will win the game.
  If the ghosts manage to eat Pacman then the player will lose.
  
# Leaderboards:

  Whenever a player loses or wins the game his/her score is recorded and can be viewed from the leaderboards option in the main menu. The leaderboard shows the best 3 scores along with the player name and time played.
  The leaderboard data is stored in a file "Leaderboards" in the game folder. You can delete this file to reset 
  leaderboard data.
  

# Levels:

* Level 1
  
  The first level introduces the game to the player and gives a mediocre challenge to the player.
  There are 4 ghosts in this level and food items distributed throughout the maze.
  
* Level 2
  
  The second level is very challenging and requires analyzing the ghosts patrolling paths and quickly timing one's entry. This level brings 4 patrolling ghosts apart from the usual 4 ghosts which follow the player
  Continuously. The patrolling ghosts move around in fixed looping paths and protect strategic food locations.
  The player must time his/her movements correctly in order to avoid getting caught by the ghosts.
  
* Level 3
  
  The third level brings a new element to the game. The map now has blue berry's which grant an advantage to the player.
  Just like the original Pacman game whenever the player eats a blueberry the ghosts become scared and run away from the 
  player for a few seconds after which they become normal once again and return to their normal behavior, during this period 
  the player can go on the offensive and eat the ghosts instead of being eaten by them, eating a ghost will permanently remove
  its presence from the maze and grant the player a huge score bonus. This effect allows the player to escape from a sticky situation. 
  
# Ghost Behavior:
  
* Normal ghosts will try to follow Pacman. The player can fool them or trap them in places within the maze, in order to 
  avoid this each ghost makes a random move after a certain time, this confuses the player and makes the ghost's movements 
  unpredictable.
  
* Patrolling Ghosts will always move in a fixed loop.

# Known Bugs:

* In rare cases the UI may not behave as expected or might crash to avoid this it is recommend to restart the game after every   playthrough

* Sometimes a ghost might move out of the map and disappear


