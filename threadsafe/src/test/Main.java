package test;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.CountDownLatch;

public class Main {

	private static final int THREAD_LOOP = 3;
	private static final int THREAD_COUNT = 1000;

	public static void main(String[] args) {
		
		int a = 1;
		System.out.println(a++);
		System.out.println(a);
		
		
		User user = new User("Junichi.Kato");
		CountDownLatch startLatch = new CountDownLatch(1);
		Collection<Thread> threads = new HashSet<Thread>();
		// スレッドの準備
		for (int i = 1; i <= THREAD_COUNT; i++) {
			Thread thread = new Thread(new ThreadAccess(startLatch, user, THREAD_LOOP));
			threads.add(thread);
			thread.start();
		}
		// 足並み揃えてゴー。
		startLatch.countDown();
		try {
			// みんなが終わるのを待つ
			for (Thread thread : threads) {
				thread.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 答え合わせ
		

	}

	public static class ThreadAccess implements Runnable {

		private User user;
		private CountDownLatch startLatch;
		private int loopCount;

		public ThreadAccess(CountDownLatch startLatch, User user, int loopCount) {
			this.startLatch = startLatch;
			this.user = user;
			this.loopCount = loopCount;
		}

		@Override
		public void run() {
			try {
				startLatch.await();
				for (int i = 1; i <= loopCount; i++) {
					
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
