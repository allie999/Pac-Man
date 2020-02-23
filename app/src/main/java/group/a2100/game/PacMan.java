package group.a2100.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import static group.a2100.game.Direction.LEFT;


/**
 * @author
 *     Vikram (u6390710)
 *     Aaron(Hang) (u5939273)
 * @Date 20/04/2019
 */

/*
    Class for the pacman sprite. Contains the attributes of the PacMan, and
    has various values and methods relating to drawing the PacMan. See the
    Sprite class for more detail.
 */

public class PacMan extends Sprite {
    public static final Direction DEFAULT_DIRECTION = LEFT;
    public static final int DEFAULT_SPEED = 2;
    private int angle = 0;
    private int orientation = 0;
    private int inc = 10;

    public PacMan(){
        this.direction = DEFAULT_DIRECTION;
        this.bufferedDirection = DEFAULT_DIRECTION;
        this.speed = DEFAULT_SPEED;
        this.isPacman = true;
    }

    // Draws the death animation of PacMan. This is called once per frame while
    // PacMan is dying, and the prog parameter tracks the progress through the
    // animation.
    public void drawDeath(Canvas canvas, float size, float xpos, float ypos, Paint p, int prog) {
        float xCentre = xpos + size/2.0f;
        float yCentre = ypos + size/2.0f;
        float radius = 2.0f* size / 5.0f;

        if (prog >= 0) {
            angle = prog * 5;
        } else {
            angle = 0;
        }
        int sweep = angle;
        int startAngle = orientation - (angle/2 - 180);

        RectF oval = new RectF(xCentre - radius, yCentre - radius, xCentre + radius, yCentre + radius);
        canvas.drawArc(oval, startAngle, sweep, true, p);
    }

    // Draws the PacMan. Uses the angle variable to track the angle
    // of the opening of his mouth.
    public void draw(Canvas canvas, float size, float xpos, float ypos, Paint p) {
        angle += inc;
        if (angle > 120 || angle < 0) {
            inc *= -1;
            angle += inc;
        }

        switch (direction) {
            case UP:
                orientation = 270;
                break;
            case DOWN:
                orientation = 90;
                break;
            case RIGHT:
                orientation = 0;
                break;
            case LEFT:
                orientation = 180;
                break;
            default:
                break;
        }

        int sweep = 360 - angle;
        int startAngle = orientation + angle / 2;

        float xCentre = xpos + size/2.0f;
        float yCentre = ypos + size/2.0f;
        float radius = 2.0f* size / 5.0f;
        RectF oval = new RectF(xCentre - radius, yCentre - radius, xCentre + radius, yCentre + radius);
        canvas.drawArc(oval, startAngle, sweep, true, p);
    }
}
