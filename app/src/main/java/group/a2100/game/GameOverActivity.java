package group.a2100.game;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 * @author
 *     Vikram (u6390710)
 *     Allie(Yihan) (u6684916)
 * @Date 20/04/2019
 */

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
    }
//    close all the activities except main activity
    public void mainMenuActivity(View view) {
        if(VictoryActivity.va != null) VictoryActivity.va.finish();
        GameActivity.gameActivity.finish();
        this.finish();

    }

    public void rankActivity(View view) {
        Intent intent = new Intent(this, RankTableActivity.class);
        startActivity(intent);
    }

    public void savePlayer(View view) {
        Log.d("GameOverActivity", "savePlayer: saved data");
        int score = GameActivity.gameView.game.score; // + GameActivityLvlTwo.gameView.game.score;
        EditText editText = (EditText) findViewById(R.id.editText2);
        String name = editText.getText().toString();

//    add player to the ranking table
        try {
            File file =  new File(Environment.getExternalStorageDirectory() + "/rankTable.txt");
            FileWriter fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            br.write(name+" "+score+"\n");
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
