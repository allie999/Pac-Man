package group.a2100.game;

import android.graphics.Canvas;
import android.graphics.Paint;
/**
 * @author
 *     Vikram (u6390710)
 *     Calum (u6044174)
 *     Aaron(Hang) (u5939273)
 * @Date 20/04/2019
 */

/*
    The sprite class collects the information of each mobile entity in the
    game. Think of a Sprite as a record of information which tells the Game
    how it behaves, so this class doesn't really need to encode any behaviour.
    Each sprite must have a draw method to display it on the board.
 */

public abstract class Sprite {
    int speed;
    Direction direction;
    Direction bufferedDirection;
    boolean isPacman = false;

    public abstract void draw(Canvas canvas, float size, float xpos, float ypos, Paint p);

}
