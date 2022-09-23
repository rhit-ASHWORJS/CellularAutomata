
public class Main {
	public static void main(String[] args) throws InterruptedException {
		// cool big one

		// set boxWidth=2 and frameGens=400
//		int numGens = 400;
//		int[] start = new int[1000];
//		start[500]=1;
//		CA thisOne = new CA(start, 126);
//		thisOne.frameInit();
//		for(int i=0; i<numGens; i++)
//		{
//			thisOne.step();
//			thisOne.updateFrame();
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//-------------------------------------------------------------------------------------
		int[] start = new int[20];
		start[10] = 1;
//		CA thisOne = new CA(start, 30);
//		System.out.println(thisOne.calculateTransientDuration());
//		thisOne.frameInit();
//		thisOne.updateFrame();

		int[] durations = new int[256];

		for (int runs = 0; runs < 100; runs++) {
			for (int rule = 0; rule < 256; rule++) {
				CA thisOne = new CA(20, rule);
				int duration = thisOne.calculateTransientDuration();
				durations[rule] += duration;
			}
		}
		for(int r=0; r<256; r++)
		{
			durations[r] /= 100;
		}

		// print out x highest
		int max = 0;
		int num = 1;
		for (int i = 0; i < 15; i++) {
			for (int r = 0; r < 256; r++) {
				if (durations[r] > durations[max]) {
					max = r;
				}
			}
			CA newOne = new CA(20, max);
			System.out.println(num + ":Rule " + max + " has duration " + durations[max] + " and lambda " + newOne.getLambda());
			num++;
			durations[max] = 0;
		}
//-----------------------------------------------------------------------------------------
//		GOL thisone = new GOL(50,50);
//		thisone.frameInit();
//		for(int i=0; i<10000; i++)
//		{
//			thisone.step();
//			thisone.updateFrame();
//			Thread.sleep(100);
////			System.out.println("a");
//		}
	}
}
