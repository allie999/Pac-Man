package group.a2100.game;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static group.a2100.game.RankTable.load;
import static org.junit.Assert.assertEquals;
/**
 * @author Allie(Yihan) (u6684916)
 * @Date 22/05/2019
 */
public class RankTableTest {

//        write player to ranking table (same code used in activity)
        public void addNameToFile(String name, int score){
            try {
                File file =  new File("testing.txt");
                FileWriter fr = new FileWriter(file, true);
                BufferedWriter br = new BufferedWriter(fr);
                br.write(name+" "+score+"\n");
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    @Test
    public void loadAndStoreFileTest(){
        File file = new File("testing.txt");

        addNameToFile("Nebula",90);
        addNameToFile("Thor",100);
        addNameToFile("Hulk",90);
        ArrayList<RankTable.Person> personList = load("testing.txt");

        String[] nameArr = new String[]{"Nebula","Thor","Hulk"};
        int[] scoreArr = new int[]{90,100,90};
        // check values of person stored in the txt file
        for (int i = 0; i < personList.size(); i++) {
            assertEquals(personList.get(i).name,nameArr[i]);
            assertEquals(personList.get(i).score,scoreArr[i]);
        }

        // checking length for personList
        assertEquals(personList.size(),3);

        addNameToFile("Thanos",1000);
        personList = load("testing.txt");
        assertEquals(personList.get(personList.size()-1).name,"Thanos");
        assertEquals(personList.get(personList.size()-1).score,1000);
        assertEquals(personList.size(),4);
        file.delete();
    }

}
