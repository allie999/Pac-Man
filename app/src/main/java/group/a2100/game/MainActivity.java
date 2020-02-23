package group.a2100.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @author
 *     Aaron(Hang) (u5939273)
 * @Date 04/05/2019
 */
public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //    start the game
    public void startGameActivity(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    //    go to help
    public void startRuleActivity(View view) {
        Intent intent = new Intent(this, RuleActivity.class);
        startActivity(intent);
    }

}
