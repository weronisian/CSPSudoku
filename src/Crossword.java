import java.util.Set;

public class Crossword {
    private int nr;
    private Variable board [][];
    private Set<String> words;

    public Crossword(int nr){
        this.nr = nr;
    }

    public Crossword(Crossword crossword){
        this.nr = crossword.getNr();
        this.board = new Variable[crossword.getBoard().length][crossword.getBoard()[0].length];
        for(int i=0; i<board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Variable(crossword.getBoard()[i][j]);
            }
        }
    }

    public void displayBoard(){
        System.out.println("Board:\n-------");
        System.out.print(this.toString());
        System.out.println("-------");
    }

    public void display(){
        System.out.println("Jolka nr " + nr);
        System.out.println("Board:\n-------------");
        System.out.print(this.toString());
        System.out.println("--------------");
        System.out.println("Words:\n" + words);
        System.out.println();
    }


    @Override
    public String toString() {
        String str = "";
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[0].length; j++) {
                str += board[i][j] + " ";
            }
            str += "\n";
        }
        return str;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public Variable[][] getBoard() {
        return board;
    }

    public void setBoard(Variable[][] board) {
        this.board = board;
    }

    public Set<String> getWords() {
        return words;
    }

    public void setWords(Set<String> words) {
        this.words = words;
    }
}
