package org.ysoft.trust.observer;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.ysoft.trust.scorer.TrustScorer;

import rx.Subscription;

public class TransactionObserver {

	private Web3j web3j;
	private TrustScorer scorer;
	private Subscription subscription;
	
	public void init() {
		System.out.println("Started listening for transactions.");
		subscription = web3j.catchUpToLatestAndSubscribeToNewTransactionsObservable(DefaultBlockParameterName.EARLIEST).subscribe(tx -> {
        	String from = tx.getFrom();
        	String to = tx.getTo();
        	double value = tx.getValue().doubleValue();
        	String input = tx.getInput();
        	System.out.println(String.format("Received transaction %s -> %s with value %s and input %s!", from, to, value, input));
        	if (from != null && to != null && value > 0) {
        		scorer.addTransaction(from, to);
        	}
        },
		Throwable::printStackTrace);
	}
	
	
	public void destroy() {
		System.out.println("Shutting down.");
		subscription.unsubscribe();
	}


	public void setWeb3j(Web3j web3j) {
		this.web3j = web3j;
	}


	public void setScorer(TrustScorer scorer) {
		this.scorer = scorer;
	}
}
