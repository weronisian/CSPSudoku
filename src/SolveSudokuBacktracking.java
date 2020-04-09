import java.util.ArrayList;

public class SolveSudokuBacktracking {

    private Sudoku sudoku;
    private long start, end, time_to_first, time;
    private int nodes_to_first = 0, all_nodes = 0;
    private int backtracks_to_first = 0, all_backtracks = 0;
    private ArrayList<Sudoku> solutions;
    private VariableHeuristic variableHeuristic;
    private ValueHeuristic valueHeuristic;

    public SolveSudokuBacktracking(Sudoku sudoku, VariableHeuristic varH, ValueHeuristic valH){
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

    public ArrayList<Character> giveArray(int row, int col){
        ArrayList<Character> temp = new ArrayList<>();
        for(int a=0; a<sudoku.getVariables()[row][col].getDomainSize(); a++) {
//            System.out.print(sudoku.getVariables()[row][col].domain.get(a) + ", ");
            temp.add(sudoku.getVariables()[row][col].domain.get(a));
        }
        return temp;
    }

    public boolean solve(Sudoku sudoku) {
        int row = -1;
        int col = -1;
        boolean isEmpty = true;

        // variable selection heuristic
        // check next variable is empty
        int nextVar[] = variableHeuristic.selectVariable(sudoku, row, col);
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

        // value selection heuristic
        ArrayList<Character> values = valueHeuristic.selectValue(sudoku,row, col);

//        ArrayList<Character> values = giveArray(row, col);
        for (Character val: values) {
            all_nodes++;
            int num = Character.getNumericValue(val);
            //check all predicates are true
            if (sudoku.checkLine(num, row, col) && sudoku.checkSquare(num, row, col)) {
                sudoku.getVariables()[row][col].value = Character.forDigit(num, 10);
                solve(sudoku);
                sudoku.getVariables()[row][col].value = '0';
            }
        }

//        for (int num = 1; num <= Sudoku.SUDOKU_SIZE; num++) {
//            all_nodes++;
//            //check all predicates are true
//            if (sudoku.checkLine(num, row, col) && sudoku.checkSquare(num, row, col)) {
//                sudoku.getVariables()[row][col].value = Character.forDigit(num, 10);
////                if (solve(sudoku)) {
////                    sudoku.getVariables()[row][col].value = '0';
////                    return true;
////                }
////                else {
////                    sudoku.getVariables()[row][col].value = '0';
////                }
//                solve(sudoku);
//                sudoku.getVariables()[row][col].value = '0';
//            }
//        }
        all_backtracks ++;
        return false;
    }

}
