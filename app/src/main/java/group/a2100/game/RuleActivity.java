package group.a2100.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


/**
 * @author Allie(Yihan) (u6684916)
 * @Date 20/04/2019
 */

public class RuleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule);
    }


    //    go to help
    public void returnMainActivity(View view) {
        finish();
    }
}
