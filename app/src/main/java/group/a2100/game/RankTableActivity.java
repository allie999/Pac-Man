package group.a2100.game;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * @author Allie(Yihan) (u6684916)
 * @Date 20/04/2019
 */
public class RankTableActivity extends AppCompatActivity {
    private static final String TAG = "RankTableActivity";
    public static ArrayAdapter<String> adapter;
    public static ListView lv;
    ArrayList<String> noteList = new ArrayList<>(10);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_table);

        Log.d(TAG, "onCreate: Started");
        lv = (ListView) findViewById(R.id.listview);

        ArrayList<RankTable.Person> personList = RankTable.load(Environment.getExternalStorageDirectory() + "/rankTable.txt");

        // sort personList first (high - low)
        Collections.sort(personList, new Comparator<RankTable.Person>(){
            public int compare(RankTable.Person s1, RankTable.Person s2) {
                return s2.score-s1.score;
            }
        });

        // take top 5 player
        int len = personList.size() >= 5? 5:personList.size();
        for (int i = 0; i < len; i++) {
            RankTable.Person person = personList.get(i);
            String playerInfo = String.format("%-30s %30s %n",person.name,person.score);
            noteList.add(playerInfo);
        }


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,noteList);
        lv.setAdapter(adapter);
        }


    public void returnButton(View view) {
        finish();
    }
}
