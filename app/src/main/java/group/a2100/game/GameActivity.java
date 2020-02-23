
package group.a2100.game;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;

// the ongesturelistenr implementation was learnt form a youtube video
// https://www.youtube.com/watch?v=zsNpiOihNXU
/**
 * @author
 *     Vikram (u6390710)
 *     Calum (u6044174)
 *     Aaron(Hang) (u5939273)
 * @Date 20/04/2019
 */

public class GameActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    static GameActivity gameActivity;

    static GameView gameView;
    GestureDetectorCompat gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);

        gameView = findViewById(R.id.gameview1);
        gameView.setGame(1, 0, 3);
        this.gestureDetector = new GestureDetectorCompat(this, this);

        // to let other activity to close game activity
        gameActivity = this;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Direction flingDirection = Direction.getDirectionFromFling(e1, e2);
        gameView.fling(flingDirection);

        return true;
    }

    public void finishLvlOne() {
        Intent intent = new Intent(this, GameOverActivity.class);
        startActivity(intent);
    }

    public void goToVictory() {
        Intent intent = new Intent(this, VictoryActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }
}
