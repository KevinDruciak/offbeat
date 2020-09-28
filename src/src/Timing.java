package src;

public final class Timing {

	private long elapsedTime;
	private boolean isTicking;
	private long startTime, pauseTime, resumeTime;
	private long waitStart, waitElapsed;

	public boolean getTicking() {
		return this.isTicking;
	}

	public boolean wait(int ms) {
		waitStart = System.currentTimeMillis();
		while (waitElapsed != ms) {
			waitElapsed = System.currentTimeMillis() - waitStart;
		}
		return true;
	}

	public synchronized long getElapsedTime() {
		if (isTicking) {
			return System.currentTimeMillis() - startTime;
		} else {
			return elapsedTime;
		}
	}

	public synchronized void reset() {
		elapsedTime = 0;
		isTicking = false;
	}

	public synchronized void start() {
		//System.out.println("called");
		if (isTicking) {
			throw new IllegalStateException("already started");
		}
		isTicking = true;
		startTime = System.currentTimeMillis();
	}

	public synchronized void pause() {
		isTicking = false;
		pauseTime = System.currentTimeMillis();
	} 

	public synchronized void resume() {
		isTicking = true;
		resumeTime = System.currentTimeMillis();
		startTime = startTime + (resumeTime - pauseTime);
	}

	public synchronized void stop() {
		if (!isTicking) {
			throw new IllegalStateException("not started");
		}
		elapsedTime = System.currentTimeMillis() - startTime;
		isTicking = false;
	}
}
