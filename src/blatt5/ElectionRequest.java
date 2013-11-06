package blatt5;

import java.util.UUID;

import disy.Message;
import disy.Process;

public class ElectionRequest extends Message {
	private final UUID uuid;

	protected ElectionRequest(Process sender, UUID uuid) {
		super(sender);
		this.uuid = uuid;
	}

	public UUID getUuid() {
		return uuid;
	}

	@Override
	public void getProcessedBy(Process process) {
		((BullyProcess) process).process(this);

	}

}
