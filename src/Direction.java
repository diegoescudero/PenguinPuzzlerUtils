
public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    NONE;

    public static Direction getDirectionFromString(String s) {
        String str = s.toLowerCase();

        if (str.contains("up")) {
            return UP;
        }
        else if (str.contains("down")) {
            return DOWN;
        }
        else if (str.contains("left")) {
            return LEFT;
        }
        else if (str.contains("right")) {
            return RIGHT;
        }
        else {
            return NONE;
        }
    }

    public static String toString(Direction d) {
        switch (d) {
            case UP:
                return "up";

            case DOWN:
                return "down";

            case LEFT:
                return "left";

            case RIGHT:
                return "right";

        }

        return null;
    }
}
