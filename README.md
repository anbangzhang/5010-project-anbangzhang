 # 5010-project-anbangzhang

 name: Anbang Zhang
 
 preferred name: Anbang

 email: zhang.anb@northeastern.edu
 
 # Project Introduction

 This project is the project of CS5010, and it's a board game that is loosely inspired by Doctor Lucky series of games.
 
 Running this project will create a graphical image of the world specified in the input txt file.

 # Input File Format

 The input file specifies the world map in the following three sections, you can refer to the sample _/res/mansion.txt_.
  
  1. World description including the size, the name, and the target character.
  2. A detailed list of all of the spaces or rooms that make up the world
  3. A detailed list of all of the items that can be found in the world
  
 Each section is specified as follows:
  
  * World description consists of two lines:
  
     - the first line contains the number of rows, the number columns, and the name of the world
     - the second line contains the health and name of the target character
  
  * Spaces or rooms are specified in an ordered, 0-indexed list. The first line of this section contains a single number that represents the number of spaces or rooms that make up the world. Then each space or room is specified on its own line containing:
  
     - (row, col) of the upper left corner
     - (row, col) of the lower right corner
     - the name of the space or room
  
  * Items are also specified in an ordered list. The first line of this section contains a single number that represents the number of items. Then each item is specified on its own line containing:
  
     - the index of the room in which the item can be found
     - the amount of damage the item could do if it was used to attack the target character
     - the name of the item

 # How to Run

 The Jar file can be executed with the following instructions:
 1. Open the terminal line and navigate to the folder where the jar is located on your PC.
 2. Execute the following command:

        java -jar WorldDriver.jar `input_file_name` `turn_amount`

    Sample:

        java -jar WorldDriver.jar mansion.txt 3

 The above command is going to take `mansion.txt` file as input world specification, and specify that there are `3` rounds in the game.

 # Example Run
 
 There are two example runs in the `res/example/` directory.
 
 **Run 1**

 * Start the program with `mansion.txt` as the world specification and `3` as the turn amount.
 
 * Create a human-controlled player, its name is `Adam`, its starting space index is `8`, it has unlimited weapon limit.
 
 * Create a computer-controlled player, its name is `Eve`, its starting space index is `6`, its weapon limit is `2`.
 
 * Creating a computer-controlled player failed due to repeated name, its name is `Eve`, its starting space index is `4`, its weapon limit is `2`.
 
 * Quit player creation process.
 
 * Display all the spaces in the world.
 
 * Display the details of 2nd space in the world.
 
 * Generate a graphical image at `res/` directory.
 
 * Quit the program.
 
 **Run 2**

 * Start the program with `mansion.txt` as the world specification and `3` as the turn amount.
 
 * Create a human-controlled player, its name is `Adam`, its starting space index is `8`, it has unlimited weapon limit.
 
 * Create a human-controlled player, its name is `Eve`, its starting space index is `4`, its weapon limit is `2`.
 
 * Create a computer-controlled player, its name is `Arthur`, its starting space index is `1`, its weapon limit is `4`.

 * Quit player creation process.
 
 * Start the game.
 
 * At each turn for the player, the player's detail is displayed.
 
 * In the 1st turn, human-controlled player `Adam` picks up the weapon `Crepe Pan`.
 
 * In the 1st turn, human-controlled player `Eve` fails to pick up the weapon `Trowel`, then picks up the weapon `Letter Opener`.
 
 * In the 1st turn, computer-controlled player `Arthur` looks around the space.
 
 * In the 2nd turn, human-controlled player `Adam` moves to a neighbor space `Wine Cellar`.
 
 * In the 2nd turn, human-controlled player `Eve` moves to a neighbor space `Wine Cellar`.
 
 * In the 2nd turn, computer-controlled player `Arthur` looks around the space.
 
 * In the 3rd turn, human-controlled player `Adam` looks around the space.
 
 * In the 3rd turn, human-controlled player `Eve` picks up the weapon `Rat Poison`.
 
 * In the 3rd turn, computer-controlled player `Arthur` looks around the space.
 
 * The game ends, each player's detail is displayed.
 
 # Game Instructions

 The program is going to print some instructions to guide you
 
 **Create Player**

 Select player type:
 
    Please input the type of player to create:
            1. human-controlled
            2. computer-controlled
            q. quit creating

 Input player name:

    Please input the name of player:

 Input player space index:

    Please input the space index that the player created at:
  
 Input player weapon limit:
 
    Please input the weapon limit of player, -1 indicates no limit:
 
 **Game Menu** 
 
    Please input the number below to select the function:
            1. displayAllSpaces
            2. displaySpaceDetail
            3. displayGraphicalImage
            4. startGame
            q. exit

 Input space index:
 
    Please input the space index:
 
 Input image output directory:
 
    Please input the output directory:
 
 Player action selection:
 
    Please use the number below to select thea action for player [Adam]
            1. move to a neighbor space.
            2. pick up a weapon in the space.
            3. look around the space.
 
 Input neighbor name:
 
    Please input a neighbor space name from the neighbors: [Dining Hall, Parlor, Wine Cellar]
 
 Input weapon name:
 
    Please input a weapon name from the weapons: [Rat Poison, Piece of Rope]
