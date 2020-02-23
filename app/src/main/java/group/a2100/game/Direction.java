package group.a2100.game;

import android.view.MotionEvent;

/**
 * @author Vikram (u6390710) Calum (u6044174)
 * @Date 18/05/2019
 */

public enum Direction {
    UP,
    DOWN,
    RIGHT,
    LEFT,
    STOP;

    public Direction opposite() {
        if (this == UP) {return DOWN;}
        if (this == DOWN) {return UP;}
        if (this == LEFT) {return RIGHT;}
        if (this == RIGHT) {return LEFT;}
        return STOP;
    }

    public boolean isParallel(Direction d) {
        // Should always be false if one direction is STOP
        return d == this || d == this.opposite();
    }

    public Direction[] perp() {
        if (this == UP || this == DOWN) {
            return new Direction[] {LEFT, RIGHT};
        }
        if (this == LEFT || this == RIGHT) {
            return new Direction[] {UP, DOWN};
        }
        return new Direction[] {UP, DOWN, LEFT, RIGHT};
    }

    // do note that the direction is specified as the normal direction when looking at the
    // movement on the screen, the actual coordinate system of the screen is upside down

    // this method extracts the direction from the fling gesture on the screen
    public static Direction getDirectionFromFling(MotionEvent start, MotionEvent finish) {
        float xDistance = finish.getX() - start.getX();
        float yDistance = finish.getY() - start.getY();
        float ratio = yDistance/xDistance;

        if(xDistance > 0) {
            if (ratio >= 1) {
                return DOWN;
            } else if (ratio >= -1) {
                return RIGHT;
            } else {
                return UP;
            }
        } else if (xDistance < 0){
            if(ratio <= -1){
                return DOWN;
            } else if (ratio <= 1){
                return LEFT;
            } else {
                return UP;
            }
        } else {
            return yDistance > 0 ? DOWN : UP;
        }

    }

    public boolean isOppositeDirection(Direction direction){
        switch (this){
            case UP: return direction == DOWN;
            case DOWN: return direction == UP;
            case LEFT: return direction == RIGHT;
            case RIGHT: return direction == LEFT;
            default: return false;
        }
    }
}
