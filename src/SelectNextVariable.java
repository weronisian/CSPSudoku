public class SelectNextVariable implements VariableHeuristic {

    // check next variable in order of definition
    // return first empty variable
    @Override
    public int[] selectVariable(Sudoku sudoku, int row, int col) {
        int[] temp = {row, col};
        for (int i = 0; i < Sudoku.SUDOKU_SIZE; i++) {
            for (int j = 0; j < Sudoku.SUDOKU_SIZE; j++) {
                if (sudoku.getVariables()[i][j].value == '0') {
                    sudoku.findDomain(sudoku.getVariables()[i][j], i, j);   //
                    temp[0] = i;
                    temp[1] = j;
                    return temp;
                }
            }
        }
        return temp;
    }
}
