package n06.oop;

import dnl.utils.text.table.TextTable;
import n06.oop.query.Query;
import n06.oop.query.QueryResult;
import n06.oop.query.QueryStatistic;
import org.eclipse.rdf4j.query.QueryEvaluationException;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args)
    {
        QueryStatistic statistic = new QueryStatistic();
        statistic.statistic();
    }
}
