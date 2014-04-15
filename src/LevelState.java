import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class LevelState {
    private String moves;
    private HashSet<String> previousStates;

    private ArrayList<LevelState> possibleStates;
    private ArrayList<LevelState> completeStates;

    private Obstacle[][] grid;
    private int x;
    private int y;

    private int width;
    private int height;

    public LevelState() throws InvalidLevelException {
        moves = "";
        previousStates = new HashSet<String>();
        possibleStates = new ArrayList<LevelState>();
        completeStates = new ArrayList<LevelState>();
        parseLevelFile();
    }

    public LevelState(Obstacle[][] g, String soFar, HashSet<String> prev, int x, int y) {
        grid = g;
        moves = soFar;
        previousStates = prev;
        possibleStates = new ArrayList<LevelState>();
        completeStates = new ArrayList<LevelState>();
        this.x = x;
        this.y = y;

        width = grid.length;
        height = grid[0].length;
    }

    public int getMoveCount() {
        return moves.length();
    }

    public String getMoves() {
        return moves;
    }

    public void checkUp() {
        int yTemp = y;
        Obstacle barrier = null;

        while (yTemp > 0) {
            if (Obstacle.isBlocked(Direction.UP, grid[x][yTemp-1])) {
                barrier = grid[x][yTemp-1];
                break;
            }
            else {
                yTemp--;
            }
        }

        if (yTemp != y && barrier != null) {
            if (barrier == Obstacle.BREAKABLE) {
                Obstacle[][] o = makeGridCopy();
                o[x][y] = Obstacle.EMPTY;
                o[x][yTemp] = Obstacle.PLAYER;
                o[x][yTemp-1] = Obstacle.EMPTY;
                String s = moves + "U";
                HashSet<String> p = new HashSet<String>(previousStates);
                p.add(getStateCode());
                LevelState newState = new LevelState(o, s, p, x, yTemp);
                possibleStates.add(newState);
            }
            else {
                Obstacle[][] o = makeGridCopy();
                o[x][y] = Obstacle.EMPTY;
                o[x][yTemp] = Obstacle.PLAYER;
                String s = moves + "U";
                HashSet<String> p = new HashSet<String>(previousStates);
                p.add(getStateCode());
                LevelState newState = new LevelState(o, s, p, x, yTemp);

                if (!previousStates.contains(newState.getStateCode())) {
                    if (barrier == Obstacle.FINISH) {
                        completeStates.add(newState);
                    }
                    else {
                        possibleStates.add(newState);
                    }
                }
            }
        }
    }

    public void checkDown() {
        int yTemp = y;
        Obstacle barrier = null;

        while (yTemp < height - 1) {
            if (Obstacle.isBlocked(Direction.DOWN, grid[x][yTemp+1])) {
                barrier = grid[x][yTemp+1];
                break;
            }
            else {
                yTemp++;
            }
        }

        if (yTemp != y && barrier != null) {
            if (barrier == Obstacle.BREAKABLE) {
                Obstacle[][] o = makeGridCopy();
                o[x][y] = Obstacle.EMPTY;
                o[x][yTemp] = Obstacle.PLAYER;
                o[x][yTemp+1] = Obstacle.EMPTY;
                String s = moves + "D";
                HashSet<String> p = new HashSet<String>(previousStates);
                p.add(getStateCode());
                LevelState newState = new LevelState(o, s, p, x, yTemp);
                possibleStates.add(newState);
            }
            else {
                Obstacle[][] o = makeGridCopy();
                o[x][y] = Obstacle.EMPTY;
                o[x][yTemp] = Obstacle.PLAYER;
                String s = moves + "D";
                HashSet<String> p = new HashSet<String>(previousStates);
                p.add(getStateCode());
                LevelState newState = new LevelState(o, s, p, x, yTemp);

                if (!previousStates.contains(newState.getStateCode())) {
                    if (barrier == Obstacle.FINISH) {
                        completeStates.add(newState);
                    }
                    else {
                        possibleStates.add(newState);
                    }
                }
            }
        }
    }

    public void checkLeft() {
        int xTemp = x;
        Obstacle barrier = null;

        while (xTemp > 0) {
            if (Obstacle.isBlocked(Direction.LEFT, grid[xTemp-1][y])) {
                barrier = grid[xTemp-1][y];
                break;
            }
            else {
                xTemp--;
            }
        }

        if (xTemp != x && barrier != null) {
            if (barrier == Obstacle.BREAKABLE) {
                Obstacle[][] o = makeGridCopy();
                o[x][y] = Obstacle.EMPTY;
                o[xTemp][y] = Obstacle.PLAYER;
                o[xTemp-1][y] = Obstacle.EMPTY;
                String s = moves + "L";
                HashSet<String> p = new HashSet<String>(previousStates);
                p.add(getStateCode());
                LevelState newState = new LevelState(o, s, p, xTemp, y);
                possibleStates.add(newState);
            }
            else {
                Obstacle[][] o = makeGridCopy();
                o[x][y] = Obstacle.EMPTY;
                o[xTemp][y] = Obstacle.PLAYER;
                String s = moves + "L";
                HashSet<String> p = new HashSet<String>(previousStates);
                p.add(getStateCode());
                LevelState newState = new LevelState(o, s, p, xTemp, y);

                if (!previousStates.contains(newState.getStateCode())) {
                    if (barrier == Obstacle.FINISH) {
                        completeStates.add(newState);
                    }
                    else {
                        possibleStates.add(newState);
                    }
                }
            }
        }
    }

    public void checkRight() {
        int xTemp = x;
        Obstacle barrier = null;

        while (xTemp < width - 1) {
            if (Obstacle.isBlocked(Direction.RIGHT, grid[xTemp+1][y])) {
                barrier = grid[xTemp+1][y];
                break;
            }
            else {
                xTemp++;
            }
        }

        if (xTemp != x && barrier != null) {
            if (barrier == Obstacle.BREAKABLE) {
                Obstacle[][] o = makeGridCopy();
                o[x][y] = Obstacle.EMPTY;
                o[xTemp][y] = Obstacle.PLAYER;
                o[xTemp+1][y] = Obstacle.EMPTY;
                String s = moves + "R";
                HashSet<String> p = new HashSet<String>(previousStates);
                p.add(getStateCode());
                LevelState newState = new LevelState(o, s, p, xTemp, y);
                possibleStates.add(newState);
            }
            else {
                Obstacle[][] o = makeGridCopy();
                o[x][y] = Obstacle.EMPTY;
                o[xTemp][y] = Obstacle.PLAYER;
                String s = moves + "R";
                HashSet<String> p = new HashSet<String>(previousStates);
                p.add(getStateCode());
                LevelState newState = new LevelState(o, s, p, xTemp, y);

                if (!previousStates.contains(newState.getStateCode())) {
                    if (barrier == Obstacle.FINISH) {
                        completeStates.add(newState);
                    }
                    else {
                        possibleStates.add(newState);
                    }
                }
            }
        }
    }

    public void calculateStates() {
        possibleStates = new ArrayList<LevelState>();
        completeStates = new ArrayList<LevelState>();

        checkUp();
        checkDown();
        checkLeft();
        checkRight();
    }

    public ArrayList<LevelState> getPossibleStates() {
        return possibleStates;
    }

    public ArrayList<LevelState> getCompleteStates() {
        return completeStates;
    }

    private String getStateCode() {
        String res = "";

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                res += grid[i][j].ordinal();
            }
        }

        return res;
    }

    private Obstacle[][] makeGridCopy() {
        Obstacle[][] gCopy = new Obstacle[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                gCopy[i][j] = grid[i][j];
            }
        }

        return gCopy;
    }

    private void parseLevelFile() throws InvalidLevelException {
        Scanner sc = new Scanner(System.in);
        boolean levelFound = false;

        int width = 0;
        int height = 0;

        //find opening Level tag
        while (sc.hasNext()) {
            String s = sc.next();
            if (s.equals("<level")) {
                if (sc.hasNext()) {
                    String w = sc.next();
                    Scanner sc1 = new Scanner(w);
                    if (sc1.hasNextInt()) {
                        width = sc1.nextInt();
                    }
                    else {
                        throw new InvalidLevelException();
                    }
                }
                else {
                    throw new InvalidLevelException();
                }
                if (sc.hasNext()) {
                    String h = sc.next();
                }
                else {
                    throw new InvalidLevelException();
                }

                levelFound = true;
                break;
            }
        }

        //Initialize game board
        grid = new Obstacle[width][height];

        width = grid.length;
        height = grid[0].length;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = Obstacle.EMPTY;
            }
        }
//        x = 2;
//        y = 1;

        //Parse the rest of the file and fill in game board
        if (!levelFound) {
            throw new InvalidLevelException();
        }
        else {
            System.out.println(sc.next());
            System.out.println(sc.next());
        }
    }

//    private Obstacle[][] parseLevelFile(String fileName) {
//        Obstacle[][] g = new Obstacle[8][11];
//
//        width = g.length;
//        height = g[0].length;
//
//        for (int i = 0; i < width; i++) {
//            for (int j = 0; j < height; j++) {
//                g[i][j] = Obstacle.EMPTY;
//            }
//        }
//
//        g[5][0] = Obstacle.BLOCK;
//        g[0][1] = Obstacle.BLOCK;
//        g[7][1] = Obstacle.BLOCK;
//        g[3][2] = Obstacle.BLOCK;
//        g[6][5] = Obstacle.BLOCK;
//        g[7][6] = Obstacle.BLOCK;
//        g[1][7] = Obstacle.BLOCK;
//        g[7][9] = Obstacle.BLOCK;
//        g[2][10] = Obstacle.BLOCK;
//
//        g[5][3] = Obstacle.PIPE_VERT;
//        g[3][9] = Obstacle.PIPE_HOR;
//        g[6][8] = Obstacle.PIPE_VERT;
//
//        g[2][4] = Obstacle.RAMP_TOP;
//        g[4][1] = Obstacle.RAMP_RIGHT;
//        g[3][7] = Obstacle.RAMP_TOP;
//
//        g[3][5] = Obstacle.BREAKABLE;
//        g[4][4] = Obstacle.BREAKABLE;
//        g[5][5] = Obstacle.BREAKABLE;
//        g[4][6] = Obstacle.BREAKABLE;
//
//        g[2][1] = Obstacle.PLAYER;
//        g[4][5] = Obstacle.FINISH;
//
//        x = 2;
//        y = 1;
//
//        return g;
//    }
}
