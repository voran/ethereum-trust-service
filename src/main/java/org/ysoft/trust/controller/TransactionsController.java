package org.ysoft.trust.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ysoft.trust.controller.request.AddTransactionRequest;
import org.ysoft.trust.scorer.PreComputeTrustScorer;



/**
 *
 * @author yavor
 */
@Controller
@RequestMapping("/transactions")
public class TransactionsController {
    @Autowired private PreComputeTrustScorer scorer;
    

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody void create(HttpSession session, @ModelAttribute AddTransactionRequest request) {
    	scorer.addTransaction(request.getSourceAddress(), request.getDestinationAddress());
    }


    void setTrustScorer(PreComputeTrustScorer scorer) {
        this.scorer = scorer;
    }
}