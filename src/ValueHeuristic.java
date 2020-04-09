import java.util.ArrayList;

public interface ValueHeuristic {
    public ArrayList<Character> selectValue(Sudoku sudoku, int row, int col);
}
