package n06.oop.generator.interfaces;

import n06.oop.model.entities.BaseEntity;
import org.eclipse.rdf4j.model.Model;

public interface IGenerator<T extends BaseEntity> {
    void generateData(int num);
    Model createModel(T item);
    String getName();
}
