package samsung_sw_expert_academy;

/////////////////////////////////////////////////////////////////////////////////////////////
// 기본 제공코드는 임의 수정해도 관계 없습니다. 단, 입출력 포맷 주의
// 아래 표준 입출력 예제 필요시 참고하세요.
// 표준 입력 예제
// int a;
// double b;
// char g;
// String var;
// long AB;
// a = sc.nextInt();                           // int 변수 1개 입력받는 예제
// b = sc.nextDouble();                        // double 변수 1개 입력받는 예제
// g = sc.nextByte();                          // char 변수 1개 입력받는 예제
// var = sc.next();                            // 문자열 1개 입력받는 예제
// AB = sc.nextLong();                         // long 변수 1개 입력받는 예제
/////////////////////////////////////////////////////////////////////////////////////////////
// 표준 출력 예제
// int a = 0;                            
// double b = 1.0;               
// char g = 'b';
// String var = "ABCDEFG";
// long AB = 12345678901234567L;
//System.out.println(a);                       // int 변수 1개 출력하는 예제
//System.out.println(b); 		       						 // double 변수 1개 출력하는 예제
//System.out.println(g);		       						 // char 변수 1개 출력하는 예제
//System.out.println(var);		       				   // 문자열 1개 출력하는 예제
//System.out.println(AB);		       				     // long 변수 1개 출력하는 예제
/////////////////////////////////////////////////////////////////////////////////////////////
import java.util.Scanner;
import java.io.FileInputStream;

/*
   사용하는 클래스명이 Solution 이어야 하므로, 가급적 Solution.java 를 사용할 것을 권장합니다.
   이러한 상황에서도 동일하게 java Solution 명령으로 프로그램을 수행해볼 수 있습니다.
 */
class Solution_5656
{
	static int N;
	static int W;
	static int H;
	static int[][] original_map;
	static int[][] copied_map;
	static int answer;
	
	public static void main(String args[]) throws Exception
	{
		/*
		   아래의 메소드 호출은 앞으로 표준 입력(키보드) 대신 input.txt 파일로부터 읽어오겠다는 의미의 코드입니다.
		   여러분이 작성한 코드를 테스트 할 때, 편의를 위해서 input.txt에 입력을 저장한 후,
		   이 코드를 프로그램의 처음 부분에 추가하면 이후 입력을 수행할 때 표준 입력 대신 파일로부터 입력을 받아올 수 있습니다.
		   따라서 테스트를 수행할 때에는 아래 주석을 지우고 이 메소드를 사용하셔도 좋습니다.
		   단, 채점을 위해 코드를 제출하실 때에는 반드시 이 메소드를 지우거나 주석 처리 하셔야 합니다.
		 */
		//System.setIn(new FileInputStream("res/input.txt"));

		/*
		   표준입력 System.in 으로부터 스캐너를 만들어 데이터를 읽어옵니다.
		 */
		Scanner sc = new Scanner(System.in);
		int T;
		T=sc.nextInt();
		/*
		   여러 개의 테스트 케이스가 주어지므로, 각각을 처리합니다.
		*/

		for(int test_case = 1; test_case <= T; test_case++)
		{
			N = sc.nextInt();
			W = sc.nextInt();
			H = sc.nextInt();
			original_map = new int[H][W];
			copied_map = new int[H][W];
			answer = Integer.MAX_VALUE;
			
			for(int i=0; i<H; i++) {
				for(int j=0; j<W; j++) {
					original_map[i][j] = sc.nextInt();
				}
			}

			sol();
			System.out.println("#" + test_case + " " + answer);
		}
	}
	
	public static void sol() {
		int[] d = new int[4];
		boolean flag = false;
		
		for(d[0] = 0; d[0] < W; d[0]++) {
			for(d[1] = 0; d[1] < W; d[1]++) {
				for(d[2] = 0; d[2] < W; d[2]++) {
					for(d[3] = 0; d[3] < W; d[3]++) {
						map_copy();
						
						for(int i = 0; i < N; i++) {
							flag = bomb(d[i]);
							
							while(flag) {
								flag = chain_bomb();
							}
							
							down();
						}

						int bricks_count = get_bricks_count();
						
						if(answer > bricks_count) {
							answer = bricks_count;
						}
						
						if(answer == 0)
							return;
						
						if(N < 4)
							break;
					}
					
					if(N < 3)
						break;
				}
				
				if(N < 2)
					break;
			}
		}
	}
	
	
	public static void map_copy() {
		for(int i=0; i<H; i++) {
			for(int j=0; j<W; j++) {
				copied_map[i][j] = original_map[i][j];
			}
		}
	}
	
	public static boolean bomb(int y) {
		int x = 0;
		
		while(x < H && copied_map[x][y] == 0) {
			x++;
		}
		
		if(x >= H) {
			return false;
		}
		
		if(copied_map[x][y] == 1 ) {
			copied_map[x][y] = 0;
		} else {
			copied_map[x][y] += 10;
			return true;
		}
		
		return false;
	}
	
	public static boolean chain_bomb() {
		boolean flag = false;
		
		for(int i=0; i<H; i++) {
			for(int j=0; j<W; j++) {
				if(copied_map[i][j] > 9) {
					int range = copied_map[i][j] % 10 - 1;
					
					copied_map[i][j] = 0;
					
					for(int y= (j-range >= 0 ? j-range : 0) ; y<=(j+range < W ? j+range : W-1); y++) {
						if(copied_map[i][y] == 1) {
							copied_map[i][y] = 0;
						} else if(copied_map[i][y] > 1){
							copied_map[i][y] += 10;
							flag = true;
						}
					}
					
					for(int x= (i-range >= 0 ? i-range : 0) ; x<=(i+range < H ? i+range : H-1); x++) {
						if(copied_map[x][j] == 1) {
							copied_map[x][j] = 0;
						} else if(copied_map[x][j] > 1){
							copied_map[x][j] += 10;
							flag = true;
						}
					}
				}
			}
		}
		
		return flag;
	}
	
	public static void down() {
		for(int j=0; j<W; j++) {
			for(int i=H-1; i>0; i--) {
				if(copied_map[i][j] == 0) {
					int idx = i-1;
					
					while(idx >= 0 && copied_map[idx][j] == 0) {
						idx--;
					}
					
					if(idx < 0) {
						break;
					}
					
					copied_map[i][j] = copied_map[idx][j];
					copied_map[idx][j] = 0;
				}
			}
		}
	}
	
	public static int get_bricks_count() {
		int count = 0;
		
		for(int i=0; i<H; i++) {
			for(int j=0; j<W; j++) {
				if(copied_map[i][j] != 0) {
					count++;
				}
			}
		}
		
		return count;
	}
}