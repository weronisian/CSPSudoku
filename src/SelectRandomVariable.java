public class SelectRandomVariable implements VariableHeuristic {

    @Override
    public int[] selectVariable(Sudoku sudoku, int row, int col) {
        int temp [] = {row, col};
        boolean isEmpty = false;
        while(!isEmpty) {
            int randRow = (int) (Math.random()*Sudoku.SUDOKU_SIZE);
            int randCol = (int) (Math.random()*Sudoku.SUDOKU_SIZE);
            if (sudoku.getVariables()[randRow][randCol].value == '0') {
                temp[0] = randRow;
                temp[1] = randCol;
                isEmpty = true;
            }
        }
        return temp;
    }
}
