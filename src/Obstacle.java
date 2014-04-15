
public enum Obstacle {
    EMPTY,
    BLOCK,
    PIPE_VERT,
    PIPE_HOR,
    RAMP_LEFT,
    RAMP_TOP,
    RAMP_RIGHT,
    RAMP_BOTTOM,
    BREAKABLE,
    FINISH,
    PLAYER;

    public static boolean isBlocked(Direction d, Obstacle o) {
        boolean res = true;

        switch (d) {
            case UP:
                if (o == EMPTY || o == PIPE_VERT || o == RAMP_BOTTOM) {
                    res = false;
                }
                break;
            case DOWN:
                if (o == EMPTY || o == PIPE_VERT || o == RAMP_TOP) {
                    res = false;
                }
                break;
            case LEFT:
                if (o == EMPTY || o == PIPE_HOR || o == RAMP_RIGHT) {
                    res = false;
                }
                break;
            case RIGHT:
                if (o == EMPTY || o == PIPE_HOR || o == RAMP_LEFT) {
                    res = false;
                }
                break;
        }

        return res;
    }
}
