package group.a2100.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @author Allie(Yihan) (u6684916)
 * @Date 20/04/2019
 */

public class RankTable {
    public static class Person {
        String name;
        int score;
        public Person(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }
    public static ArrayList<Person> load(String fileName) {
//        Log cannot be in the function where test runs
//        Log.d("RankTable", "load: load data");
        ArrayList<Person> personList=new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            // read in line and test if null
            String line;

            while((line = reader.readLine()) != null){
                // break string into array
                String[] dataline  = line.split(" ");
                // put data into new Person object
                Person a = new Person(dataline[0],Integer.parseInt(dataline[1]));
                personList.add(a);

            }
            // release system resources
            reader.close();
        } catch (IOException e) { e.printStackTrace(); }
        return personList;
    }

}




