package n06.oop.query;

import dnl.utils.text.table.TextTable;
import n06.oop.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class QueryStatistic {
    final static String BASIC_FILE = "/query/coban.txt";
    final static String ADVANCE_FILE = "/query/nangcao.txt";

    Map<String, String> basicQueries;
    Map<String, String> advanceQueries;

    public QueryStatistic() {
        basicQueries = loadData(BASIC_FILE);
        advanceQueries = loadData(ADVANCE_FILE);
    }

    public void statistic() {
        List<String> headerList = new ArrayList<>();
        List<String> queries = new ArrayList<>();
        int no = 1;
        for (Map.Entry<String, String> entry: basicQueries.entrySet()) {
            queries.add(entry.getValue());
            headerList.add("CB_" + no);
            no++;
        }

        no = 1;
        for (Map.Entry<String, String> entry: advanceQueries.entrySet()) {
            queries.add(entry.getValue());
            headerList.add("NC_" + no);
            no++;
        }

        String[] headers = headerList.toArray(new String[0]);
        String[][] times = new String[1][headers.length];

        for(int i=0; i<queries.size(); i++) {
            String sql = queries.get(i);
            QueryResult result = Query.query(sql);
            times[0][i] = "" + result.getTimeExec() + " ms";
        }

        TextTable tt = new TextTable(headers, times);
        tt.printTable();
    }

    private Map<String, String> loadData(String file) {
        Map<String, String> map = new LinkedHashMap<>();
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(file);
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String name = null;
            String line;
            while ((line = reader.readLine()) != null) {
                line = Utils.removeUTF8BOM(line);
                if (line.startsWith("-")) {
                    String sql = stringBuilder.toString();
                    if (name != null && !name.isEmpty() && !sql.isEmpty()) {
                        map.put(name, sql);
                        stringBuilder = new StringBuilder();
                    }
                    name = line;
                    continue;
                }
                stringBuilder.append("\n");
                stringBuilder.append(line);
            }
            String sql = stringBuilder.toString();
            if (name != null && !name.isEmpty() && !sql.isEmpty()) {
                map.put(name, sql);
                stringBuilder = new StringBuilder();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }
}
