import java.util.ArrayList;

public class SolveSudokuForward {

    private Sudoku sudoku;
    private long start, end, time_to_first, time;
    private int nodes_to_first = 0, all_nodes = 0;
    private int backtracks_to_first = 0, all_backtracks = 0;
    private ArrayList<Sudoku> solutions;
    private VariableHeuristic variableHeuristic;
    private ValueHeuristic valueHeuristic;

    public SolveSudokuForward(Sudoku sudoku, VariableHeuristic varH, ValueHeuristic valH){
        this.sudoku = sudoku;
        this.variableHeuristic = varH;
        this.valueHeuristic = valH;
        solutions = new ArrayList<Sudoku>();

        start = System.currentTimeMillis();
        solve(sudoku);
        end = System.currentTimeMillis();

        if (!solutions.isEmpty()) {
            System.out.println("First solution:");
            solutions.get(0).display();
            System.out.println("Time to first solution: "+ time_to_first + " ms");
            System.out.println("Visited nodes to first solution: " + nodes_to_first);
            System.out.println("Backtracks to first solution: " + backtracks_to_first);
            System.out.println("\nFound " + solutions.size() + " solutions");
        }
        else {
            System.out.println("No solution found");
        }

        time = end - start;
        System.out.println("Time: "+ time + " ms");
        System.out.println("All visited nodes: " + all_nodes);
        System.out.println("All backtracks: " + all_backtracks);
        System.out.println("-----------------------------------\n");
    }

    public boolean solve(Sudoku sudoku){
        Sudoku copy = new Sudoku(sudoku);

        int row = -1;
        int col = -1;
        boolean isEmpty = true;

        // variable heuristic
        int[] nextVar = variableHeuristic.selectVariable(sudoku, row, col);
        row = nextVar[0];
        col = nextVar[1];

        // we still have some remaining missing values in Sudoku
        if(row != -1 && col != -1)
            isEmpty = false;

        // no empty space left
        if (isEmpty) {
            solutions.add(new Sudoku(sudoku));
            if(solutions.size() == 1) {
                time_to_first = System.currentTimeMillis() - start;
                backtracks_to_first = all_backtracks;
                nodes_to_first = all_nodes;
            }
//            sudoku.display();
            return true;
        }

        //value heuristic
        ArrayList<Character> values = valueHeuristic.selectValue(sudoku, row, col);

        for (Character val: values) {
            all_nodes++;
            int num = Character.getNumericValue(val);

                sudoku.getVariables()[row][col].value = Character.forDigit(num, 10);
                removeVal(sudoku,num, row, col);
                if(checkDomains()) {
                    solve(sudoku);
                    sudoku.getVariables()[row][col].value = '0';

                }

        }
        sudoku = new Sudoku(copy);
        all_backtracks ++;
        return false;
    }

    public void removeVal(Sudoku sudoku, int val, int row, int col){
        for(int i=0; i<Sudoku.SUDOKU_SIZE; i++){
            if(sudoku.getVariables()[i][col].value == '0')
                sudoku.getVariables()[i][col].domain.remove((Object) Character.forDigit(val, 10));
            if(sudoku.getVariables()[row][i].value == '0')
            sudoku.getVariables()[row][i].domain.remove((Object) Character.forDigit(val, 10));
        }

        int temp1 = row % 3;
        int temp2 = col % 3;
        for(int i=0; i<Sudoku.SQUARE_SIZE; i++){
            for(int j=0; j<Sudoku.SQUARE_SIZE; j++) {
                if(sudoku.getVariables()[row - temp1 + i][col - temp2 + j].value == '0')
                 sudoku.getVariables()[row - temp1 + i][col - temp2 + j].domain.remove((Object) Character.forDigit(val, 10));
            }
        }
    }

    public boolean checkDomains(){
        for(int i=0; i<Sudoku.SQUARE_SIZE; i++) {
            for (int j = 0; j < Sudoku.SQUARE_SIZE; j++) {
                if(sudoku.getVariables()[i][j].value != '0' && sudoku.getVariables()[i][j].domain.size() == 0)
                    return false;
            }
        }
        return true;
    }

}
