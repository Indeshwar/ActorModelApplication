package org.example;

import com.opencsv.CSVReader;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class DauClass {

    public ArrayList<String> keyList(JSONObject obj){
        ArrayList<String> list = new ArrayList<String>();
        Iterator d =obj.keys();

        while(d.hasNext()){
            list.add(d.next().toString());

        }
        return list;
    }



    public int reader(JSONObject obj)
    {
        CSVReader reader = null;
        try
        {
            ArrayList<String> k = keyList(obj);//return all the keys
            String path = obj.get(k.get(0)).toString();  //path of file
            //Get the CSVReader instance with specifying the delimiter to be used
            reader = new CSVReader(new FileReader(path), ',');

            String [] nextLine;

            //Read one line at a time
            int count = 0;

            while ((nextLine = reader.readNext()) != null)
            {

                for(int i = 0; i < nextLine.length-1; i++)
                {

                    if(k.size() == 3 && obj.get(k.get(1)).toString().equals(nextLine[i]) && obj.get(k.get(2)).toString().equals(nextLine[i+1])){
                        count++;
                    }else{
                        if(k.get(1).equals("firstName") && obj.get(k.get(1)).toString().equals(nextLine[i])){ //check whether first name match
                            count++;
                        }else if(k.get(1).equals("lastName") && obj.get(k.get(1)).toString().equals(nextLine[i+1])){ //check whether last name match
                            count++;
                        }

                    }

                }
            }
            return count;

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
