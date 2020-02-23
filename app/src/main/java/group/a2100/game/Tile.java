
package group.a2100.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;
/**
 * @author
 *     Vikram (u6390710)
 *     Calum (u6044174)
 *     Aaron(Hang) (u5939273)
 * @Date 20/04/2019
 */

/*
    A class which encodes the information of the tiles on the board. Each
    tile links to its neighbours, and stores whether or not it has a dot
    on it. There are also some useful methods for finding neighbours of
    tiles.
 */

public class Tile {
    Dot dot;
    Tile left = null;
    Tile right = null;
    Tile up = null;
    Tile down = null;

    int xc;
    int yc;


    Tile(int x, int y, boolean superDot) {
        xc = x;
        yc = y;
        if (superDot) {
            this.dot = Dot.SUPER;
        } else {
            this.dot = Dot.NORMAL;
        }
    }

    public Tile adj(Direction d) {
        switch (d) {
            case DOWN:
                return down;
            case UP:
                return up;
            case LEFT:
                return left;
            case RIGHT:
                return right;
            default:
                return this;
        }
    }

    public List<Direction> validDirections() {
        List<Direction> ret = new ArrayList<>();
        if (up != null) {
            ret.add(Direction.UP);
        }
        if (down != null) {
            ret.add(Direction.DOWN);
        }
        if (left != null) {
            ret.add(Direction.LEFT);
        }
        if (right != null) {
            ret.add(Direction.RIGHT);
        }
        return ret;
    }


    /*
        Draws the tile on the board. Each tile is a square, this method simply
        drwas a blue line across each invalid direction of each tile, so in a
        sense it draws the 'walls' around each tile.
     */
    public void draw(Canvas canvas, float size, int xPos, int yPos) {
        // Find the four corners of the tile
        float xl = size * (float) xPos;
        float xr = xl + size;
        float yt = size * (float) yPos;
        float yb = yt + size;

        Paint p = new Paint();
        p.setColor(Color.BLUE);
        p.setStrokeWidth(5.0f);

        // If a direction is invalid, draws a wall in that direction
        if (up == null) {
            canvas.drawLine(xl, yt, xr, yt, p);
        }
        if (left == null) {
            canvas.drawLine(xl, yt, xl, yb, p);
        }
        if (right == null) {
            canvas.drawLine(xr, yt, xr, yb, p);
        }
        if (down == null) {
            canvas.drawLine(xl, yb, xr, yb, p);
        }

        Paint dotPaint = new Paint();
        dotPaint.setColor(Color.rgb(255,153,0));

        // Draws the dot in the centre of the tile
        if (dot == Dot.NORMAL) {
            canvas.drawCircle(xl + size/2.0f, yt + size/2.0f, size/10.0f,dotPaint);
        } else if (dot == Dot.SUPER) {
            canvas.drawCircle(xl + size/2.0f, yt + size/2.0f, size/5.0f,dotPaint);
        }
    }
}