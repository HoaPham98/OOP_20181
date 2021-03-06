package n06.oop.query;

public class QueryResult {
    private long timeExec;
    private String[] header;
    private String[][] data;

    public QueryResult() {
    }

    public QueryResult(long timeExec, String[] header, String[][] data) {
        this.timeExec = timeExec;
        this.header = header;
        this.data = data;
    }

    public long getTimeExec() {
        return timeExec;
    }

    public void setTimeExec(long timeExec) {
        this.timeExec = timeExec;
    }

    public String[] getHeader() {
        return header;
    }

    public void setHeader(String[] header) {
        this.header = header;
    }

    public String[][] getData() {
        return data;
    }

    public void setData(String[][] data) {
        this.data = data;
    }
}
