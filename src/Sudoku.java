import java.util.Arrays;

public class Sudoku {
    public static int SUDOKU_SIZE = 9;
    public static int SQUARE_SIZE = 3;
    private Variable variables [][];
    private int nr;
    private int difficulty;

    public Sudoku(){
    }

//    public Sudoku(int nr, int difficulty, String var){
//        this.nr = nr;
//        this.difficulty = difficulty;
//        this.variables = new Variable[SolveSudokuBacktracking.SUDOKU_SIZE][SolveSudokuBacktracking.SUDOKU_SIZE];
//        char varArray[] = var.toCharArray();
//        for(int i=0; i<variables.length; i++) {
//            for (int j = 0; j < variables[0].length; j++) {
//                if (varArray[i * SolveSudokuBacktracking.SUDOKU_SIZE + j] == '.')
//                    variables[i][j] = new Variable('0');
//                else
//                    variables[i][j] = new Variable(varArray[i * SolveSudokuBacktracking.SUDOKU_SIZE + j]);
//            }
//        }
//    }

    public Sudoku(Sudoku sudoku){
        this.nr = sudoku.getNr();
        this.difficulty = sudoku.getDifficulty();
        this.variables = new Variable[SUDOKU_SIZE][SUDOKU_SIZE];
        for(int i=0; i<variables.length; i++) {
            for (int j = 0; j < variables[0].length; j++) {
                variables[i][j] = new Variable(sudoku.getVariables()[i][j]);
            }
        }
    }

    public void display(){
        System.out.println("Sudoku nr " + nr);
        System.out.println("difficulty: "+ difficulty);
        for(int i=0; i<variables.length; i++){
            for(int j=0; j<variables[0].length; j++) {
                System.out.print(variables[i][j].toString() + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public String toString() {
        String str = "Sudoku{" + "variables=";
        for(int i=0; i<variables.length; i++){
            str += Arrays.toString(variables[i]);
        }
        str +=    ", difficulty=" + difficulty + '}';
        return str;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public Variable[][] getVariables() {
        return variables;
    }

    public void setVariables(String var) {
        this.variables = new Variable[SUDOKU_SIZE][SUDOKU_SIZE];
        char varArray[] = var.toCharArray();
        for(int i=0; i<variables.length; i++) {
            for (int j = 0; j < variables[0].length; j++) {
                if (varArray[i * SUDOKU_SIZE + j] == '.') {
                    variables[i][j] = new Variable('0');
                    for(int k=0; k<SUDOKU_SIZE; k++){
                        variables[i][j].domain.add(Character.forDigit(k+1, 10));
                    }
                }
                else {
                    variables[i][j] = new Variable(varArray[i * SUDOKU_SIZE + j]);
                    variables[i][j].domain.add(varArray[i * SUDOKU_SIZE + j]);
                }
            }
        }
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    //predicates: can't be the same value in column and row and little square
    public boolean checkLine(int var, int num_of_row, int num_of_col){
        boolean isOkay = true;

        for(int i=0; i<SUDOKU_SIZE; i++){
            if (getVariables()[i][num_of_col].value == Character.forDigit(var, 10)) {
                isOkay = false;
                break;
            } else if (getVariables()[num_of_row][i].value == Character.forDigit(var, 10)) {
                isOkay = false;
                break;
            }
        }
        return isOkay;
    }

    public boolean checkSquare(int var, int num_of_row, int num_of_col){
        boolean isOkay = true;
        int temp1 = num_of_row % 3;
        int temp2 = num_of_col % 3;

        for(int i=0; i<SQUARE_SIZE; i++){
            for(int j=0; j<SQUARE_SIZE; j++) {
                if (getVariables()[num_of_row - temp1 + i][num_of_col - temp2 + j] .value == Character.forDigit(var, 10)) {
                    isOkay = false;
                }
            }
            if(!isOkay)
                break;
        }
        return isOkay;
    }

    // specify domain for variable
    public void findDomain(Variable variable, int row, int col){
        variable.domain.clear();
        for (int n=1; n <= SUDOKU_SIZE; n++){
            if(checkLine(n, row, col) && checkSquare(n, row, col)){
                variable.domain.add(Character.forDigit(n, 10));
            }
        }
    }
}