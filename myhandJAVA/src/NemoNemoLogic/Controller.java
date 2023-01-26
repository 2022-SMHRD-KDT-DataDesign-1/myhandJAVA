package NemoNemoLogic;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Controller {
	Scanner sc = new Scanner(System.in);
	DAO dao = new DAO();
	int row = 0;
	static String userNick = "";
	static int level = 0;
	static int userSeq = 0;

	public void join(DTO dto) { // 회원가입
		row = dao.join(dto);
		if (row > 0) {
			System.out.println("회원가입 성공!");
		} else {
			System.out.println("아이디 또는 닉네임이 중복되었습니다.");
		}

	}

	// 로그인
	public boolean login(DTO dto) {
		boolean isCheck = false;
		DTO res = dao.login(dto);
//		boolean res = dao.login(dto);
		userSeq = res.getUserSeq();
		if (res.getNick() != null) {
			userNick = res.getNick();
			System.out.println("로그인 완료");
			System.out.println(res.getNick() + "님 반갑습니다.");
			return isCheck = true;
		} else {
			System.out.println("아이디나 비밀번호를 확인하세요.");
			return isCheck;
		}
	}

	// 게임 난이도 선택
	public static void levelChoice(int level_choice) {
		boolean isCleared = false;
		level = level_choice;
		DAO dao1 = new DAO();
		ArrayList<Integer> game_seq = dao1.levelChoice(level_choice);
		while (true) {
			if (level_choice == 1) {
				System.out.println("=====5X5=====");
				for (int i = 0; i < game_seq.size(); i++) {
					System.out.print("[" + game_seq.get(i) + "] ");
				}
//				System.out.println("[1] [2] [3] [4] [5]");
				System.out.println("============");
				break;
			} else if (level_choice == 2) {
				System.out.println("=====10X10=====");
				System.out.println("[1] [2] [3] [4] [5]");
				System.out.println("============");
				break;
			} else {
				System.out.println("올바른 숫자를 입력하세요");
			}
		}
	}

	// 난이도에 따른 게임 선택
	public static void gameChoice(int game_select) {
		DAO dao1 = new DAO();
		int num = 0;
		int[][] res = new int[5][5];
		// ans는 답 데이터
		String ans = dao1.gameChoice(game_select);
		if (ans == null) {
			System.out.println("게임 정보 오류");

		} else if (level == 1) { // 5*5
			num = 5;
			System.out.println(num + "x" + num);
			// 답데이터 res[][]배열에 저장
			res = arrMake(ans, num);

			// 문제 
			playGame(num, res , game_select);

		} else if (level == 2) { // 10*10
			System.out.println(num + "x" + num);
			num = 10;
		} else {
			System.out.println("올바른 숫자를 입력하세요");
		}

	}

	public static int[][] arrMake(String ans, int num) {
		int[][] res = new int[num][num];
		String[] arr = ans.split(",");

		for (int i = 0; i < res.length; i++) {
			String[] arr3 = arr[i].split("");
			for (int j = 0; j < arr.length; j++) {
				res[i][j] = Integer.parseInt(arr3[j]);
//				System.out.print(res[i][j] + " ");
			}
//			System.out.println();
		}
		return res;
	}


	// Game Start!
	public static void playGame(int num, int[][] res , int game_select) {
		Scanner sc = new Scanner(System.in);
		// 정답 체크할 변수
		int resCheck = 0;
		int userCheck = 0;
		
		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < res.length; j++) {
				System.out.print(res[i][j] + " ");
				if (res[i][j] == 1)	resCheck+=1;
			}
			System.out.println();
		}
		System.out.println(resCheck);

		// 숫자 매칭 배열
		int[][] arr = new int[num][num];
		int cnt = 1;

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				arr[i][j] = cnt;
				cnt++;
				System.out.print(arr[i][j] + "\t");
			}
			System.out.println();
		}

		// 목숨
		int count = 3;
		int[][] user = new int[num][num];

		while (count > 0) {

			System.out.println("현재 목숨 : " + count);
			for (int i = 0; i < user.length; i++) {
				for (int j = 0; j < user.length; j++) {
					if (user[i][j] == 1) {
						System.out.print("■" + " ");
					} else if (user[i][j] == 3) {
						System.out.print("X" + " ");
					} else {
						System.out.print("□" + " ");
					}
				}
				System.out.println();
			}

			System.out.print("선택을 원하면 1번, X를 원하면 2번 선택 >>");
			int oxSelect = sc.nextInt();
			if (oxSelect == 1) {
				System.out.print("숫자 선택 >>");
				int choice = sc.nextInt();

				if (res[(choice - 1) / num][(choice - 1) % num] == 1) {
					user[(choice-1) / num][(choice - 1) % num] = 1;
					userCheck++;
				} else {
					count--;
				}
			} else if (oxSelect == 2) {
				System.out.print("숫자 선택 >>");
				int choice = sc.nextInt();

				if (res[(choice - 1) / num][(choice - 1) % num] == 0) {
					user[(choice - 1) / num][(choice - 1) % num] = 3;
				} else {
					System.out.println("숫자를 확인하세요.");
					continue;
				}
			}

			System.out.println(userCheck);
			if (userCheck == resCheck) {
				int row = 0;
				DAO dao1 = new DAO();
				int coin = 0;
				System.out.println("정답");
				if(level == 1 && count == 3) {
					coin = 1;
				}else if(level == 2) {
					coin = count;
				}
				row = dao1.updateCoin(coin, userSeq);
				if(row > 0) {
					System.out.println(coin+"코인 흭득!");
				}
				row = dao1.userGame(userSeq , game_select);
				
			}

		} // while문 종료
		if (cnt == 0) {
			System.out.println("목숨이 없습니다.");
		}
	}
	

	// 이미 클리어한 게임의 경우 랭크를 먼저 보여주기 위함
	public static void isCleared() {
		Scanner sc = new Scanner(System.in);
		System.out.println("=========랭크========");
		System.out.println("[1] 누구누구 어쩌고 저쩌고 1.24초");
		System.out.println("[2] 누구누구 어쩌고 저쩌고 4.46초");
		System.out.println("[3] 누구누구 어쩌고 저쩌고 6.01초");

		System.out.println();
		System.out.println("[1] 다시 도전하기  [2] 돌아가기");
		int re_select = sc.nextInt();
		if (re_select == 1) {
			gamePlay();
		} else if (re_select == 2) {
//			levelChoice();
		}
	}

	public static void gamePlay() {
		int life = 0;
		Scanner sc = new Scanner(System.in);

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
//				gaCha();

			} else if (yesOrNo == 2) {
				System.out.println("포기하셨습니다");

			}

		}
	}

	// 목숨 뽑기 // 3코인 차감
	public static void gaCha() {
		Random rd = new Random();
		Scanner sc = new Scanner(System.in);
		System.out.println("  Λ＿Λ\n" + "（ㆍωㆍ)つ━☆*。\n" + "⊂　　 ノ 　　　.뾰\n" + "　し-Ｊ　　　°。로 *´¨)\n"
				+ "　　　　　　..　.· ´¸.·롱*´¨) ¸.·*¨)\n" + "　　　　　　　　　　(¸.·´ ( ¸.'*\n" + "");
		int num = rd.nextInt(4);
		if (num == 0) {
			System.out.println("　 　 ∧＿∧\n" + "　 　 (・ω・)\n" + "┏ーーー∪ー∪ーーー┓\n" + "┃＼　　　　 ／ ┃\n" + "┃　＼　-　／　 ┃\n"
					+ "┃　／＼＿／＼  ┃\n" + "┗ー━ー━ー━ー━ー┛\n" + "");
		} else if (num == 1) {
			System.out.println("　 　 ∧＿∧\n" + "　 　 (・ω・)\n" + "┏ーーー∪ー∪ーーー┓\n" + "┃＼　　　　 ／ ┃\n" + "┃　＼　♥　／　 ┃\n"
					+ "┃　／＼＿／＼  ┃\n" + "┗ー━ー━ー━ー━ー┛\n" + "");
		} else if (num == 2) {
			System.out.println("　 　 ∧＿∧\n" + "　 　 (・ω・)\n" + "┏ーーー∪ー∪ーーー┓\n" + "┃＼　　　　 ／ ┃\n" + "┃　＼ ♥♥　／　┃\n"
					+ "┃　／＼＿／＼  ┃\n" + "┗ー━ー━ー━ー━ー┛\n" + "");
		} else if (num == 3) {
			System.out.println("　 　 ∧＿∧\n" + "　 　 (・ω・)\n" + "┏ーーー∪ー∪ーーー┓\n" + "┃＼　　　　 ／ ┃\n" + "┃　＼ ♥♥♥／  ┃\n"
					+ "┃　／＼＿／＼  ┃\n" + "┗ー━ー━ー━ー━ー┛\n" + "");
		}
		System.out.println(num + "개의 목숨UP!");
		System.out.println();
		System.out.println("[1] 계속하기  [2] 돌아가기");
		int life_select = sc.nextInt();

		if (life_select == 1) {
			gamePlay();
		} else if (life_select == 2) {
//			levelChoice();
		}
	}

}
