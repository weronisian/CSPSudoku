import java.util.ArrayList;

public class SelectNextValue implements ValueHeuristic {

    // value selection heuristic

    @Override
    public ArrayList<Character> selectValue(Sudoku sudoku, int row, int col){
        ArrayList<Character> temp = new ArrayList<>();
//        for(int k=0; k<Sudoku.SUDOKU_SIZE; k++){
//            temp.add(Character.forDigit(k+1, 10));
//        }
        for(int a=0; a<sudoku.getVariables()[row][col].getDomainSize(); a++) {
            temp.add(sudoku.getVariables()[row][col].domain.get(a));
        }
        return temp;
    }
}
