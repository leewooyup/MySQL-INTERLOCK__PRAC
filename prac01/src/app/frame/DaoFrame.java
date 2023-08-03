package app.frame;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public interface DaoFrame<K, V> {
	public int insert(V v) throws Exception;
	public int update(V v) throws Exception;
	public int delete(K k) throws Exception;
	public V select(K k) throws Exception;
	public List<V> select() throws Exception;
	
	public static void closePstmt(PreparedStatement pstmt) throws Exception {
		if(pstmt != null) {
			pstmt.close();
		}
	}
	
	public static void closeRset(ResultSet rset) throws Exception {
		if(rset != null) {
			rset.close();
		}
	}
}
