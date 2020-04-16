public class SelectInSquareVariable implements VariableHeuristic {

    // count empty variables in each square
    // return first empty variable in square where is the least empties fields
    @Override
    public int[] selectVariable(Sudoku sudoku, int row, int col) {
        int[] temp = {row, col};
        int[][] count = new int[Sudoku.SQUARE_SIZE][Sudoku.SQUARE_SIZE];

        int min_i = 0, min_j = 0, minCount = Sudoku.SUDOKU_SIZE + 1;
        for (int i = 0; i < Sudoku.SUDOKU_SIZE; i = i+3) {
            for (int j = 0; j < Sudoku.SUDOKU_SIZE; j = j + 3) {
                count[(i / 3)][(j / 3)] = countEmptyInSquare(sudoku, i, j);
                if(count[(i / 3)][(j / 3)] < minCount && count[(i / 3)][(j / 3)] > 0) {
                    minCount = count[(i / 3)][(j / 3)];
                    min_i = i / 3;
                    min_j = j / 3;
                }
            }
        }

        // that means there are no empty squares
        if(minCount == Sudoku.SUDOKU_SIZE + 1)
            return temp;

        // check next empty variable in square where is the least empties fields
        for(int i=0; i<Sudoku.SQUARE_SIZE; i++){
            for(int j=0; j<Sudoku.SQUARE_SIZE; j++) {
                if(sudoku.getVariables()[min_i * 3 + i][min_j * 3 + j].value == '0') {
                    sudoku.findDomain(sudoku.getVariables()[min_i * 3 + i][min_j * 3 + j], min_i * 3 + i, min_j * 3 + j); //
                    temp[0] = min_i * 3 + i;
                    temp[1] = min_j * 3 + j;
                    return temp;
                }
            }
        }
        return temp;
    }

    private int countEmptyInSquare(Sudoku sudoku, int num_of_row, int num_of_col){
        int countEmpty = 0;
        int temp1 = num_of_row % 3;
        int temp2 = num_of_col % 3;

        for(int i=0; i<Sudoku.SQUARE_SIZE; i++){
            for(int j=0; j<Sudoku.SQUARE_SIZE; j++) {
                if (sudoku.getVariables()[num_of_row - temp1 + i][num_of_col - temp2 + j] .value == '0') {
                    countEmpty ++;
                }
            }
        }
        return countEmpty;
    }
}
