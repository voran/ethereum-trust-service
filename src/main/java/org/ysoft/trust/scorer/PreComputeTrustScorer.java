package org.ysoft.trust.scorer;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PreComputeTrustScorer implements TrustScorer {
	final Map<String, Set<String>> recipients = new HashMap<>();
	final Map<String, Set<String>> sources = new HashMap<>();
	final Map<String, Integer> scores = new HashMap<>();
	
	@Value(value="${base.max_hops:3}")
	int maxHops; 
	

	@Override
	public synchronized void addTransaction(String source, String destination) {
		if (!recipients.containsKey(source)) {
			recipients.put(source, new HashSet<>());
		}
		recipients.get(source).add(destination);
		
		if (!sources.containsKey(destination)) {
			sources.put(destination, new HashSet<>());
		}
		sources.get(destination).add(source);
		updateScore(new HashSet<>(), new HashSet<>(), source, destination, 1);
	}
	
	@Override
	public Integer getTrustScore(String source, String destination) {
		return scores.get(getCacheKey(source, destination));
	}
	
	private String getCacheKey(String source, String destination) {
		return source + ":" + destination;
	}
    
	private void updateScore(Set<String> visitedSources, Set<String> visitedDestinations, String source, String destination, int hops) {
		if (hops > maxHops) {
			return;
		}
		
		if (!source.equals(destination) && !scores.containsKey(getCacheKey(source, destination))) {
			scores.put(getCacheKey(source, destination), hops);
		}
		
		if (visitedDestinations.add(destination)) {
			for (String destinationRecipient : this.recipients.getOrDefault(destination, Collections.emptySet())) {
				updateScore(visitedSources, visitedDestinations, source, destinationRecipient, hops + 1);
			}
		}
		if (visitedDestinations.add(source)) {
			for (String sourceSource : this.sources.getOrDefault(source, Collections.emptySet())) {
				updateScore(visitedSources, visitedDestinations, sourceSource, destination, hops + 1);
			}
		}
	}
}
