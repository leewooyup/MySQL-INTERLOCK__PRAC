package app.frame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConnectionPool {
	private List<Connection> connectionPool;
	private List<Connection> usedConnections = new ArrayList<>();
	private static int INITIAL_POOL_SIZE = 3;
	static ResourceBundle rb;
	
	static {
		rb = null;
		rb = ResourceBundle.getBundle("mysql", Locale.KOREA);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ConnectionPool(List<Connection> connectionPool) {
		this.connectionPool = connectionPool;
	}
	
	private static Connection createConnection(String url, String user, String password) throws SQLException { //Connection객체 create
		return DriverManager.getConnection(url, user, password);
	}
	
	public static ConnectionPool create() throws SQLException { //connectionPool create
		String url = rb.getString("url");
		String user = rb.getString("user");
		String password = rb.getString("password");
		
		List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
		for(int i = 0; i < INITIAL_POOL_SIZE; i++) { //POOL_SIZE만큼의 Connection 객체를 미리 만들어 놓는다.
			pool.add(createConnection(url, user, password));
		}
		return new ConnectionPool(pool);//connectionPool 멤버필드 초기화
	}
	
	public Connection getConnection() {
		Connection connection = connectionPool.remove(connectionPool.size()- 1); //사용되고 있는 Connection객체를 connectionPool에서 빼주고,
		usedConnections.add(connection); //사용되고 있는 Connection 객체를 더해준다.
		return connection; //사용되고 있는 Connetion객체를 반환
	}
	
	public boolean releaseConnection(Connection connection) {
		connectionPool.add(connection);//사용된 Connection객체를 connectionPool에 반환
		return usedConnections.remove(connection);
	}
	
	public int getSize() { //전체 Connection객체의 수
		return connectionPool.size() + usedConnections.size();
	}
	
	public int getUseSize() { //사용되고 있지 않은 Connection객체의 수
		return connectionPool.size();
	}
}
