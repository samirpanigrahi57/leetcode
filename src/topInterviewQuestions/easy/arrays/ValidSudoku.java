package topInterviewQuestions.easy.arrays;

public class ValidSudoku {
    public static void main(String[] args) {
        char[][] invalidSudoku = {{'8', '3', '.', '.', '7', '.', '.', '.', '.'}
                , {'6', '.', '.', '1', '9', '5', '.', '.', '.'}
                , {'.', '9', '8', '.', '.', '.', '.', '6', '.'}
                , {'8', '.', '.', '.', '6', '.', '.', '.', '3'}
                , {'4', '.', '.', '8', '.', '3', '.', '.', '1'}
                , {'7', '.', '.', '.', '2', '.', '.', '.', '6'}
                , {'.', '6', '.', '.', '.', '.', '2', '8', '.'}
                , {'.', '.', '.', '4', '1', '9', '.', '.', '5'}
                , {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};
        char[][] validSudoku = {{'5', '3', '.', '.', '7', '.', '.', '.', '.'}
                , {'6', '.', '.', '1', '9', '5', '.', '.', '.'}
                , {'.', '9', '8', '.', '.', '.', '.', '6', '.'}
                , {'8', '.', '.', '.', '6', '.', '.', '.', '3'}
                , {'4', '.', '.', '8', '.', '3', '.', '.', '1'}
                , {'7', '.', '.', '.', '2', '.', '.', '.', '6'}
                , {'.', '6', '.', '.', '.', '.', '2', '8', '.'}
                , {'.', '.', '.', '4', '1', '9', '.', '.', '5'}
                , {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};

        char[][] inValidFailedCase = {
                {'.', '.', '5', '.', '.', '.', '.', '.', '.'}
                , {'.', '.', '.', '8', '.', '.', '.', '3', '.'}
                , {'.', '5', '.', '.', '2', '.', '.', '.', '.'}
                , {'.', '.', '.', '.', '.', '.', '.', '.', '.'}
                , {'.', '.', '.', '.', '.', '.', '.', '.', '9'}
                , {'.', '.', '.', '.', '.', '.', '4', '.', '.'}
                , {'.', '.', '.', '.', '.', '.', '.', '.', '7'}
                , {'.', '1', '.', '.', '.', '.', '.', '.', '.'}
                , {'2', '4', '.', '.', '.', '.', '9', '.', '.'}};

        // System.out.println(isValidSudoku(validSudoku));
        //System.out.println(isValidSudoku(invalidSudoku));
        System.out.println(isValidSudoku(inValidFailedCase));


    }

    public static boolean isValidSudoku(char[][] board) {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!checkRow(board, i, j) || !checkColumn(board, i, j) || !check3x3Matrix(board, i, j)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean check3x3Matrix(char[][] board, int i, int j) {
        char val = board[i][j];
        int leftMax = (i - (i % 3)) + 2;
        int rightMax = (j - (j % 3)) + 2;
        if (val != '.') {
            for (int left = i - (i % 3); left <= leftMax && left != i; left++) {
                for (int right = j - (j % 3); right <= rightMax && right != j; right++) {
                    if (board[left][right] != '.' && board[left][right] == val) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean checkRow(char[][] board, int i, int j) {
        char val = board[i][j];
        int x = i + 1;
        int y = j;
        while (x < 9) {
            if (board[x][y] != '.' && board[x][y] == val) {
                return false;
            }
            x++;
        }
       // System.out.println("row check passed");
        return true;
    }

    private static boolean checkColumn(char[][] board, int i, int j) {
        char val = board[i][j];
        int x = i;
        int y = j + 1;
        while (y < 9) {
            if (board[x][y] != '.' && board[x][y] == val) {
                return false;
            }
            y++;
        }
       // System.out.println("column check passed");
        return true;
    }

}
