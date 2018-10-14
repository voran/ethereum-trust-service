package org.ysoft.trust.scorer;

public interface TrustScorer {

	void addTransaction(String source, String destination);

	Integer getTrustScore(String source, String destination);

}