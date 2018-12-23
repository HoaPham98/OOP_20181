package n06.oop.generator;

import n06.oop.generator.impl.*;

import java.util.WeakHashMap;

@SuppressWarnings("rawtypes")
public final class GeneratorFactory {
    private final static WeakHashMap<String, IGenerator> map = new WeakHashMap<>();

    /**
     * Get IGenerator theo tên của loại generator
     * @param name tên loại generator
     * @return IGenerator
     */
    public static IGenerator getGenerator(String name){
        IGenerator dao = map.get(name);
        if(dao != null)
            return dao;
        return createGenerator(name);
    }

    private synchronized static IGenerator createGenerator(String name) {
        IGenerator dao = null;
        if("person".equals(name)){
            dao = new PersonGenerator();
            map.put(name, dao);
        }
        if("time".equals(name)){
            dao = new TimeGenerator();
            map.put(name, dao);
        }
        if("event".equals(name)){
            dao = new EventGenerator();
            map.put(name, dao);
        }
        if("country".equals(name)){
            dao = new CountryGenerator();
            map.put(name, dao);
        }
        if("location".equals(name)){
            dao = new LocationGenerator();
            map.put(name, dao);
        }
        if("organization".equals(name)){
            dao = new OrganizationGenerator();
            map.put(name, dao);
        }
        return dao;
    }
}
