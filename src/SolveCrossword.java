import java.awt.Point;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class SolveCrossword {
    private int height, width;
    private Crossword crossword;
    private Variable[][] board;
    private Set<String> words;
    private long start, end, time_to_first, time;
    private int nodes_to_first = 0, all_nodes = 0;
    private int backtracks_to_first = 0, all_backtracks = 0;
    private ArrayList<Crossword> solutions;
    private Map<Point, Integer> vertical = new HashMap<>(), horizontal = new HashMap<>();     // start point of slots and word size on the board

    public SolveCrossword(Crossword crossword) {
        this.height = crossword.getBoard().length;
        this.width = crossword.getBoard()[0].length;
        this.crossword = crossword;
        this.board = crossword.getBoard();
        this.words = crossword.getWords();
        this.solutions = new ArrayList<Crossword>();
        findSlots();

        start = System.currentTimeMillis();
        solveHorizontal();
        end = System.currentTimeMillis();

        if (!solutions.isEmpty()) {
            System.out.println("First solution:");
            solutions.get(0).displayBoard();
//            solutions.get(1).displayBoard();

            System.out.println();
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

    private char get (int x, int y) {
        return board[y][x].value;
    }

    private void set(int x, int y, char character) {
        board[y][x].value = character;
    }

    private boolean isSpace (int x, int y) {
        return get(x, y) == '_';
    }

    // Fit all horizontal slots, when success move to solve vertical.
    private boolean solveHorizontal () {
        return solve( horizontal, this::fitHorizontal, this::solveVertical );
    }
    // Fit all vertical slots, report success when done
    private boolean solveVertical () {
        return solve( vertical, this::fitVertical, () -> true );
    }

    // Recur each slot, try every word in a loop.  When all slots of this kind are filled successfully, run next stage.
    //    Interface BiFunction<T,U,R>             Supplier - function interface (void, T)
    private boolean solve ( Map<Point, Integer> slot, BiFunction<Point, String, Boolean> tryFit, Supplier<Boolean> nextSolveFunction ) {
        // if there is no slot, apply next function
        if (slot.isEmpty())
            return nextSolveFunction.get();

        // variable selection heuristic
        // take next slot (vertical/horizontal)
        Point pos = slot.keySet().iterator().next();
        int slotSize = slot.remove(pos);

        // copy start board
        Variable[][] state = new Variable[height][width];
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                state[i][j] = new Variable(board[i][j]);
            }
        }

        // value selection heuristic
        // take next word and try fit to slot (domain = words)
        for (String word : words) {
            if(word.length() != slotSize)
                continue;
            // if predicates are true do recursion on next solve function
            if(tryFit.apply(pos, word) && solve(slot, tryFit, nextSolveFunction)) {
                // save first solution
                crossword.setBoard(state);
                solutions.add(new Crossword(crossword));
                if(solutions.size() == 1) {
                    time_to_first = System.currentTimeMillis() - start;
                    backtracks_to_first = all_backtracks;
                    nodes_to_first = all_nodes;
                }
//                return true;
            }
            // word doesn't match, so restore board and try next word
//            board = new Variable[height][width];
            for(int i=0; i<height; i++){
                for(int j=0; j<width; j++){
                    board[i][j] = new Variable(state[i][j]);
                }
            }
        }
        // restore slot and return false
        slot.put(pos, slotSize);
        all_backtracks++;
        return false;
    }

    // predicates
    // Try fit a word to a slot. Return false if there is a conflict
    private boolean fitHorizontal (Point pos, String word) {
        all_nodes++;
        final int x = pos.x, y = pos.y;
        for (int i = 0; i < word.length(); i++) {
            if (!isSpace( x+i, y) && get(x+i, y) != word.charAt(i)) {
                return false;
            }
            set(x+i, y, word.charAt(i));
        }
        return true;
    }

    private boolean fitVertical (Point pos, String word) {
        all_nodes++;
        final int x = pos.x, y = pos.y;
        for (int i = 0 ; i < word.length(); i++ ) {
            if (! isSpace(x, y+i ) && get(x, y+i) != word.charAt(i) ) {
                return false;
            }
            set(x, y+i, word.charAt(i) );
        }
        return true;
    }

    // find horizontal and vertical slots
    private void findSlots(){
        for (int y = 0, size ; y < height ; y++) {
            for (int x = 0; x < width - 1; x++) {
                if (isSpace(x, y) && isSpace(x + 1, y)) {
                    // find slot size
                    for (size = 2; x + size < width && isSpace(x + size, y); size++) ;
                    horizontal.put(new Point(x, y), size);
                    x += size; // Skip past this horizontal slot
                }
            }
        }
        for (int x = 0, size ; x < width ; x++) {
            for (int y = 0; y < height - 1; y++) {
                if (isSpace(x, y) && isSpace(x, y + 1)) {
                    for (size = 2; y + size < height && isSpace(x, y + size); size++) ;
                    vertical.put(new Point(x, y), size);
                    y += size;
                }
            }
        }
    }
}