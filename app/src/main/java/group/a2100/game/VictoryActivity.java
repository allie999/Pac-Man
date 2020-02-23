
package group.a2100.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
/**
 * @author Vikram (u6390710) Allie(Yihan) (u6684916)
 * @Date 20/04/2019
 */
public class VictoryActivity extends AppCompatActivity {
    static VictoryActivity va;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        va=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victory);
    }

    public void goToMain(View view) {
        GameActivity.gameActivity.finish();
        this.finish();
    }

    public void goToGameOver(View view) {
        Intent intent = new Intent(this, GameOverActivity.class);
        startActivity(intent);
    }
}
