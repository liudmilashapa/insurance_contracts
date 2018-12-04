package dao;

public interface IDao<T> {

    void create(T entity);

    T read(long id);

    void update(T entity);

    void delete(long id);
}
