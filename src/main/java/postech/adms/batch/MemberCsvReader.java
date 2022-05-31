package postech.adms.batch;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by miriet on 2017. 5. 5..
 */
public class MemberCsvReader {


    private String filename = "input\\test.csv";

    public MemberCsvReader() {}

    public List<String[]> readCsv() {

        List<String[]> data = new ArrayList<String[]>();

        try {
            // CSVReader reader = new CSVReader(new FileReader(filename), '\t');
            // UTF-8
            CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
            String[] s;

            while ((s = reader.readNext()) != null) {
                data.add(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
