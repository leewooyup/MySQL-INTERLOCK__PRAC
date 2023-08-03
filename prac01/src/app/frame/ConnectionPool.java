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
	
	private static Connection createConnection(String url, String user, String password) throws SQLException { //Connection��ü create
		return DriverManager.getConnection(url, user, password);
	}
	
	public static ConnectionPool create() throws SQLException { //connectionPool create
		String url = rb.getString("url");
		String user = rb.getString("user");
		String password = rb.getString("password");
		
		List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
		for(int i = 0; i < INITIAL_POOL_SIZE; i++) { //POOL_SIZE��ŭ�� Connection ��ü�� �̸� ����� ���´�.
			pool.add(createConnection(url, user, password));
		}
		return new ConnectionPool(pool);//connectionPool ����ʵ� �ʱ�ȭ
	}
	
	public Connection getConnection() {
		Connection connection = connectionPool.remove(connectionPool.size()- 1); //���ǰ� �ִ� Connection��ü�� connectionPool���� ���ְ�,
		usedConnections.add(connection); //���ǰ� �ִ� Connection ��ü�� �����ش�.
		return connection; //���ǰ� �ִ� Connetion��ü�� ��ȯ
	}
	
	public boolean releaseConnection(Connection connection) {
		connectionPool.add(connection);//���� Connection��ü�� connectionPool�� ��ȯ
		return usedConnections.remove(connection);
	}
	
	public int getSize() { //��ü Connection��ü�� ��
		return connectionPool.size() + usedConnections.size();
	}
	
	public int getUseSize() { //���ǰ� ���� ���� Connection��ü�� ��
		return connectionPool.size();
	}
}
