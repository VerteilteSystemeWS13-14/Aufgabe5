package blatt5;

import disy.Message;
import disy.Process;

public class ElectionTrigger extends Message {

	protected ElectionTrigger() {
		super(null);
	}

	@Override
	public void getProcessedBy(Process process) {
		((BullyProcess) process).process(this);

	}

}
