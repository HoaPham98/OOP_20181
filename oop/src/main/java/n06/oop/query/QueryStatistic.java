package n06.oop.query;

import dnl.utils.text.table.TextTable;
import n06.oop.config.Setting;

import java.util.*;

public class QueryStatistic {

    private Map<String, String> basicQueries;
    private Map<String, String> advanceQueries;

    public QueryStatistic() {
        basicQueries = QueryUtils.loadData(Setting.BASIC_FILE);
        advanceQueries = QueryUtils.loadData(Setting.ADVANCE_FILE);
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
            QueryResult result = QueryUtils.query(sql);
            times[0][i] = "" + result.getTimeExec() + " ms";
        }

        TextTable tt = new TextTable(headers, times);
        tt.printTable();
    }
}
