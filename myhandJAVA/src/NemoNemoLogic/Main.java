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
				if (isCheck) {
					System.out.println("==========난이도 선택==========");
					System.out.println("[1] 5X5  [2] 10X10");
					System.out.println("============================");
					int level_choice = sc.nextInt();
					
					ct.levelChoice(level_choice);
					
					System.out.println("플레이할 그림을 선택하세요");
					int game_select = sc.nextInt();
					ct.gameChoice(game_select);
					
				} else {
					continue;
				}
			}else if (choice == 3) {
				System.out.println("게임을 종료합니다");
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
	
	
	// welcome
		public static void welcome() {
			System.out.println();
			System.out.println("━━━━⊱⋆⊰━━━━━━━━⊱⋆⊰━━━━━━━━⊱⋆⊰━━━━━━━━⊱⋆⊰━━━━━━━━⊱⋆⊰━━━━━━━━⊱⋆⊰━━━━");
			System.out.println(" \t\t                         ___                                           \n"
					+ "  \t\t                        /\\_ \\                                          \n"
					+ " \t\t    __  __  __     __   \\//\\ \\      ___     ___     ___ ___       __   \n"
					+ " \t\t   /\\ \\/\\ \\/\\ \\  /'__`\\   \\ \\ \\    /'___\\  / __`\\ /' __` __`\\   /'__`\\ \n"
					+ " \t\t   \\ \\ \\_/ \\_/ \\/\\  __/    \\_\\ \\_ /\\ \\__/ /\\ \\L\\ \\/\\ \\/\\ \\/\\ \\ /\\  __/ \n"
					+ "  \t\t   \\ \\___x___/'\\ \\____\\   /\\____\\\\ \\____\\\\ \\____/\\ \\_\\ \\_\\ \\_\\\\ \\____\\\n"
					+ "   \t\t   \\/__//__/   \\/____/   \\/____/ \\/____/ \\/___/  \\/_/\\/_/\\/_/ \\/____/");
			System.out.println();
			sleep();
			System.out.println("\t __  __                                   __  __                                  \n"
					+ "\t/\\ \\/\\ \\                                 /\\ \\/\\ \\                                 \n"
					+ "\t\\ \\ `\\\\ \\      __     ___ ___      ___   \\ \\ `\\\\ \\      __     ___ ___      ___   \n"
					+ "\t \\ \\ , ` \\   /'__`\\ /' __` __`\\   / __`\\  \\ \\ , ` \\   /'__`\\ /' __` __`\\   / __`\\ \n"
					+ "\t  \\ \\ \\`\\ \\ /\\  __/ /\\ \\/\\ \\/\\ \\ /\\ \\L\\ \\  \\ \\ \\`\\ \\ /\\  __/ /\\ \\/\\ \\/\\ \\ /\\ \\L\\ \\\n"
					+ "\t   \\ \\_\\ \\_\\\\ \\____\\\\ \\_\\ \\_\\ \\_\\\\ \\____/   \\ \\_\\ \\_\\\\ \\____\\\\ \\_\\ \\_\\ \\_\\\\ \\____/\n"
					+ "\t    \\/_/\\/_/ \\/____/ \\/_/\\/_/\\/_/ \\/___/     \\/_/\\/_/ \\/____/ \\/_/\\/_/\\/_/ \\/___/ \n"
					+ "");
			System.out.println();
			sleep();
			System.out.println("\t\t\t           __                                                    \n"
					+ "\t\t\t          /\\ \\                          __                       \n"
					+ "\t\t\t          \\ \\ \\        ___      __     /\\_\\     ___              \n"
					+ "\t\t\t           \\ \\ \\  __  / __`\\  /'_ `\\   \\/\\ \\   /'___\\            \n"
					+ "\t\t\t            \\ \\ \\L\\ \\/\\ \\L\\ \\/\\ \\L\\ \\   \\ \\ \\ /\\ \\__/            \n"
					+ "\t\t\t             \\ \\____/\\ \\____/\\ \\____ \\   \\ \\_\\\\ \\____\\           \n"
					+ "\t\t\t              \\/___/  \\/___/  \\/___L\\ \\   \\/_/ \\/____/           \n"
					+ "\t\t\t                                /\\____/                          \n"
					+ "\t\t\t                                \\_/__/      ");
			System.out.println();
			System.out.println("━━━━⊱⋆⊰━━━━━━━━⊱⋆⊰━━━━━━━━⊱⋆⊰━━━━━━━━⊱⋆⊰━━━━━━━━⊱⋆⊰━━━━━━━━⊱⋆⊰━━━━");
			sleep();
			System.out.println();
			
			
			System.out.println();
			sleep();
			
		}
		
	// sleep 1초
	public static void sleep() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	

	
}
