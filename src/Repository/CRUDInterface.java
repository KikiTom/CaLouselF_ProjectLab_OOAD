package Repository;

import java.util.List;

public interface CRUDInterface<T> {
	List<T> getAll();
	T getById(int id);
	boolean create(T entity);
	boolean update(int id, T entity);
	boolean delete(int id);
}