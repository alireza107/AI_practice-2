import java.util.Scanner;
import java.util.Random;
public class HillClimbingRandomRestart {
    private static int stepsClimbedAfterLastRestart = 0;
    private static int stepsClimbed =0;
    private static int heuristic = 0;
    private static int randomRestarts = 0;

    //Method to create a new random board
    public static NQueen[] generateBoard() {
        NQueen[] startBoard = new NQueen[8];
        Random rndm = new Random();
        for(int i=0; i<8; i++){
            startBoard[i] = new NQueen(rndm.nextInt(8), i);
        }
        return startBoard;
    }

    //Method to print the Current State
    private static void printState (NQueen[] state) {
        //Creating temporary board from the present board
        int[][] tempBoard = new int[8][8];
        for (int i=0; i<8; i++) {
            //Get the positions of Queen from the Present board and set those positions as 1 in temp board
            tempBoard[state[i].getRow()][state[i].getColumn()]=1;
        }
        System.out.println();
        for (int i=0; i<8; i++) {
            for (int j= 0; j < 8; j++) {
                System.out.print(tempBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Method to find Heuristics of a state
    public static int findHeuristic (NQueen[] state) {
        int heuristic = 0;
        for (int i = 0; i< state.length; i++) {
            for (int j=i+1; j<state.length; j++ ) {
                if (state[i].ifConflict(state[j])) {
                    heuristic++;
                }
            }
        }
        return heuristic;
    }

    // Method to get the next board with lower heuristic
    public static NQueen[] nextBoard (NQueen[] presentBoard) {
        NQueen[] nextBoard = new NQueen[8];
        NQueen[] tmpBoard = new NQueen[8];
        int presentHeuristic = findHeuristic(presentBoard);
        int bestHeuristic = presentHeuristic;
        int tempH;

        for (int i=0; i<8; i++) {
            //  Copy present board as best board and temp board
            nextBoard[i] = new NQueen(presentBoard[i].getRow(), presentBoard[i].getColumn());
            tmpBoard[i] = nextBoard[i];
        }
        //  Iterate each column
        for (int i=0; i<8; i++) {
            if (i>0)
                tmpBoard[i-1] = new NQueen (presentBoard[i-1].getRow(), presentBoard[i-1].getColumn());
            tmpBoard[i] = new NQueen (0, tmpBoard[i].getColumn());
            //  Iterate each row
            for (int j=0; j<8; j++) {
                //Get the heuristic
                tempH = findHeuristic(tmpBoard);
                //Check if temp board better than best board
                if (tempH < bestHeuristic) {
                    bestHeuristic = tempH;
                    //  Copy the temp board as best board
                    for (int k=0; k<8; k++) {
                        nextBoard[k] = new NQueen(tmpBoard[k].getRow(), tmpBoard[k].getColumn());
                    }
                }
                //Move the queen
                if (tmpBoard[i].getRow()!=8-1)
                    tmpBoard[i].move();
            }
        }
        //Check whether the present bord and the best board found have same heuristic
        //Then randomly generate new board and assign it to best board
        if (bestHeuristic == presentHeuristic) {
            randomRestarts++;
            stepsClimbedAfterLastRestart = 0;
            nextBoard = generateBoard();
            heuristic = findHeuristic(nextBoard);
        } else
            heuristic = bestHeuristic;
        stepsClimbed++;
        stepsClimbedAfterLastRestart++;
        return nextBoard;
    }

    public static void main(String[] args) {
        int presentHeuristic;

        System.out.println("Solution to 8 queens using hill climbing with random restart:");
        //Creating the initial Board
        NQueen[] presentBoard = generateBoard();
        presentHeuristic = findHeuristic(presentBoard);
        // test if the present board is the solution board
        while (presentHeuristic != 0) {
            //  Get the next board
            // printState(presentBoard);
            presentBoard = nextBoard(presentBoard);
            presentHeuristic  = heuristic;
        }
        //Printing the solution
        printState(presentBoard);
        System.out.println("\nTotal number of Steps Climbed: " + stepsClimbed);
        System.out.println("Number of random restarts: " + randomRestarts);
        System.out.println("Steps Climbed after last restart: " + stepsClimbedAfterLastRestart);
    }
}