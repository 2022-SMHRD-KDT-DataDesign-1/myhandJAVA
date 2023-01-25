package NemoNemoLogic;

import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		
		
		Scanner sc = new Scanner(System.in);
		boolean isCleared = false;
		
		
		while(true) {
			System.out.println("=======================================");
			System.out.println("[1] 회원가입  [2] 로그인  [3] 종료");
			System.out.println("=======================================");
			
			int choice = sc.nextInt();
			
			if (choice == 1) {
				System.out.println("=========회원가입==========");
				break;
			}else if (choice == 2) {
				System.out.println("=========로그인=========");
				levelChoice();
				break;
			}else if (choice == 3) {
				System.out.println("게임을 종료합니다");
				break;
			}else {
				System.out.println("올바른 숫자를 입력해주세요");
			}
		}
	}
	
	public static void levelChoice() {
		Scanner sc = new Scanner(System.in);
		boolean isCleared = false;
		
		while(true) {
			System.out.println("==========난이도 선택==========");
			System.out.println("[1] 5X5  [2] 10X10");
			System.out.println("============================");
			
			int level_choice = sc.nextInt();
			
			if (level_choice == 1) {
				System.out.println("=====5X5=====");
				System.out.println("[1] [2] [3] [4] [5]");
				System.out.println("============");
				System.out.println("플레이할 그림을 선택하세요");
				int game_select = sc.nextInt();
				if (game_select == 1) {
					if (isCleared) {
						System.out.println("=========랭크========");
						System.out.println("[1] 누구누구 어쩌고 저쩌고 1.24초");
						System.out.println("[2] 누구누구 어쩌고 저쩌고 4.46초");
						System.out.println("[3] 누구누구 어쩌고 저쩌고 6.01초");
					}
					
					System.out.println("====1====");
					int life = 0;
					
					System.out.println("================GAME================");
					System.out.println(" xxx 님                코인 : 5개");
					System.out.println("                      힌트 : 0개");
					System.out.println("------------------------------------");
					
					System.out.println("GAME");
					
					System.out.println("------------------------------------");
					System.out.println("                            ♥♥♡     ");
					
					if (life == 0) {
						System.out.println("=============================");
						System.out.println("♡ 목숨이 0이 되었어요!");
						System.out.println("목숨을 구입해서 계속 플레이 하시겠습니까?");
						System.out.println("[1] 네! 계속할래요  [2] 아니요 ㅠㅠ 포기하겠습니다");
						System.out.println("=============================");
						int yesOrNo = sc.nextInt();
						
						if (yesOrNo == 1) {
							System.out.println("코인 3개를 사용 해 목숨뽑기를 진행합니다");
							
							
							System.out.println("[1] 계속하기  [2] 돌아가기");
							
							
						}else if (yesOrNo == 2) {
							System.out.println("포기하셨습니다");
						}
						
					}
				}
				break;
			}else if (level_choice == 2) {
				System.out.println("=====10X10=====");
				System.out.println("[1] [2] [3] [4] [5]");
				break;
			}else {
				System.out.println("올바른 숫자를 입력하세요");
			}
			
		}
		
		
	}
}
