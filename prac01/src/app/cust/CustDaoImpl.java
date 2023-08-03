package app.cust;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import app.dto.Cust;
import app.frame.ConnectionPool;
import app.frame.DaoFrame;
import app.frame.SQL;

public class CustDaoImpl implements DaoFrame<String, Cust> {
	Logger log = Logger.getLogger("CustDao");//syso대신 logger로 확인
	ConnectionPool pool;
	
	public CustDaoImpl() {
		try {
			pool = ConnectionPool.create();//connectionPool create
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int insert(Cust v) throws Exception {
		int result = 0;
		Connection con = pool.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(SQL.custInsert);
			pstmt.setString(1, v.getId());
			pstmt.setString(2, v.getPwd());
			pstmt.setString(3, v.getName());
			
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			log.info(e.getMessage());
			throw new Exception("아이디 중복에러.");
		} finally {
			DaoFrame.closePstmt(pstmt);
			pool.releaseConnection(con); //Connection객체 반환
		}
		return result;
	}

	@Override
	public int update(Cust v) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String k) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Cust select(String k) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cust> select() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
