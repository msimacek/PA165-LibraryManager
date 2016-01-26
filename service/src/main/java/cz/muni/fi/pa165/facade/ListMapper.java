package cz.muni.fi.pa165.facade;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.dozer.Mapper;

/**
 * List Mapper
 * 
 * @author Michael Šimáček
 */
@Named
public class ListMapper {
    @Inject
    private Mapper mapper;

    public <T> T map(Object obj, Class<T> clazz) {
        return mapper.map(obj, clazz);
    }

    public <T> List<T> map(List<?> list, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        for (Object item : list) {
            result.add(mapper.map(item, clazz));
        }
        return result;
    }

    public void map(Object source, Object destination) {
        mapper.map(source, destination);
    }
}
