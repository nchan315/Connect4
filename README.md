# Connect 4
For this project, I worked on a couple different bots to play Connect 4 against.
- The Easy Bot selects a random valid column as its move.
- The Medium Bot uses a simple decision tree and a heuristic function to select its move. 
    - The decision tree checks if it can win in the next move, if the next move results in a loss, then uses the heuristic function
    - The heuristic function counts the number of connections (both two-in-a-row and three-in-row) and uses that to calculate a score for the board
- The Hard Bot evaluates all possible boards after a set number of moves, then uses the minimax algorithm to find the best move.
