import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

//        String data = scanner.nextLine(); //reads the input data as a string
//        char[][] gameField = convertDataStringToGameField(data); //converts input string to a char array used further as a game field;

        char[][] gameField = new char[3][3];
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3;j++ ){
                gameField[i][j] = ' ';
            }
        }
        int round = 1;
        char player = '\u0000';

        printGameField(gameField); //prints the game field before player's move

        while (!checkIfGameIsFinished(gameField)) {

            if (round % 2 == 0) {
                player = 'O';
            } else {
                player = 'X';
            }

            System.out.println("Enter the coordinates: ");

            makeMove(gameField, player, scanner);
            printGameField(gameField);
            round++;
        }
    }

    private static void makeMove(char[][] gameField, char xOrO, Scanner scanner) {
        boolean isCoordinateValid = false;
        String[] coordinatesTable = null;

        while (!isCoordinateValid) {

            coordinatesTable = scanner.nextLine().split(" "); //reads the coordinates and splits them by space char

            if (!(coordinatesTable.length == 2)) { //verifies if there are exactly 2 cooridnates given as an input
                continue;
            }

            if (!isNumber(coordinatesTable[0]) || !isNumber(coordinatesTable[1])) { //verifies if entered data is numbers
                System.out.println("You should enter numbers!");
                continue;
            }

            int coordinate1 = Integer.parseInt(coordinatesTable[0]); //verifies if entered data is in range 1 to 3
            int coordinate2 = Integer.parseInt(coordinatesTable[1]);
            if (coordinate1 < 1 || coordinate1 > 3 || coordinate2 < 1 || coordinate2 > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            if (!(gameField[Math.abs(coordinate2 - 3)][coordinate1 - 1] == '\u0000' || gameField[Math.abs(coordinate2 - 3)][coordinate1 - 1] == '_' || gameField[Math.abs(coordinate2 - 3)][coordinate1 - 1] == ' ')) {//cooridinate 2 = nr wiersza [2][Y]
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }

            gameField[Math.abs(coordinate2 - 3)][coordinate1 - 1] = xOrO;
            isCoordinateValid = true;
        }
    }

    private static boolean isNumber(String stringToCheck) {
        return stringToCheck.matches("[0-9]+");
    }

//    private static char[][] convertDataStringToGameField(String data) {
//        char[][] dataTable = new char[3][3];
//        int positionInTheArrayCounter = 0;
//
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                dataTable[i][j] = data.charAt(positionInTheArrayCounter);
//                positionInTheArrayCounter++;
//            }
//        }
//        return dataTable;
//    }


    private static boolean checkIfGameIsFinished(char[][] dataTable) {
        boolean xWins = checkIfCharWins(dataTable, 'X');
        boolean oWins = checkIfCharWins(dataTable, 'O');
        boolean isGameFieldFull = checkIfGameFieldIsFull(dataTable);
        boolean isPossible = checkIfGameStateIsPossible(dataTable);

        if (!isPossible) {
            System.out.println("Impossible");
            return true;
        } else if (xWins) {
            System.out.println("X wins");
            return true;
        } else if (oWins) {
            System.out.println("O wins");
            return true;
        } else if (isGameFieldFull) {
            System.out.println("Draw");
            return true;
        } else {
            return false;
        }
    }

    private static boolean checkIfGameFieldIsFull(char[][] dataTable) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (dataTable[i][j] == ' ' || dataTable[i][j] == '_' ||  dataTable[i][j] == '\u0000') {
                    return false;
                }
            }
        }
        return true;
    }

    private static void printGameField(char[][] dataTable) {
        System.out.println("---------");

        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(dataTable[i][j] + " ");
            }
            System.out.print("| \n");
        }

        System.out.println("---------");
    }

    private static boolean checkIfCharWins(char[][] dataTable, char charToCheck) {
        int counterRows = 0;
        int counterCollumns = 0;

        if (dataTable[1][1] == charToCheck) { //checking if char is winning sideways ("\" or "/" and returns true if yes)
            if (dataTable[0][0] == charToCheck && dataTable[2][2] == charToCheck || dataTable[0][2] == charToCheck && dataTable[2][0] == charToCheck) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) { //searching if the char is winning in rows or collumns and returns true if yes
            counterCollumns = 0;
            counterRows = 0;
            for (int j = 0; j < 3; j++) {
                if (dataTable[i][j] == charToCheck) {
                    counterRows++;
                }
                if (dataTable[j][i] == charToCheck) {
                    counterCollumns++;
                }
                if (counterRows == 3 || counterCollumns == 3) {
                    return true;
                }
            }
        }

        return false; //returns false when char is not winning sideways, in rows or in columns
    }

    private static boolean checkIfGameStateIsPossible(char[][] gameField) {
        int numberOfX = 0;
        int numberOfO = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameField[i][j] == 'X') {
                    numberOfX++;
                } else if (gameField[i][j] == 'O') {
                    numberOfO++;
                }
            }
        }

        if (numberOfO > numberOfX + 1 || numberOfX > numberOfO + 1) {
            return false;
        }

        if (checkIfCharWins(gameField, 'X') && checkIfCharWins(gameField, 'O')) {
            return false;
        }

        return true;
    }
}


