package n06.oop.generator;

import n06.oop.model.entities.BaseEntity;
import org.eclipse.rdf4j.model.Model;

public interface IGenerator<T extends BaseEntity> {
    /**
     * Tạo giả lập database theo số lượng
     * @param num số lượng thực thể
     */
    void generateData(int num);

    /**
     * Tạo model từ đối tượng
     * @param item dối tượng cần tạo model
     * @return model
     */
    Model createModel(T item);
    
    /**
     * Get giá trị tên của loại thực thể
     * @return tên
     */
    String getName();
}
