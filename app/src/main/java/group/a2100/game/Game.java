
package group.a2100.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author
 *     Vikram (u6390710)
 *     Calum (u6044174)
 *     Aaron(Hang) (u5939273)
 * @Date 20/04/2019
 */

public class Game {
    private Random rand = new Random();

    private boolean dying;
    private int deathCountdown = 72; // Expand by 5 degrees each time

    private int superCycles = 0; // How long the superdot will last
    private final int superDotLength = 300;
    private Paint textPaint;

    public Map m;

    int remainingDots;
    int score;
    int lives;
    int level;
    PacMan pacMan;

    Tile[][] tiles;
    float mapWidth;
    float mapHeight;

    private SpritePos pacPos;
    private List<SpritePos> ghostsPos;

    /*
        This class is used to track positional information for sprites. It tracks four
        pieces of information:
            - A sprite
            - A tile, representing the tile the sprite is on
            - A subdivision, representing how far the sprite is from the centre of the tile
            - A direction, representing the direction that the sprite is from the centre of the tile
        So logically, a tile looks something like this:
            . . . . @ . . . .
            . . . . . . . . .   Where C is the centre of the tile. The first @ to the right of C
            . . . . @ . . . .   would be direction right, subdivision 1. The .'s in between the @'s
            . . . . . . . . .   are blank space. So the sprites on the board will only ever actually
            @ . @ . C . @ . @   be present on the @'s.
            . . . . . . . . .
            . . . . @ . . . .   So a sprite moves within a tile by changing its subdivision and/or
            . . . . . . . . .   direction, and moves between tiles by transferring to a neighbouring
            . . . . @ . . . .   tile.
     */
    private class SpritePos {
        // Tiles are glued on subPos = SUBDIVS, so subPos = SUBDIVS, direction = RIGHT is the same
        // position as subPos = SUBDIVS, direction = LEFT on the neighbouring tile.
        private static final int SUBDIVS = 20;
        Sprite sprite;
        Tile pos;
        Direction subDir = Direction.LEFT;
        int subPos = 0;
        // subPos should always be between 0 and SUBDIVS

        SpritePos(Tile start, Sprite sp) {
            sprite = sp;
            pos = start;
        }

        // Returns the absolute X position of a sprite (for drawing)
        float absoluteX(float size) {
            float xc = size * pos.xc;
            float offset = (float) subPos * size/((float) (2*SUBDIVS));

            switch (subDir) {
                case LEFT:
                    xc -= offset;
                    break;
                case RIGHT:
                    xc += offset;
                    break;
                default:
                    break;
            }
            return xc;
        }

        // Returns the absolute Y position of a sprite (for drawing)
        float absoluteY(float size) {
            float yc = size * pos.yc;
            float offset = (float) subPos * size/((float) (2*SUBDIVS));

            switch (subDir) {
                case UP:
                    yc -= offset;
                    break;
                case DOWN:
                    yc += offset;
                    break;
                default:
                    break;
            }
            return yc;
        }

        // Draws the sprite at the correct position
        void draw(Canvas c, float size, Paint p) {
            sprite.draw(c, size, absoluteX(size), absoluteY(size), p);
        }

        // TODO: check for logic
        /*
            This function moves the sprites around the board.
         */
        void Step() {

            if (!sprite.direction.isParallel(subDir) && sprite.direction != Direction.STOP) {
                sprite.direction = Direction.STOP; // This case should be quite rare, and perhaps never occur
            } else {

                int remaining = sprite.speed; // The number of subdivisions the sprite will move
                if (superCycles > 0 && !sprite.isPacman) {
                    remaining /= 2; // Halve speed of ghosts during superdot
                }

                // Take care of complete movement.
                // The sprite can only change direction when subPos = 0, and the sprite has
                // to change tiles when subPos = SUBDIVS, so we do the movement piecewise
                while (remaining > 0) {
                    // Eats the dot at the centre of the tile, if there is one
                    if (subPos == 0 && sprite.isPacman) {
                        if (pos.dot == Dot.NORMAL) {
                            pos.dot = Dot.NONE;
                            remainingDots--;
                            score += 10;
                        } else if (pos.dot == Dot.SUPER) {
                            pos.dot = Dot.NONE;
                            superCycles = superDotLength; // triggers the superdot effect
                            remainingDots--;
                            score += 30;
                        }
                    }

                    // If the buffered direction is opposite the current direction,
                    // it's fine to turn immediately. Otherwise it has to wait until
                    // it reaches the centre of a tile (subPos == 0).
                    // Every sprite has a buffered direction, which is the direction
                    // that it will attempt to turn in when it reaches the centre of
                    // a tile.
                    if (sprite.bufferedDirection == sprite.direction.opposite()) {
                        sprite.direction = sprite.bufferedDirection;
                    } else if (subPos == 0) {
                        if (pos.adj(sprite.bufferedDirection) != null) {
                            // If it can turn, turns
                            sprite.direction = sprite.bufferedDirection;
                            subDir = sprite.direction;
                        } else if (pos.adj(sprite.direction) == null) {
                            // If it can't turn and can't keep moving, it stops
                            sprite.direction = Direction.STOP;
                            remaining = 0;
                        }
                    }

                    // This section handles the logic of the movement. That is,
                    // transitioning tiles and stopping at centre points if
                    // necessary.
                    boolean away = sprite.direction == subDir;
                    if (away) {
                        int dist = SUBDIVS - subPos;
                        if (dist <= remaining) { // Distance to boundary <= distance to travel
                            subPos = SUBDIVS;
                            pos = pos.adj(sprite.direction);
                            subDir = subDir.opposite();
                            remaining -= dist;
                        } else { // Distance to boundary > distance to travel
                            subPos += remaining;
                            remaining = 0;
                        }
                    } else {
                        int dist = subPos; // Distance to the middle
                        if (dist <= remaining) { // Distance to middle <= distance to travel
                            subPos = 0;
                            subDir = subDir.opposite();
                            remaining -= dist;
                        } else { // Distance to middle > distance to travel
                            subPos -= remaining;
                            remaining = 0;
                        }

                    }
                    // If it reaches the middle of a tile or transfers tiles and still
                    // has remaining movement, the while loop will carry it out.

                    if (sprite.direction == Direction.STOP) {
                        remaining = 0;
                    }
                }
            }
        }
    }

    // Initializes the game variables, nothing fancy here
    public Game(int level) {
        this.dying = false;
        this.score = 0;
        this.lives = 3;

        this.textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(50f);
        textPaint.setStrokeWidth(5f);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);

        updateMap(level);
    }

    // Sets the variables which track map information. Gets data from
    // the map class.
    public void updateMap(int newLevel) {
        this.level = newLevel;
        this.m = new Map(level);

        tiles = m.grid;
        remainingDots = m.dotCount;
        superCycles = 0;

        mapWidth = tiles.length;
        mapHeight = tiles[0].length;

        pacMan = new PacMan();
        pacPos = new SpritePos(m.pacStart, pacMan);

        ghostsPos = new ArrayList<>();

        int ghostCount = 0;
        for (Tile t : m.ghostStarts) {
            Ghost g = new Ghost(ghostCount);
            ghostCount++;
            ghostsPos.add(new SpritePos(t, g));
        }
    }

    // Called on every game tick, moves all sprites and checks for collisions.
    public void Step(){
        if (!dying) {

            if (superCycles > 0) {superCycles--;}

            // Ghost behaviour programmed here.
            // At every turn, the ghosts pick a random direction to move in.
            for (SpritePos p : ghostsPos) {
                Sprite g = p.sprite;

                // Update buffered direction
                if (g.direction == g.bufferedDirection) {
                    int ix = rand.nextInt(2);
                    g.bufferedDirection = g.direction.perp()[ix];
                } else if (g.direction == Direction.STOP) {
                    List<Direction> l = p.pos.validDirections();
                    int ix = rand.nextInt(l.size());
                    g.bufferedDirection = l.get(ix);
                }

                p.Step();
            }

            // Check for collisions
            List<SpritePos> toDelete = new ArrayList<>();

            pacPos.Step();
            for (SpritePos p : ghostsPos) {
                // Check if pacman is adjacent to any ghosts
                boolean collision = false;
                float xDif = (pacPos.absoluteX(1.0f) - p.absoluteX(1.0f));
                float yDif = (pacPos.absoluteY(1.0f) - p.absoluteY(1.0f));

                float euc = (float) Math.sqrt(xDif * xDif + yDif * yDif);
                if (euc < 0.3) {
                    collision = true;
                }

                if (superCycles > 0 && collision) {
                    score += 20 * (toDelete.size() + 1);
                    toDelete.add(p);
                } else if (collision) {
                    this.dying = true;
                    lives--;
                }
            }

            for (SpritePos p : toDelete) {
                ghostsPos.remove(p);
            }

        }
    }

    // Draws the map and everything on it. Scales everything to the size
    // of the screen, and makes the map fill as much of the screen as
    // possible.
    public void draw(Canvas canvas) {
        float w = canvas.getWidth();
        float h = canvas.getHeight();
        float tileSize;

        // To make the board fill the screen, we will need either
        // mapWidth * tileSize = w OR mapHeight * tileSize = h.
        // We choose the option which allows the rest of the map
        // to fit on the screen.
        float xRatio = mapWidth / w;
        float yRatio = mapHeight / h;

        if (xRatio > yRatio) {
            tileSize = w / mapWidth;
        } else {
            tileSize = h / mapHeight;
        }
        // The size of everything we draw is scaled to tileSize.

        // Draws the tiles
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                if (tiles[x][y] != null) {
                    tiles[x][y].draw(canvas, tileSize, x, y);
                }
            }
        }

        Paint pacPaint = new Paint();
        pacPaint.setColor(Color.YELLOW);

        canvas.drawText("Score: " + this.score, w * 1f / 8f, h * 10f / 11f, textPaint);
        canvas.drawText("Lives: " + this.lives, w * 5f / 8f, h * 10f / 11f, textPaint);

        // Draws the ghosts
        Paint ghostPaint = new Paint();
        if (superCycles > 0) {
            ghostPaint.setColor(Color.BLUE);
        }
        else {
            ghostPaint.setColor(Color.RED);
        }
        for (SpritePos sp : ghostsPos) {
            sp.draw(canvas, tileSize, ghostPaint);
        }

        // Handles the drawing of the PacMan when potentially dead
        if (!dying) {
            pacPos.draw(canvas, tileSize, pacPaint);
        } else {
            float xc = tileSize * pacPos.pos.xc;
            float yc = tileSize * pacPos.pos.yc;
            float offset = (float) pacPos.subPos * tileSize/((float) (2*SpritePos.SUBDIVS));

            switch (pacPos.subDir) {
                case UP:
                    yc -= offset;
                    break;
                case DOWN:
                    yc += offset;
                    break;
                case LEFT:
                    xc -= offset;
                    break;
                case RIGHT:
                    xc += offset;
                    break;
                default:
                    break;
            }

            pacMan.drawDeath(canvas, tileSize, xc, yc, pacPaint, deathCountdown);
            deathCountdown--;

            if (deathCountdown < 0 && lives != 0) {
                deathCountdown = 72;
                pacPos = new SpritePos(m.pacStart, pacMan);
                this.dying = false;
            }
        }
    }

    // This function changes the behaviour of the whole game whenever a fling gesture happens
    public void fling(Direction direction){
        pacMan.bufferedDirection = direction;
    }

    public boolean isGameOver() {return lives <= 0 && deathCountdown <= 0;}

}
