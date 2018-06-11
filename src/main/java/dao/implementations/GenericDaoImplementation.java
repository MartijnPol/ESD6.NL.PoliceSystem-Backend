package dao.implementations;

import dao.JPA;
import dao.interfaces.GenericDao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Martijn van der Pol on 30-05-18
 **/
@Stateless
@JPA
public abstract class GenericDaoImplementation<T> implements GenericDao<T> {

    @PersistenceContext(unitName = "PoliceSystemPU")
    protected EntityManager entityManager;

    private Class<T> type;

    public GenericDaoImplementation() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }


    @Override
    public T create(T t) {
        this.entityManager.persist(t);
        return t;
    }

    @Override
    public T update(T t) {
        return this.entityManager.merge(t);
    }

    @Override
    public void deleteById(Long id) {
        this.entityManager.remove(findById(id));
    }

    @Override
    public void delete(T t) {
        this.entityManager.remove(this.entityManager.merge(t));
    }

    @Override
    public T findById(Long id) {
        return this.entityManager.find(type, id);
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery("SELECT t FROM " + type.getSimpleName() + " t", type).getResultList();
    }
}
