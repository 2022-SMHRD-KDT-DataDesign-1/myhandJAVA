package NemoNemoLogic;

import java.util.Random;
import java.util.Scanner;


public class Main {
	
	public static void main(String[] args) {
		
		Controller ct = new Controller();
		Scanner sc = new Scanner(System.in);
		
		
		while(true) {
			linePrint();
			System.out.println("[1] 회원가입  [2] 로그인  [3] 종료");
			linePrint();			
			int choice = sc.nextInt();
			
			if (choice == 1) { // 회원가입 
				System.out.println("=========회원가입==========");
				System.out.print("아이디 : ");
				String id = sc.next();
				System.out.print("비밀번호 : ");
				String pw = sc.next();
				System.out.print("닉네임 : ");
				String nick = sc.next();
				DTO dto = new DTO(id,pw,nick);
				ct.join(dto);
				break;
			}else if (choice == 2) {
				System.out.println("=========로그인=========");
				// 로그인
				System.out.print("ID를 입력해주세요 >>");
				String id = sc.next();
				System.out.print("Password를 입력해주세요 >>");
				String pw = sc.next();
				DTO dto = new DTO(id, pw);
				boolean isCheck = ct.login(dto);
				while(true) {
					if (isCheck) {
						System.out.println("==========난이도 선택==========");
						System.out.println("[1] 5X5  [2] 10X10 [3] 로그아웃 ");
						System.out.println("============================");
						int level_choice = sc.nextInt();
						if(level_choice == 3) {
							System.out.println("로그아웃 되었습니다.");
							break;
						}
						ct.levelChoice(level_choice);
						
						System.out.println("플레이할 그림을 선택하세요");
						int game_select = sc.nextInt();
						ct.gameChoice(game_select);
						
					} else {
						System.out.println("번호를 다시 입력 해 주세요");
					}
				}
			}else if (choice == 3) {
				System.out.println("게임을 종료합니다.");
				break;
			}else {
				System.out.println("올바른 숫자를 입력해주세요");
			}
		}
	}
	
	// 구분선 출력
	public static void linePrint() {
		System.out.println("=======================================");
	}
	
	

	
}
