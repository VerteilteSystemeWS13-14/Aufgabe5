package blatt5;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import disy.Process;

public class BullyProcess extends Process {
	
	private volatile boolean coordinator;
	private Set<UUID> requestUUIDs;
	
	protected BullyProcess(int id) {
		super(id);
		coordinator = true;
		requestUUIDs = new HashSet<UUID>();
	}
	
	/**
	 * Trigger
	 * @param electionTrigger
	 */
	public void process(ElectionTrigger electionTrigger) {		
		multicastBullyRequest(new ElectionRequest(this, UUID.randomUUID()));
	}
	
	/**
	 * Request
	 * @param electionRequest
	 */
	public void process(ElectionRequest electionRequest) {		
		ElectionResponse response = new ElectionResponse(this);
		electionRequest.getSender().receiveMessage(response);
		
		synchronized(requestUUIDs) {
			if (requestUUIDs.contains(electionRequest.getUuid()))
				return;
		
			requestUUIDs.add(electionRequest.getUuid());
		}		
		
		multicastBullyRequest(new ElectionRequest(this, electionRequest.getUuid()));
	}

	/**
	 * Response
	 * @param electionResponse
	 */
	public void process(ElectionResponse electionResponse) {
		coordinator = false;
	}
	
	private void multicastBullyRequest(ElectionRequest electionRequest) {
		for (Process process : destinations.values()) {
			if (process.getID() > this.getID())
				process.receiveMessage(electionRequest);
		}
		
		checkCoordinator();
	}
	
	private void checkCoordinator() {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if (coordinator)
			System.out.println("Process " + getID() + " has been elected.");		
	}

}
