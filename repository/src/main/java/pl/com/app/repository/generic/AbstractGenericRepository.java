package pl.com.app.repository.generic;

import pl.com.app.exceptions.ExceptionCode;
import pl.com.app.exceptions.MyException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class AbstractGenericRepository<T> implements GenericRepository<T> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<T> entityClass
            = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    protected EntityManager getEntityManager() {
        return entityManager;
    }    
    protected Class<T> getEntityClass() {
        return entityClass;
    }

    @Override
    public T saveOrUpdate(T t) {
        try {
            if (t == null) {
                throw new NullPointerException("OBJECT IS NULL");
            }
            return entityManager.merge(t);
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, e.getMessage());
        }
    }

    @Override
    public void saveOrUpdateAll(List<T> tList) {
        try {
            if (tList == null) {
                throw new NullPointerException("OBJECT IS NULL");
            }
            tList.forEach(entityManager::merge);
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            if (id == null) {
                throw new NullPointerException("ID IS NULL");
            }
            T t = entityManager.find(entityClass, id);
            entityManager.remove(t);
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        try {
            entityManager
                    .createQuery("select t from " + entityClass.getCanonicalName() + " t ", entityClass)
                    .getResultList()
                    .stream()
                    .forEach(t -> entityManager.remove(t));
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, e.getMessage());
        }
    }

    @Override
    public Optional<T> findById(Long id) {
        try {
            return Optional.of(entityManager.find(entityClass, id));
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, e.getMessage());
        }
    }

    @Override
    public List<T> findAll() {
        try {
            return entityManager
                    .createQuery("select t from " + entityClass.getCanonicalName() + " t ", entityClass)
                    .getResultList();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, e.getMessage());
        }
    }
}
