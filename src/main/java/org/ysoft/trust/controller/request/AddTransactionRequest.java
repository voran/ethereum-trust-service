package org.ysoft.trust.controller.request;

public class AddTransactionRequest {
	private String sourceAddress;
	private String destinationAddress;

	public String getSourceAddress() {
		return sourceAddress;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}
	

	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	@Override
	public String toString() {
		return "AddTransactionRequest [sourceAddress=" + sourceAddress + ", destinationAddress=" + destinationAddress
				+ "]";
	}
	
	
}
