package Repository.RepositoryInterface;

public interface Update<T> {
	boolean update(int id, T entity);

}
