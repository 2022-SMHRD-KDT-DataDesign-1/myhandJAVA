package NemoNemoLogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO {
	
	Connection conn = null;
	PreparedStatement psmt = null;
	ResultSet rs = null;
	
	public int join(DTO dto) {
		getCon();
		int row = 0;
		try {
			String sql = "INSERT INTO USER_INFO( USER_SEQ , USER_ID , USER_PW , USER_NICK ) VALUES ( USER_SEQ.NEXTVAL , ? , ? , ? )";
			psmt = conn.prepareStatement(sql);
			
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPw());
			psmt.setString(3, dto.getNick());
			
			row = psmt.executeUpdate();
			
			
		} catch (SQLException e) {
			System.out.println("SQL 전송 실패");
			e.printStackTrace();
		}finally {
			getClose();
		}
		return row;
	}
	
	public void getCon() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@gjaischool-b.ddns.net:1525:xe";
			String user = "campus_d_0120_4";
			String password = "smhrd4";
			
			conn = DriverManager.getConnection(url, user, password);
			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("데이터 베이스 연동 실패");
			e.printStackTrace();
		}
	}
	public void getClose() {
		try {
			if(rs != null) {
				rs.close();
			}
			if(psmt != null) {
				psmt.close();							
			}
			if(conn != null) {
				conn.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

