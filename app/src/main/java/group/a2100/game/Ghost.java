package group.a2100.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import static group.a2100.game.Direction.DOWN;
import static group.a2100.game.Direction.LEFT;
import static group.a2100.game.Direction.RIGHT;
import static group.a2100.game.Direction.UP;

/**
 * @author
 *     Calum (u6044174)
 *     Allie(Yihan) (u6684916)
 * @Date 20/04/2019
 */


/*
    Class for the Ghost sprite. This contains the attributes of the ghosts, and a method
    to draw them. For more detail, see the Sprite abstract class.
 */
public class Ghost extends Sprite {


    int colour;
    public static final int DEFAULT_SPEED = 2;


    public Ghost(int colour){
//        random generate a direction
        this.direction = (new group.a2100.game.Direction[]{LEFT,RIGHT,UP,DOWN})[(int)(Math.random() * 4)];
        this.bufferedDirection = this.direction;
        this.speed = DEFAULT_SPEED;
        this.colour = colour;
    }


    // This should be redone once we have proper Ghost sprites.
    public void draw(Canvas canvas, float size, float xpos, float ypos, Paint p) {
        // Use paint for body of ghost, give different paint when super dot eaten
        //canvas.drawCircle(xpos + size/2.0f, ypos + size/2.0f, size/2.0f, p);
        float xCen = xpos + size/2.0f;
        float yCen = ypos + size/2.0f;
        float inc = size/11.0f;
        /*
        ........... 0
        ...@@@@@...
        ..@@@@@@@..
        .@@@@@@@@@.
        .@@@@@@@@@.
        .@@@@C@@@@.
        .@@@@@@@@@.
        .@@@@@@@@@.
        .@@.@@@.@@.
        .@@.@@@.@@.
        ........... 1
         */
        canvas.drawRect(xCen - 4.0f*inc, yCen - 2.0f*inc, xCen + 4.0f*inc, yCen + 3.0f*inc, p);
        canvas.drawRect(xCen - 3.0f*inc, yCen - 3.0f*inc, xCen + 3.0f*inc, yCen, p);
        canvas.drawRect(xCen - 2.0f*inc, yCen - 4.0f*inc, xCen+2.0f*inc, yCen, p);
        canvas.drawRect(xCen - 4.0f*inc, yCen - 2.0f*inc, xCen-2.0f*inc, yCen+4.0f*inc,p);
        canvas.drawRect(xCen - 1.0f*inc, yCen - 2.0f*inc, xCen+1.0f*inc, yCen+4.0f*inc,p);
        canvas.drawRect(xCen + 2.0f*inc, yCen - 2.0f*inc, xCen+4.0f*inc, yCen+4.0f*inc,p);

        Paint eyePaint = new Paint();
        eyePaint.setColor(Color.WHITE);
        canvas.drawRect(xCen-2.0f*inc, yCen-2.0f*inc, xCen-0.5f*inc,yCen+1.5f*inc, eyePaint);
        canvas.drawRect(xCen-2.5f*inc, yCen-1.5f*inc, xCen-0.1f*inc,yCen+1.0f*inc, eyePaint);
        canvas.drawRect(xCen+0.5f*inc, yCen-2.0f*inc, xCen+2.0f*inc,yCen+1.5f*inc, eyePaint);
        canvas.drawRect(xCen+0.1f*inc, yCen-1.5f*inc, xCen+2.5f*inc,yCen+1.0f*inc, eyePaint);

        Paint irisPaint = new Paint();
        irisPaint.setColor(Color.BLUE);
        float xOffset = 0.0f;
        float yOffset = 0.0f;

        switch (direction) {
            case UP:
                yOffset -= 0.5f*inc;
                break;
            case DOWN:
                yOffset += 0.5f*inc;
                break;
            case LEFT:
                xOffset -= 0.25f*inc;
                break;
            case RIGHT:
                xOffset += 0.25f*inc;
                break;
            default:
                break;
        }

        canvas.drawCircle(xCen - 1.25f*inc + xOffset, yCen - 0.25f*inc + yOffset, 2.0f*inc/3.0f, irisPaint);
        canvas.drawCircle(xCen + 1.25f*inc + xOffset, yCen - 0.25f*inc + yOffset, 2.0f*inc/3.0f, irisPaint);

    }
}