package NemoNemoLogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


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
	
	
	// 로그인
	public DTO login(DTO dto) {
		DTO a = new DTO();
		getCon();
		try {
			String sql = "SELECT * FROM user_info WHERE user_id = ? and user_pw = ?";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPw());
			rs = psmt.executeQuery();
			while (rs.next()) {
				if (rs.getString(4).equals(dto.getPw())) {
					a.setNick(rs.getString(3));
					a.setUserSeq(rs.getInt(1));
					a.setUserCoin(5);
					break;
				}else {
					a.setNick(null);
				}
			}
		} catch (SQLException e) {
			System.out.println("로그인 : 데이터베이스 연결 실패");
			e.printStackTrace();
		} finally {
			getClose();
		}
		return a;
	}
	
	// 게임 난이도에 따른 게임 종류(개수) 반환
	public ArrayList<Integer> levelChoice(int level_choice) {
		ArrayList<Integer> game_seq = new ArrayList<>();
		getCon();
		try {
			String sql = "SELECT * FROM GAME_INFO WHERE game_level = ?";
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, level_choice);
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				game_seq.add(rs.getInt("game_seq"));
			}
		} catch (SQLException e) {
			System.out.println("game seq : 데이터베이스 연결 실패");
			e.printStackTrace();
		} finally {
			getClose();
		}
		return game_seq;
	}
	
	
	// 게임 난이도에 따른 게임 종류(개수) 반환
	public String gameChoice(int level, int game_select) {
		String answer = "";
		getCon();
		try {
			String sql = "SELECT * FROM (SELECT ROWNUM AS RN, game_ans FROM GAME_INFO WHERE game_level = ? order by game_seq) WHERE ROWNUM  = ?";
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, level);
			psmt.setInt(2, game_select);
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				answer = rs.getString(2);
			}
		} catch (SQLException e) {
			System.out.println("game seq : 데이터베이스 연결 실패");
			e.printStackTrace();
		} finally {
			getClose();
		}
		return answer;
	}
	
	public int updateCoin(int coin , int userSeq) {
		getCon();
		int row = 0;
		try {
			String sql = "UPDATE USER_INFO SET USER_COIN = USER_COIN + ? WHERE USER_SEQ = ? ";
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, coin);
			psmt.setInt(2, userSeq);
			row = psmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("코인 업데이트 실패");
			e.printStackTrace();
		}finally {
			getClose();
		}
		return row;
	}

	public void userGame(int userSeq ,int game_select , String time) {
		int row = 0;
		int check = 0;
		getCon();
		try {
			String sql = "SELECT GAME_SEQ FROM USER_GAME_INFO WHERE USER_SEQ = ? AND GAME_SEQ = ? ";
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, userSeq);
			psmt.setInt(2, game_select);
			check = psmt.executeUpdate();
		} catch (SQLException e1) {
			System.out.println("SQL 전송 실패");
			e1.printStackTrace();
		}finally {
			getClose();
		}
		if (check > 0) {
			try {
				String sql = "UPDATE USER_GAME_INFO SET GAME_TIME = ? WHERE USER_SEQ = ? AND GAME_SEQ = ? ";
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, time);    //  클리어 타임
				psmt.setInt(2, userSeq);
				psmt.setInt(3, game_select);
				row = psmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println("SQL 전송 실패");
				e.printStackTrace();
			}finally {
				getClose();
			}
		}else {
			try {
				String sql = "INSERT INTO USER_GAME_INFO ( USER_SEQ , GAME_SEQ , GAME_TIME , GAME_CLEAR ) VALUES ( ? , ? , ? , 1 )";
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, userSeq);
				psmt.setInt(2, game_select);
				psmt.setString(3,time);         // 클리어 타임
				row = psmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println("SQL 전송 실패");
				e.printStackTrace();
			}finally {
				getClose();
			}
		}
	}
}

