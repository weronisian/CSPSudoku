public class SelectMostLimitedVariable implements VariableHeuristic{

    // check all empty variables
    // return most limited variable (with the smallest domain)
    @Override
    public int[] selectVariable(Sudoku sudoku, int row, int col) {
        int[] temp = {row, col};
        int minDomain = Sudoku.SUDOKU_SIZE + 1;
        for (int i = 0; i < Sudoku.SUDOKU_SIZE; i++) {
            for (int j = 0; j < Sudoku.SUDOKU_SIZE; j++) {
                if (sudoku.getVariables()[i][j].value == '0') {
                    sudoku.findDomain(sudoku.getVariables()[i][j], i, j);
                    if(minDomain > sudoku.getVariables()[i][j].getDomainSize()){
                        minDomain = sudoku.getVariables()[i][j].getDomainSize();
                        row = i;
                        col = j;
                   }
                }
            }
        }
        temp[0] = row;
        temp[1] = col;
        return temp;
    }
}
