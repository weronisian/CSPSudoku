public class CSP {

    public static void main(String[] args) {

        int num_of_sudoku = 43;     // 1 - 46
        int num_of_jolka = 1;       // 0 - 4


        Sudoku sudoku = (Sudoku) new Loader("sudoku", num_of_sudoku).read();
        sudoku.display();

//        new SolveSudokuBacktracking(sudoku, new SelectNextVariable(), new SelectNextValue());
        new SolveSudokuBacktracking(sudoku, new SelectNextVariable(), new SelectMostInDomainsValue());
//        new SolveSudokuBacktracking(sudoku, new SelectMostLimitedVariable(), new SelectNextValue());
//        new SolveSudokuBacktracking(sudoku, new SelectMostLimitedVariable(), new SelectMostInDomainsValue());

//        new SolveSudokuBacktracking(sudoku, new SelectInSquareVariable(), new SelectNextValue());
        new SolveSudokuBacktracking(sudoku, new SelectInSquareVariable(), new SelectMostInDomainsValue());
//        new SolveSudokuBacktracking(sudoku, new SelectInSquareVariable(), new SelectMostInDomainsValue());


//        new SolveSudokuForward(sudoku, new SelectNextVariable(), new SelectNextValue());
        new SolveSudokuForward(sudoku, new SelectNextVariable(), new SelectMostInDomainsValue());
//        new SolveSudokuForward(sudoku, new SelectMostLimitedVariable(), new SelectNextValue());
//        new SolveSudokuForward(sudoku, new SelectMostLimitedVariable(), new SelectMostInDomainsValue());

        new SolveSudokuForward(sudoku, new SelectInSquareVariable(), new SelectMostInDomainsValue());



//        Crossword crossword = (Crossword) new Loader("jolka", num_of_jolka).read();
//        crossword.display();

//        new SolveCrossword(crossword);

    }
}
