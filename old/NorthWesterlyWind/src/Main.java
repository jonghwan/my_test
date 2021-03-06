import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

// https://www.acmicpc.net/problem/5419
public class Main {
	static Island island[];
	static int tree[];
	static long total;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		System.setIn(new FileInputStream("input"));
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		StringTokenizer token;
		
		int TC = Integer.parseInt(br.readLine());
		
		while (TC-- > 0) {
			int N = Integer.parseInt(br.readLine());
			island = new Island[N];
			total = 0;
			
			for (int i = 0; i<N; i++) {
				token = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(token.nextToken());
				int y = Integer.parseInt(token.nextToken());
				island[i] = new Island(x, y);
			}
			
			Arrays.sort(island, new Comparator<Island>() {
				@Override
				public int compare(Island o1, Island o2) {
					if (o1.y < o2.y) {
						return -1;
					} else if (o1.y > o2.y) {
						return 1;
					} else {
						return 0;
					}
				}
			});
			//����
			int idx = 0;
			int maxY = 0;
			int tempIDX = island[0].y;
			for (int i = 0; i<N; i++) {
				if (tempIDX == island[i].y) {
					island[i].y = idx;
				} else {
					tempIDX = island[i].y;
					island[i].y = ++idx;
					maxY++;
				}
			}		

			Arrays.sort(island, new Comparator<Island>() {
				@Override
				public int compare(Island o1, Island o2) {
					if (o1.x > o2.x) {
						return -1;
					} else if (o1.x < o2.x) {
						return 1;
					} else {
						if (o1.y > o2.y) {
							return 1;
						} else {
							return -1;
						}
					}
				}
			});			
			
			
			int start = 0;
			int end = maxY;
			tree = new int[(maxY+1)*4];
			
			for (int i = 0; i<N; i++) {
				total += getSum(1, 0, island[i].y, start, end);
				update(1, island[i].y, start, end);
			}
			System.out.println(total);
		}
	}
	private static int getSum(int idx, int left, int right, int start, int end) {
		if (right < start || left > end) {
			return 0;
		}
		
		if (left <= start && right >= end) {
			return tree[idx];
		}
		
		return getSum(idx*2, left, right, start, (start+end)/2) +
				    getSum(idx*2+1, left, right, (start+end)/2+1, end);
	}
	private static int update(int idx, int targetIDX, int start, int end) {
		if (start > targetIDX || targetIDX > end) {
			return tree[idx];
		}
		
		if (start == end) {
			tree[idx]++;
			return tree[idx];
		}
		
		tree[idx] = update(idx*2, targetIDX, start, (start+end)/2) + update(idx*2+1, targetIDX, (start+end)/2+1, end);  
		return tree[idx];
	}
}

class Island {
	int x;
	int y;
	public Island(int x, int y) {
		this.x = x;
		this.y = y;
	}
}