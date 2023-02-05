 #5010-project-anbangzhang

 name: Anbang Zhang
 
 preferred name: Anbang

 email: zhang.anb@northeastern.edu
 
 #Project Introduction

 This project is the project of CS5010, and it's a board game that is loosely inspired by Doctor Lucky series of games.
 
 Running this project will create a graphical image of the world specified in the input txt file.

 #Input File Format

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

 #How to Run

 The Jar file can be executed with the following instructions:
 1. Open the terminal line and navigate to the folder where the jar is located on your PC.
 2. Execute the following command:

        java -jar WorldDriver.jar `input_file_name`

    Sample:

        java -jar WorldDriver.jar mansion.txt

 The above command is going to take `mansion.txt` file as input world specification.

 Then the program is going to print some instructions to guide you, eg

    Please use the order number below to select the function:
        1. getAllSpaces
        2. getNeighbors
        3. getSpace
        4. getTargetPosition
        5. moveTarget
        6. showGraphicalImage
        7. exit

 An input of number is required here.

 **getAllSpaces**

 Input `1` here, then the program is going to print all the spaces in the map:

    The spaces: [Armory, Billiard Room, Carriage House, Dining Hall, Drawing Room, Foyer, Green House, Hedge Maze, Kitchen, Lancaster Room, Library, Lilac Room, Master Suite, Nursery, Parlor, Piazza, Servants' Quarters, Tennessee Room, Trophy Room, Wine Cellar, Winter Garden]

 **getNeighbors**

 Input `2` here, then the program is going to require an input of space index, then the instruction is like below:

    Please input the index of space.

 Input '4' as the index of space, then the program is going to print the neighbors of this space like below:

    The neighbors of 2th space: [Armory, Dining Hall, Foyer, Wine Cellar]

 Input an invalid index eg `-1`, then the program is going to print an empty list like below:

    The neighbors of -1th space: []

 **getSpace**

 Input '3' here, then the program is going to require an input of space index, then the instruction is like below:

    Please input the index of space.

 Input `1` as the index of space, then the program is going to print the space info like below:

    The space is Billiard Room, its neighbors: [Armory, Dining Hall, Trophy Room], weapons inside this space: [Billiard Cue]

 Input an invalid index eg `-1`, then the program is going to print response like below:

    The space is null.

 **getTargetPosition**

 Input '4' here, then the program is going to print the name of target's current space like below:

    The target is at space Armory.

 **moveTarget**

 Input `5` here, then the program is going to move the target to the next space and print the space name after move:

    The target moves to space Billiard Room.

 **showGraphicalImage**

 Input `6` here, then the program is going to require an input of output directory like below:

    Please input the output directory:

 Input `./` as the output directory, then the program is going to generate a png file at `./` directory and print the status like below:

    Graphical image has been generated, please check the ./ directory.

 **exit**

 Input `7` here, then the program is going to exit.