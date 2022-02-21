
package com.proxiad.hangmangame.soap.consumer;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.2
 * Generated source version: 2.2
 * 
 */
@WebService(name = "HangmanGameRankingWS", targetNamespace = "http://ranking.logic.hangmangame.proxiad.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface HangmanGameRankingWS {


    /**
     * 
     * @return
     *     returns java.util.List<com.proxiad.hangmangame.soap.consumer.RankingModel>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTop10ForLastMonth", targetNamespace = "http://ranking.logic.hangmangame.proxiad.com/", className = "com.proxiad.hangmangame.soap.consumer.GetTop10ForLastMonth")
    @ResponseWrapper(localName = "getTop10ForLastMonthResponse", targetNamespace = "http://ranking.logic.hangmangame.proxiad.com/", className = "com.proxiad.hangmangame.soap.consumer.GetTop10ForLastMonthResponse")
    public List<RankingModel> getTop10ForLastMonth();

    /**
     * 
     * @return
     *     returns java.util.List<com.proxiad.hangmangame.soap.consumer.RankingModel>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTop10Ever", targetNamespace = "http://ranking.logic.hangmangame.proxiad.com/", className = "com.proxiad.hangmangame.soap.consumer.GetTop10Ever")
    @ResponseWrapper(localName = "getTop10EverResponse", targetNamespace = "http://ranking.logic.hangmangame.proxiad.com/", className = "com.proxiad.hangmangame.soap.consumer.GetTop10EverResponse")
    public List<RankingModel> getTop10Ever();

}
