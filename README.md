Chess web application using Spring Boot.

It's still under development.

This program also open your Firefox with localhost:8080/lobby so if you don't have Firefox then open your browser and type http://localhost:8080/lobby

Update:
This project uses MySql Workbench in order to manage the saved data.
The matches: are created in database once the lobby form is completed
             are read in appData at the start of the program's execution
             are updated after every move
             and deleted once the game ends (through checkmate or draw)
          (all these actions are saved inside audit.csv)
             
