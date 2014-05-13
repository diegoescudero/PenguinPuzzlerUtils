import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LevelState {
    private String moves;
    private HashSet<String> previousStates;

    private ArrayList<LevelState> possibleStates;
    private ArrayList<LevelState> completeStates;

    private Obstacle[][] grid;
    private int playerX;
    private int playerY;

    private int gridWidth;
    private int gridHeight;

    public LevelState() throws InvalidLevelException {
        moves = "";
        previousStates = new HashSet<String>();
        possibleStates = new ArrayList<LevelState>();
        completeStates = new ArrayList<LevelState>();
        parseLevelFile();
    }

    public LevelState(Obstacle[][] g, String soFar, HashSet<String> prev, int playerX, int playerY) {
        grid = g;
        moves = soFar;
        previousStates = prev;
        possibleStates = new ArrayList<LevelState>();
        completeStates = new ArrayList<LevelState>();
        this.playerX = playerX;
        this.playerY = playerY;

        //Assumes grid more than 0x0
        gridWidth = grid.length;
        gridHeight = grid[0].length;
    }

    public int getMoveCount() {
        return moves.length();
    }

    public String getMoves() {
        return moves;
    }

    public void checkUp() {
        int yTemp = playerY;
        Obstacle barrier = null;

        while (yTemp > 0) {
            if (Obstacle.isBlocked(Direction.UP, grid[playerX][yTemp-1])) {
                barrier = grid[playerX][yTemp-1];
                break;
            }
            else {
                yTemp--;
            }
        }

        if (yTemp != playerY && barrier != null) {
            if (barrier == Obstacle.BREAKABLE) {
                Obstacle[][] o = makeGridCopy();
                o[playerX][playerY] = Obstacle.EMPTY;
                o[playerX][yTemp] = Obstacle.START;
                o[playerX][yTemp-1] = Obstacle.EMPTY;
                String s = moves + "U";
                HashSet<String> p = new HashSet<String>(previousStates);
                p.add(getStateCode());
                LevelState newState = new LevelState(o, s, p, playerX, yTemp);
                possibleStates.add(newState);
            }
            else {
                Obstacle[][] o = makeGridCopy();
                o[playerX][playerY] = Obstacle.EMPTY;
                o[playerX][yTemp] = Obstacle.START;
                String s = moves + "U";
                HashSet<String> p = new HashSet<String>(previousStates);
                p.add(getStateCode());
                LevelState newState = new LevelState(o, s, p, playerX, yTemp);

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
        int yTemp = playerY;
        Obstacle barrier = null;

        while (yTemp < gridHeight - 1) {
            if (Obstacle.isBlocked(Direction.DOWN, grid[playerX][yTemp+1])) {
                barrier = grid[playerX][yTemp+1];
                break;
            }
            else {
                yTemp++;
            }
        }

        if (yTemp != playerY && barrier != null) {
            if (barrier == Obstacle.BREAKABLE) {
                Obstacle[][] o = makeGridCopy();
                o[playerX][playerY] = Obstacle.EMPTY;
                o[playerX][yTemp] = Obstacle.START;
                o[playerX][yTemp+1] = Obstacle.EMPTY;
                String s = moves + "D";
                HashSet<String> p = new HashSet<String>(previousStates);
                p.add(getStateCode());
                LevelState newState = new LevelState(o, s, p, playerX, yTemp);
                possibleStates.add(newState);
            }
            else {
                Obstacle[][] o = makeGridCopy();
                o[playerX][playerY] = Obstacle.EMPTY;
                o[playerX][yTemp] = Obstacle.START;
                String s = moves + "D";
                HashSet<String> p = new HashSet<String>(previousStates);
                p.add(getStateCode());
                LevelState newState = new LevelState(o, s, p, playerX, yTemp);

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
        int xTemp = playerX;
        Obstacle barrier = null;

        while (xTemp > 0) {
            if (Obstacle.isBlocked(Direction.LEFT, grid[xTemp-1][playerY])) {
                barrier = grid[xTemp-1][playerY];
                break;
            }
            else {
                xTemp--;
            }
        }

        if (xTemp != playerX && barrier != null) {
            if (barrier == Obstacle.BREAKABLE) {
                Obstacle[][] o = makeGridCopy();
                o[playerX][playerY] = Obstacle.EMPTY;
                o[xTemp][playerY] = Obstacle.START;
                o[xTemp-1][playerY] = Obstacle.EMPTY;
                String s = moves + "L";
                HashSet<String> p = new HashSet<String>(previousStates);
                p.add(getStateCode());
                LevelState newState = new LevelState(o, s, p, xTemp, playerY);
                possibleStates.add(newState);
            }
            else {
                Obstacle[][] o = makeGridCopy();
                o[playerX][playerY] = Obstacle.EMPTY;
                o[xTemp][playerY] = Obstacle.START;
                String s = moves + "L";
                HashSet<String> p = new HashSet<String>(previousStates);
                p.add(getStateCode());
                LevelState newState = new LevelState(o, s, p, xTemp, playerY);

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
        int xTemp = playerX;
        Obstacle barrier = null;

        while (xTemp < gridWidth - 1) {
            if (Obstacle.isBlocked(Direction.RIGHT, grid[xTemp+1][playerY])) {
                barrier = grid[xTemp+1][playerY];
                break;
            }
            else {
                xTemp++;
            }
        }

        if (xTemp != playerX && barrier != null) {
            if (barrier == Obstacle.BREAKABLE) {
                Obstacle[][] o = makeGridCopy();
                o[playerX][playerY] = Obstacle.EMPTY;
                o[xTemp][playerY] = Obstacle.START;
                o[xTemp+1][playerY] = Obstacle.EMPTY;
                String s = moves + "R";
                HashSet<String> p = new HashSet<String>(previousStates);
                p.add(getStateCode());
                LevelState newState = new LevelState(o, s, p, xTemp, playerY);
                possibleStates.add(newState);
            }
            else {
                Obstacle[][] o = makeGridCopy();
                o[playerX][playerY] = Obstacle.EMPTY;
                o[xTemp][playerY] = Obstacle.START;
                String s = moves + "R";
                HashSet<String> p = new HashSet<String>(previousStates);
                p.add(getStateCode());
                LevelState newState = new LevelState(o, s, p, xTemp, playerY);

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
        //Reset states so no double counting if called > 1 times
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

        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                res += grid[i][j].ordinal();
            }
        }

        return res;
    }

    private Obstacle[][] makeGridCopy() {
        Obstacle[][] gCopy = new Obstacle[gridWidth][gridHeight];

        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                gCopy[i][j] = grid[i][j];
            }
        }

        return gCopy;
    }

    private void parseLevelFile() throws InvalidLevelException {
//        FileInputStream stream = null;
//
//        try {
//            stream = new FileInputStream("src/level16.xml");
//        }
//        catch (FileNotFoundException e) {
//            //nothing
//        }

//        Scanner sc = new Scanner(stream);

        Scanner sc = new Scanner(System.in);

        boolean levelFound = false;
        //Find opening Level tag
        while (sc.hasNext()) {
            String s = sc.next().toLowerCase();
            if (s.equals("<level")) {
                String sub1;
                String sub2;

                //Get width/height tags
                if (sc.hasNext()) {
                    sub1 = sc.next().toLowerCase();
                }
                else {
                    throw new InvalidLevelException();
                }
                if (sc.hasNext()) {
                    sub2 = sc.next().toLowerCase();
                }
                else {

                    throw new InvalidLevelException();
                }

                //Parse width/height tags
                String xString;
                String yString;
                if (sub1.contains("width") && sub2.contains("height") || sub2.contains("width") && sub1.contains("height")) {
                    if (sub1.contains("width")) {
                        xString = sub1;
                        yString = sub2;
                    }
                    else {
                        xString = sub2;
                        yString = sub1;
                    }
                }
                else {
                    System.out.println("here2a");
                    throw new InvalidLevelException();
                }
                if (sub1.matches(".*\\d.*") && sub2.matches(".*\\d.*")) {
                    Matcher m = Pattern.compile("\\d+").matcher(xString);
                    m.find();
                    gridWidth = Integer.valueOf(m.group());

                    m = Pattern.compile("\\d+").matcher(yString);
                    m.find();
                    gridHeight = Integer.valueOf(m.group());
                }
                else {
                    System.out.println("here2b");
                    throw new InvalidLevelException();
                }

                //Cleanup
                levelFound = true;
                break;
            }
        }

        //Stop if Level tag was not found
        if (!levelFound) {
            throw new InvalidLevelException();
        }

        //Initialize game board
        grid = new Obstacle[gridWidth][gridHeight];
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                grid[i][j] = Obstacle.EMPTY;
            }
        }

        //Parse the rest of the file and fill in game board
        while (sc.hasNext()) {
            String sw = sc.next().toLowerCase();

            if (sw.equals("<block")) {
                ArrayList<String> temp = parseSubs(sc, Obstacle.BLOCK);

                int x = Integer.parseInt(temp.get(0));
                int y = Integer.parseInt(temp.get(1));

                grid[x][y] = Obstacle.BLOCK;
            }
            else if (sw.equals("<pipe")) {
                ArrayList<String> temp = parseSubs(sc, Obstacle.PIPE);

                int x = Integer.parseInt(temp.get(0));
                int y = Integer.parseInt(temp.get(1));
                Direction d = Direction.getDirectionFromString(temp.get(2));
                if (d == Direction.NONE) {
                    throw new InvalidLevelException();
                }
                else {
                    Obstacle o = Obstacle.EMPTY;
                    if (d == Direction.UP || d == Direction.DOWN) {
                        o = Obstacle.PIPE_VERT;
                    }
                    else {
                        o = Obstacle.PIPE_HOR;
                    }

                    grid[x][y] = o;
                }
            }
            else if (sw.equals("<ramp")) {
                ArrayList<String> temp = parseSubs(sc, Obstacle.RAMP);

                int x = Integer.parseInt(temp.get(0));
                int y = Integer.parseInt(temp.get(1));
                Direction d = Direction.getDirectionFromString(temp.get(2));
                if (d == Direction.NONE) {
                    throw new InvalidLevelException();
                }
                else {
                    Obstacle o = Obstacle.EMPTY;
                    if (d == Direction.UP) {
                        o = Obstacle.RAMP_TOP;
                    }
                    else if (d == Direction.DOWN) {
                        o = Obstacle.RAMP_BOTTOM;
                    }
                    else if (d == Direction.LEFT) {
                        o = Obstacle.RAMP_LEFT;
                    }
                    else if (d == Direction.RIGHT) {
                        o = Obstacle.RAMP_RIGHT;
                    }

                    grid[x][y] = o;
                }
            }
            else if (sw.equals("<breakable")) {
                ArrayList<String> temp = parseSubs(sc, Obstacle.BREAKABLE);

                int x = Integer.parseInt(temp.get(0));
                int y = Integer.parseInt(temp.get(1));

                grid[x][y] = Obstacle.BREAKABLE;
            }
            else if (sw.equals("<start")) {
                ArrayList<String> temp = parseSubs(sc, Obstacle.START);

                int x = Integer.parseInt(temp.get(0));
                int y = Integer.parseInt(temp.get(1));

                playerX = x;
                playerY = y;
                grid[x][y] = Obstacle.START;
            }
            else if (sw.equals("<finish")) {
                ArrayList<String> temp = parseSubs(sc, Obstacle.FINISH);

                int x = Integer.parseInt(temp.get(0));
                int y = Integer.parseInt(temp.get(1));

                grid[x][y] = Obstacle.FINISH;
            }
        }
    }

    //TODO make it so that each var can appear in any order
    private ArrayList<String> parseSubs(Scanner sc, Obstacle o) throws InvalidLevelException{
        ArrayList<String> subs = new ArrayList<String>();

        String sub1 = null;
        String sub2 = null;
        String sub3 = null;
        String xString = null;
        String yString = null;
        String dir = null;
        int x, y;

        //Read in vars
        if (sc.hasNext()) {
            sub1 = sc.next().toLowerCase();
        }
        else {
            throw new InvalidLevelException();
        }
        if (sc.hasNext()) {
            sub2 = sc.next().toLowerCase();
        }
        else {
            throw new InvalidLevelException();
        }

        if (o == Obstacle.PIPE || o == Obstacle.RAMP) {
            if (sc.hasNext()) {
                sub3 = sc.next().toLowerCase();
            }
            else {
                throw new InvalidLevelException();
            }
        }

        //Get var values
        if (sub1.contains("x") && sub2.contains("y") || sub2.contains("x") && sub1.contains("y")) {
            if (sub1.contains("x")) {
                xString = sub1;
                yString = sub2;
            }
            else {
                xString = sub2;
                yString = sub1;
            }
        }
        else {
            throw new InvalidLevelException();
        }
        if (sub1.matches(".*\\d.*") && sub2.matches(".*\\d.*")) {
            Matcher m = Pattern.compile("\\d+").matcher(xString);
            m.find();
            x = Integer.valueOf(m.group());

            m = Pattern.compile("\\d+").matcher(yString);
            m.find();
            y = Integer.valueOf(m.group());
        }
        else {
            throw new InvalidLevelException();
        }

        if (sub3 != null) {
            Direction d = Direction.getDirectionFromString(sub3);
            dir = Direction.toString(d);
        }

        subs.add(Integer.toString(x));
        subs.add(Integer.toString(y));
        if (dir != null) {
            subs.add(dir);
        }

        return subs;
    }
}
