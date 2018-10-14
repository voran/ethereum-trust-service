package org.ysoft.trust.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ysoft.trust.controller.response.ScorerResponse;
import org.ysoft.trust.scorer.PreComputeTrustScorer;


/**
 *
 * @author yavor
 */
@Controller
@RequestMapping("/trustLevels")
public class TrustLevelsController {
    @Autowired private PreComputeTrustScorer scorer;
    


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public @ResponseBody ScorerResponse getById(HttpSession session, 
    		@RequestParam("sourceAddress") String sourceAddress, @RequestParam("destinationAddress") String destinationAddress) {
    	Integer score = scorer.getTrustScore(sourceAddress, destinationAddress);
    	return new ScorerResponse(score);
    }


    void setTrustScorer(PreComputeTrustScorer scorer) {
        this.scorer = scorer;
    }
}