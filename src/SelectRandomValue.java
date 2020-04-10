import java.util.ArrayList;
import java.util.Random;

public class SelectRandomValue implements ValueHeuristic {
    @Override
    public ArrayList<Character> selectValue(Sudoku sudoku, int row, int col) {
        ArrayList<Character> temp = new ArrayList<>();

        Random random = new Random();
        int domainSize = sudoku.getVariables()[row][col].getDomainSize();

        for(int i=0; i<domainSize; i++){
            temp.add('0');
        }
        for(int a=0; a<domainSize; a++) {
            int rand = random.nextInt(domainSize);
            while(temp.get(rand) != '0') {
                rand = random.nextInt(domainSize);
            }
            temp.set(rand, sudoku.getVariables()[row][col].domain.get(a));
        }

//        for(int a=0; a<temp.size(); a++) {
//            System.out.print(temp.get(a) + ", ");
//        }
//        System.out.println();
        return temp;
    }
}
