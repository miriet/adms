package postech.adms.common.util;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanFilter;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import org.springframework.stereotype.Component;
import postech.adms.domain.upload.AlumniUploadDetail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by miriet on 2017. 5. 14..
 */
@Component("admsCsvReader")
public class CsvReader {
    private String filePath = "/data/adms/upload/";

    public CsvReader() {}

    public List<AlumniUploadDetail> readAlumniUploadDetail(String filename) {

        List<AlumniUploadDetail> result = new ArrayList<>();

        try {
            // CSVReader reader = new CSVReader(new FileReader(filename), '\t');
            // UTF-8
            CSVReader createReader = new CSVReader(new InputStreamReader(new FileInputStream(filePath+filename), "UTF-8"));

            HeaderColumnNameMappingStrategy<AlumniUploadDetail> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(AlumniUploadDetail.class);
            CsvToBean<AlumniUploadDetail> csvToBean = new CsvToBean<>();
            result = csvToBean.parse(strategy, createReader);

//
//            while ((s = reader.readNext()) != null) {
//                AlumniUploadDetail alumniUploadDetail = new AlumniUploadDetail();
//                alumniUploadDetail.
//                result.add(s);
//            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private class CodeFilter implements CsvToBeanFilter {

        private final MappingStrategy strategy;

        public CodeFilter(MappingStrategy strategy) {
            this.strategy = strategy;
        }

        public boolean allowLine(String[] line) {
            int index = strategy.getColumnIndex("STATE");
            String value = line[index];
            boolean result = !"production".equals(value);
            return result;
        }

    }
}
