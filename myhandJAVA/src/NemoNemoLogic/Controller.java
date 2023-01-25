package NemoNemoLogic;

public class Controller {
	DAO dao = new DAO();
	int row = 0;
	
	
	public void join(DTO dto) { // 회원가입
		row = dao.join(dto);
		if(row > 0) {
			System.out.println("회원가입 성공!");
		}else {
			System.out.println("아이디 또는 닉네임이 중복되었습니다.");
		}
		
	}

}
