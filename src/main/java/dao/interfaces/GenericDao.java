package dao.interfaces;

import java.util.List;

/**
 * Created by Martijn van der Pol on 30-05-18
 **/
public interface GenericDao<T> {

    T create(T t);
    
    void deleteById(Long id);

    void delete(T t);

    T findById(Long id);

    List<T> findAll();

}
