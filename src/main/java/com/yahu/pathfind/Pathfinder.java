package com.yahu.pathfind;

import com.yahu.pathfind.Goal.*;

public class Pathfinder {
    private static final Pathfinder INSTANCE = new Pathfinder();
    private Goal currentGoal;
    private boolean running = false;

    private Pathfinder() {}

    public static Pathfinder getInstance() {
        return INSTANCE;
    }

    public void setGoal(Goal goal) {
        this.currentGoal = goal;
        this.running = true;
    }

    public void stop() {
        this.currentGoal = null;
        this.running = false;
    }

    public void tick() {
        if (!running || currentGoal == null) return;
        // Pathfinding logic here
    }

    public Goal getCurrentGoal() { return currentGoal; }
    public boolean isRunning() { return running; }

    public static abstract class Goal {
        public static class PositionGoal extends Goal {
            public final int x, y, z;
            public PositionGoal(int x, int y, int z) { this.x = x; this.y = y; this.z = z; }
        }

        public static class BlockGoal extends Goal {
            public final String block;
            public BlockGoal(String block) { this.block = block; }
        }

        public static class EntityGoal extends Goal {
            public final String entityName;
            public EntityGoal(String entityName) { this.entityName = entityName; }
        }

        public static class ExploreGoal extends Goal {}
        public static class MineGoal extends Goal {
            public final String ore;
            public MineGoal(String ore) { this.ore = ore; }
        }

        public static class FollowGoal extends Goal {
            public final String target;
            public FollowGoal(String target) { this.target = target; }
        }
    }
}