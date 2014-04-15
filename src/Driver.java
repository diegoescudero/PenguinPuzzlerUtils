import java.util.ArrayList;

public class Driver {

    public static void main(String[] args) {
        System.out.println("Thinking..."); //create a callback that prints this every 5 seconds
        long startTime = System.currentTimeMillis();

        ArrayList<LevelState> states = new ArrayList<LevelState>();
        ArrayList<LevelState> complete = new ArrayList<LevelState>();

        try {
            states.add(new LevelState());
        }
        catch (InvalidLevelException e) {
            System.out.println("Invalid Level");
            System.exit(1);
        }

        //Calculate the possibilities
        while (states.size() >= 1) {
            ArrayList<LevelState> nextStates = new ArrayList<LevelState>();

            for (LevelState s : states) {
                s.calculateStates();
                nextStates.addAll(s.getPossibleStates());
                complete.addAll(s.getCompleteStates());
            }

            states = nextStates;
        }

        long endTime = System.currentTimeMillis();
        //Find the shortest completion if any
        LevelState best = null;
        if (complete.size() == 0) {
            System.out.println((endTime-startTime) + " Milliseconds Later...");
            System.out.println("No Solution Found :(");
        }
        else {
            for (LevelState s : complete) {
                if (best == null || best.getMoveCount() > s.getMoveCount()) {
                    best = s;
                }
            }

            System.out.println((endTime-startTime) + " Milliseconds Later...");
            System.out.println("Best Solution: " + best.getMoveCount());
            System.out.println("Moves: " + best.getMoves());
        }
    }
}
