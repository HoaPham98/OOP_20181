package n06.oop.generator;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GeneratorUtils {
    /**
     * Lấy ngẫu nhiên 1 phần tử trong list
     * @param list
     * @return phần tử ngẫu nhiên
     */
    public static String getRandomFromList(List<String> list) {
        return list.get(ThreadLocalRandom.current().nextInt(0,list.size()));
    }

    /**
     * Lấy ngẫu nhiên 1 phần tử trong mảng
     * @param list
     * @return phần tử ngẫu nhiên
     */
    public static String getRandomFromList(String[] list) {
        return list[ThreadLocalRandom.current().nextInt(0,list.length)];
    }

    /**
     * Tạo IRI quan hệ từ tên quan hệ
     * @param name tên quan hệ
     * @return IRI quan hệ
     */
    public static String nameToIRIString(String name) {
        return name.replaceAll(" ","_");
    }

    /**
     * Chia các số từ 0 đến whole thành các phần
     * @param whole chặn trên
     * @param parts số phần muốn chia
     * @return mảng chứa độ dài các phần
     */
    public static List<Integer> splitIntoParts(int whole, int parts) {
        List<Integer> arr = new ArrayList<>(parts);
        int remain = whole;
        int partsLeft = parts;
        for (; partsLeft > 0; ) {
            int size = (remain + partsLeft - 1) / partsLeft; // rounded up, aka ceiling
            arr.add(size);
            remain -= size;
            partsLeft--;
        }
        Collections.shuffle(arr);
        return arr;
    }

    /**
     * Lấy thông tin số lượng từng loại thực thể
     * @return map chứa tên loại thực thế -> số lượng
     */
//    public static Map<String, Integer> getSizeOfType() {
//        Map<String, Integer> sizeOfType;
//        RepositoryConnection conn = ConnectionManager.getConnection();
//        // Tính số lượng từng loại đối tượng có trong database
//        String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
//                "prefix model: <http://nhom06/model#>\n" +
//                "SELECT ?type (count(?type) as ?count)  WHERE {\n" +
//                "    ?x rdf:type ?type\n" +
//                "    FILTER regex(str(?x), \"http://nhom06/model#\", \"i\")\n" +
//                "}\n" +
//                "GROUP BY ?type";
//        TupleQuery query = conn.prepareTupleQuery(queryString);
//        // A QueryResult is also an AutoCloseable resource, so make sure it gets
//        // closed when done.
//        try (TupleQueryResult result = query.evaluate()) {
//            sizeOfType = new HashMap<>();
//            // we just iterate over all solutions in the result...
//            while (result.hasNext()) {
//                BindingSet solution = result.next();
//                String type = solution.getValue("type").stringValue().substring(ENT.NAMESPACE.length());
//                int count = 0;
//                try {
//                    count = Integer.parseInt(solution.getValue("count").stringValue());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                sizeOfType.put(type, count);
//            }
//        }
//        // Để giá trị mặc định là 0
//        for (String type : TYPES) {
//            if (!sizeOfType.containsKey(type)) {
//                sizeOfType.put(type, 0);
//            }
//        }
//
//        return sizeOfType;
//    }
}
