import java.util.ArrayList;

public class SelectMostInDomainsValue implements ValueHeuristic {

    @Override
    public ArrayList<Character> selectValue(Sudoku sudoku, int row, int col){
        int[] count = new int[Sudoku.SUDOKU_SIZE];
        ArrayList<Character> temp = new ArrayList<>();
        for(int a=0; a<sudoku.getVariables()[row][col].getDomainSize(); a++) {
            temp.add(sudoku.getVariables()[row][col].domain.get(a));
        }

        if(temp.size() == 0 || temp.size() == 1)
            return temp;

        //count which value is in the most domains
        for (Character character : temp) {
            for (int i = 0; i < sudoku.getVariables().length; i++) {
                for (int j = 0; j < sudoku.getVariables()[0].length; j++) {
                    if (sudoku.getVariables()[i][j].domain.contains(character))
                        count[Character.getNumericValue(character) - 1] = count[Character.getNumericValue(character) - 1] + 1;
                }
            }
        }
//        System.out.print("count: ");
//        for(int a=0; a<9; a++) {
//            System.out.print(count[a] + ", ");
//        }
//        System.out.println();


        int maxCount = 0, maxVal = 0;
        for (int k=0; k<count.length; k++ ) {
            if(maxCount < count[k] && count[k] > 0){
                maxCount = count[k];
                maxVal = k+1;
                temp.remove((Object)Character.forDigit(maxVal, 10));
                temp.add(0, Character.forDigit(maxVal, 10));
            }
        }

//        for(int a=0; a<temp.size(); a++) {
//            System.out.print(temp.get(a) + ", ");
//        }
//        System.out.println();

        return temp;
    }

}
