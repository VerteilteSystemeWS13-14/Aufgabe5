package blatt5;

import disy.Message;
import disy.Process;

public class ElectionResponse extends Message {

	protected ElectionResponse(Process sender) {
		super(sender);
	}

	@Override
	public void getProcessedBy(Process process) {
		((BullyProcess) process).process(this);

	}

}
