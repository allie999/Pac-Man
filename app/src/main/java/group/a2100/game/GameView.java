
package group.a2100.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
/**
 * @author
 *     Vikram (u6390710)
 *     Calum (u6044174)
 *     Aaron(Hang) (u5939273)
 * @Date 20/04/2019
 */
public class GameView extends View implements Runnable {
    public static final int STEPDELAY = 10;//10;    // the timer for handler -- refresh rate
    Paint paint;
    Handler repaintHandler;
    Game game;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.BLACK);

        repaintHandler = new Handler();
        repaintHandler.postDelayed(this, STEPDELAY);
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        game.draw(canvas);
    }

    public void setGame(int level, int score, int lives) {
        game = new Game(level);
        game.score = score;
        game.lives = lives;
    }


    public void fling (Direction direction) {
        game.fling(direction);
    }

    @Override
    public void run() {
        game.Step();
        if (game.remainingDots <= 0) {
            game.level++;
            if (game.level <= Map.stored_maps) {
                game.updateMap(game.level);
            } else {
                Context context = this.getContext();
                while (!(context instanceof GameActivity)) {
                    context = ((GameActivity) context).getBaseContext();
                }
                ((GameActivity) context).goToVictory();
            }

            this.invalidate();
            repaintHandler.postDelayed(this, GameView.STEPDELAY);
        } else if (game.isGameOver()) {
            Context context = this.getContext();
            while (!(context instanceof GameActivity)) {
                context = ((GameActivity) context).getBaseContext();
            }
            ((GameActivity) context).finishLvlOne();
            this.invalidate();
        }
        else {
            this.invalidate();
            repaintHandler.postDelayed(this, GameView.STEPDELAY);
        }

    }



}
