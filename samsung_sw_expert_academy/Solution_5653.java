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
import java.util.Comparator;
import java.util.PriorityQueue;
/*
   사용하는 클래스명이 Solution 이어야 하므로, 가급적 Solution.java 를 사용할 것을 권장합니다.
   이러한 상황에서도 동일하게 java Solution 명령으로 프로그램을 수행해볼 수 있습니다.
 */

class Cell {
	int i, j;
	int life_point;
	int creation_time;
	int spread_time;
	int extinction_time;
}

class Solution_5653
{
	static final int SIZE = 400;
	static int N, M, K;
	static PriorityQueue<Cell> cells_by_extinction;
	static PriorityQueue<Cell> cells_by_spread;
	static boolean[][] map;
	
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
			M = sc.nextInt();
			K = sc.nextInt();
			map = new boolean[SIZE][SIZE];
			cells_by_extinction = new PriorityQueue<>(new Comparator<Cell>() {
				@Override
				public int compare(Cell c1, Cell c2) {
					if(c1.extinction_time == c2.extinction_time) {
						if(c1.spread_time == c2.spread_time) {
							return c2.life_point - c1.life_point;
						}
 						return c1.spread_time - c2.spread_time;
					}
					return c1.extinction_time - c2.extinction_time;
				}
			});
			
			cells_by_spread= new PriorityQueue<>(new Comparator<Cell>() {
				@Override
				public int compare(Cell c1, Cell c2) {
					if(c1.spread_time == c2.spread_time) {
						return c2.life_point - c1.life_point;
					}
					return c1.spread_time - c2.spread_time;
				}
			});
			
			for(int i=0; i<N; i++) {
				for(int j=0; j<M; j++) {
					int lp = sc.nextInt();
					
					if(lp != 0) {
						map[SIZE/2+i][SIZE/2+j] = true;
						Cell c = new Cell();
						c.i = SIZE/2+i;
						c.j = SIZE/2+j;
						c.life_point = lp;
						c.creation_time = 0;
						c.extinction_time = lp*2;
						c.spread_time = lp+1;
						cells_by_extinction.add(c);
						cells_by_spread.add(c);
					}
				}
			}
			
			solve();
			System.out.println("#" + test_case + " " + cells_by_extinction.size());
		}
	}
	
	public static void solve() {
		for(int t=1; t<=K; t++) {
			if(cells_by_extinction.isEmpty()) {
				return;
			}
			
			while(!cells_by_spread.isEmpty() && cells_by_spread.peek().spread_time == t) {
				Cell tmp = cells_by_spread.poll();
				if(tmp.spread_time == t) {
					spread(tmp, t);
				}
			}
			
			while(!cells_by_extinction.isEmpty() && (cells_by_extinction.peek().extinction_time == t)) {
				cells_by_extinction.poll();
			}
		}
	}
	
	public static void spread(Cell c, int t) {
		int[] x_offset = {-1, 0, 1, 0};
		int[] y_offset = {0, -1, 0, 1};
		
		for(int i=0; i<4; i++) {
			int xidx = c.i+x_offset[i];
			int yidx = c.j+y_offset[i];
			
			if(!map[xidx][yidx]) {
				map[xidx][yidx] = true;
				
				Cell tmp = new Cell();
				tmp.i = xidx;
				tmp.j = yidx;
				tmp.life_point = c.life_point;
				tmp.creation_time = t;
				tmp.extinction_time = t+(c.life_point*2);
				tmp.spread_time = t+c.life_point+1;

				cells_by_spread.add(tmp);
				cells_by_extinction.add(tmp);
			}
		}
	}
}