import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class Loader {
    public String type;
    private File file_puzzle, file_words;
    private int nr_of_puzzle;

    public Loader(String type, int num_of_puzzle) {
        this.type = type;
        this.nr_of_puzzle = num_of_puzzle;
        if(type.equals("sudoku")){
            this.file_puzzle = new File("Sudoku.csv");
//            readSudoku(num_of_puzzle);
        } else if(type.equals("jolka")){
            this.file_puzzle = new File("Jolka/puzzle" + num_of_puzzle);
            this.file_words = new File("Jolka/words" + num_of_puzzle);
//            readJolka(num_of_puzzle);
        }
    }

    public Object read(){
        Object o = new Object();
        if(this.type.equals("sudoku"))
            o = readSudoku(this.nr_of_puzzle);
        else if(this.type.equals("jolka"))
            o = readJolka(this.nr_of_puzzle);
        return o;
    }

    public Sudoku readSudoku(int num_of_sudoku) {
        Sudoku sudoku = new Sudoku();

        try {
            BufferedReader reading = new BufferedReader(new FileReader(file_puzzle));

            String line;
            reading.readLine();
            while((line = reading.readLine()) != null) {
                String row[] = line.split(";");

                if(Integer.parseInt(row[0]) == num_of_sudoku) {
                    sudoku.setNr(Integer.parseInt(row[0]));
                    sudoku.setDifficulty((int)Double.parseDouble(row[1]));
                    sudoku.setVariables(row[2]);
                }

            }
            reading.close();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return sudoku;
    }

    public Crossword readJolka(int num_of_jolka) {
        Crossword crossword = new Crossword(num_of_jolka);
        Set<String> words = new HashSet<>();
        Variable board [][];

        // reading board
        try {
            BufferedReader reading = new BufferedReader(new FileReader(file_puzzle));

            long lineCount = Files.lines(Paths.get("Jolka/puzzle" + num_of_jolka)).count();

            String line, str = "";
            int lineSize = 0;
            while((line = reading.readLine()) != null) {
                lineSize = line.length();
                str += line;

                // wyswietlanie pliku
//                System.out.println(line);
            }
            reading.close();
            char row [] = str.toCharArray();
            board = new Variable[(int)lineCount][lineSize];

            for(int i=0; i<board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    board[i][j] = new Variable(row[i * lineSize + j]);
                }
            }
            crossword.setBoard(board);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        //reading words
        try {
            BufferedReader reading = new BufferedReader(new FileReader(file_words));
            String line;
            while((line = reading.readLine()) != null) {
                words.add(line);
            }
            reading.close();
            crossword.setWords(words);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return crossword;
    }

}
