package org.ysoft.trust.scorer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.ysoft.trust.scorer.PreComputeTrustScorer;


public class PreComputeTrustScorerTest {
	private PreComputeTrustScorer scorer;
	
	@Before
	public void setup() {
		scorer = new PreComputeTrustScorer();
		scorer.maxHops = 3;
	}
	
	@Test
	public void testAddTransaction() {
		/* Network:
		 * A <--> B --> C --> D --> E
		 *        ^           |
		 *        -------------
		 * 
		 */
		scorer.addTransaction("A", "B");
		scorer.addTransaction("B", "A");
		scorer.addTransaction("B", "C");
		scorer.addTransaction("C", "D");
		scorer.addTransaction("D", "B");
		scorer.addTransaction("D", "E");
		
		System.out.println(scorer.recipients);
		System.out.println(scorer.sources);
		
			
		assertEquals(new HashSet<>(Arrays.asList("B")), scorer.sources.get("A"));
		assertEquals(new HashSet<>(Arrays.asList("A", "D")), scorer.sources.get("B"));
		assertEquals(new HashSet<>(Arrays.asList("B")), scorer.sources.get("C"));
		assertEquals(new HashSet<>(Arrays.asList("C")), scorer.sources.get("D"));
		assertEquals(new HashSet<>(Arrays.asList("D")), scorer.sources.get("E"));
		
		assertEquals(new HashSet<>(Arrays.asList("B")), scorer.recipients.get("A"));
		assertEquals(new HashSet<>(Arrays.asList("A", "C")), scorer.recipients.get("B"));
		assertEquals(new HashSet<>(Arrays.asList("D")), scorer.recipients.get("C"));
		assertEquals(new HashSet<>(Arrays.asList("B", "E")), scorer.recipients.get("D"));
		assertNull(scorer.recipients.get("E"));
		
		assertNull(scorer.scores.get("A:A"));
		
		System.out.println(scorer.scores);
		assertEquals(1, scorer.scores.get("A:B").intValue());
		assertEquals(1, scorer.scores.get("B:A").intValue());
		assertEquals(1, scorer.scores.get("D:B").intValue());
		
		assertEquals(2, scorer.scores.get("A:C").intValue());
		assertEquals(2, scorer.scores.get("B:D").intValue());
		assertEquals(2, scorer.scores.get("D:A").intValue());
		assertEquals(2, scorer.scores.get("C:E").intValue());
		
		assertEquals(3, scorer.scores.get("A:D").intValue());
		assertEquals(3, scorer.scores.get("B:E").intValue());
		
		assertNull(scorer.scores.get("A:E"));
	}
	

	@Test
	public void testGetScore() {
		scorer.scores.put("A:B", 1);
		assertEquals(1, scorer.getTrustScore("A", "B").intValue());
	}
}
