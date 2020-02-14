package core;

import java.io.*;
import java.util.*;

public class FileMock {
    public static void main(String[] args) throws IOException {

        String inputFile = args[0];
        String outputFile = args[1];
        int rowCount = Integer.parseInt(args[2]);

        List<String> header = new ArrayList<String>();

        Map<Integer,FileMockConfig> data1 = new HashMap<Integer, FileMockConfig>();
        String line ="";
        String colsDelim = "\\|";

        int iterator = 0;
        int counter = 0;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            while( (line= br.readLine() )!= null){
                if (iterator == 0){
                    iterator++;
                    continue;
                }
                String cols[] = line.split(colsDelim);
                FileMockConfig  columnConfig = new FileMockConfig();
                columnConfig.setColumnName(cols[0]);
                columnConfig.setDataType(cols[1]);
                if(!cols[2].equalsIgnoreCase("none")){
                    columnConfig.setRangeMax(Integer.valueOf(cols[2].split("-")[0]));
                    columnConfig.setRangeMin(Integer.valueOf(cols[2].split("-")[1]));
                }
                columnConfig.setOptions(Arrays.asList(cols[3].split(",")));
                data1.put(counter,columnConfig);
                header.add(columnConfig.getColumnName());
                counter ++;


            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileWriter fw = null;
        try {
            fw = new FileWriter(outputFile);
            fw.write(header.toString().replace("[", "").replace("]", "") + "\n");
            while (rowCount -->0)
            {
                 List<String>colsInRow = new ArrayList<String>();
                 for (Integer key : data1.keySet()){
                     String colvalue = null;
                    colvalue = getNewColValue(data1.get(key));
                    colsInRow.add(colvalue);
                 }
                 String row = colsInRow.toString().replace("[","").replace("]","");
                 fw.write(row+"\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            fw.close();
        }
    }

    private  static  String getNewColValue(FileMockConfig config)
    {
        String colValue = null;
        Random random = new Random();

        if (config.getDataType().equalsIgnoreCase("INT")){
            colValue=""+random.nextInt(config.getRangeMax());
        }
        if (config.getDataType().equalsIgnoreCase("STRING"))
        {
            colValue=""+config.getOptions().get(random.nextInt(config.getOptions().size()));
        }

        return colValue;

    }
}
