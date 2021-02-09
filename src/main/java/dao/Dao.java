package dao;

import java.sql.SQLException;
import java.util.HashMap;


public interface Dao <T> {
	public HashMap<Integer, T> findAll();
	public void insert(T t) throws SQLException;
	public boolean delete (int id);
	public T findById(int id);

}
