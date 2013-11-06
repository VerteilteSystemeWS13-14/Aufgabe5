package blatt5;

import java.util.concurrent.TimeUnit;

import disy.DistributedSystem;
import disy.Process;

public class BullyDistributedSystem extends DistributedSystem {
	BullyDistributedSystem(int numProcesses) {
		super(numProcesses);
	}

	@Override
	protected Process newProcess(int id) {
		return new BullyProcess(id);
	}

	@Override
	protected void run() {
		super.run();
		getProcess(6).setActive(false);
		getProcess(8).setActive(false);
		getProcess(9).setActive(false);
		getProcess(0).receiveMessage(new ElectionTrigger());
	}

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		BullyDistributedSystem disy = new BullyDistributedSystem(10);
		disy.run();
		TimeUnit.SECONDS.sleep(10);
		disy.shutdown();
	}

}
