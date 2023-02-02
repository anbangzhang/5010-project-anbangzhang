# 5010-project-anbangzhang
 name: Anbang Zhang
 
 preferred name: Anbang

 email: zhang.anb@northeastern.edu
 
 #Project Introduction
 This project is the project of CS5010, and it's a board game that is loosely inspired by Doctor Lucky series of games.
 
 Running this project will create a graphical image of the world specified in the input txt file.
 #How to Run
 The Jar file can be executed with the following instructions:
 1. Open the terminal line and navigate to the folder where the jar is located on your PC.
 2. Execute the following command:

        java -jar WorldDriver.jar `input_file_name` `output_directory`

    Sample:

        java -jar WorldDriver.jar mansion.txt ./
 The above command is going to take `mansion.txt` file as input world specification and generate image at `./` directory.
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