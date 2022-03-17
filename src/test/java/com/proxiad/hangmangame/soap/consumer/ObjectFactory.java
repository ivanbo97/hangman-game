
package com.proxiad.hangmangame.soap.consumer;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.proxiad.hangmangame.soap.consumer package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetTop10Ever_QNAME = new QName("http://ranking.logic.hangmangame.proxiad.com/", "getTop10Ever");
    private final static QName _GetTop10EverResponse_QNAME = new QName("http://ranking.logic.hangmangame.proxiad.com/", "getTop10EverResponse");
    private final static QName _GetTop10ForLastMonth_QNAME = new QName("http://ranking.logic.hangmangame.proxiad.com/", "getTop10ForLastMonth");
    private final static QName _GetTop10ForLastMonthResponse_QNAME = new QName("http://ranking.logic.hangmangame.proxiad.com/", "getTop10ForLastMonthResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.proxiad.hangmangame.soap.consumer
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetTop10Ever }
     * 
     */
    public GetTop10Ever createGetTop10Ever() {
        return new GetTop10Ever();
    }

    /**
     * Create an instance of {@link GetTop10EverResponse }
     * 
     */
    public GetTop10EverResponse createGetTop10EverResponse() {
        return new GetTop10EverResponse();
    }

    /**
     * Create an instance of {@link GetTop10ForLastMonth }
     * 
     */
    public GetTop10ForLastMonth createGetTop10ForLastMonth() {
        return new GetTop10ForLastMonth();
    }

    /**
     * Create an instance of {@link GetTop10ForLastMonthResponse }
     * 
     */
    public GetTop10ForLastMonthResponse createGetTop10ForLastMonthResponse() {
        return new GetTop10ForLastMonthResponse();
    }

    /**
     * Create an instance of {@link RankingModel }
     * 
     */
    public RankingModel createRankingModel() {
        return new RankingModel();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTop10Ever }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetTop10Ever }{@code >}
     */
    @XmlElementDecl(namespace = "http://ranking.logic.hangmangame.proxiad.com/", name = "getTop10Ever")
    public JAXBElement<GetTop10Ever> createGetTop10Ever(GetTop10Ever value) {
        return new JAXBElement<GetTop10Ever>(_GetTop10Ever_QNAME, GetTop10Ever.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTop10EverResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetTop10EverResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ranking.logic.hangmangame.proxiad.com/", name = "getTop10EverResponse")
    public JAXBElement<GetTop10EverResponse> createGetTop10EverResponse(GetTop10EverResponse value) {
        return new JAXBElement<GetTop10EverResponse>(_GetTop10EverResponse_QNAME, GetTop10EverResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTop10ForLastMonth }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetTop10ForLastMonth }{@code >}
     */
    @XmlElementDecl(namespace = "http://ranking.logic.hangmangame.proxiad.com/", name = "getTop10ForLastMonth")
    public JAXBElement<GetTop10ForLastMonth> createGetTop10ForLastMonth(GetTop10ForLastMonth value) {
        return new JAXBElement<GetTop10ForLastMonth>(_GetTop10ForLastMonth_QNAME, GetTop10ForLastMonth.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTop10ForLastMonthResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetTop10ForLastMonthResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ranking.logic.hangmangame.proxiad.com/", name = "getTop10ForLastMonthResponse")
    public JAXBElement<GetTop10ForLastMonthResponse> createGetTop10ForLastMonthResponse(GetTop10ForLastMonthResponse value) {
        return new JAXBElement<GetTop10ForLastMonthResponse>(_GetTop10ForLastMonthResponse_QNAME, GetTop10ForLastMonthResponse.class, null, value);
    }

}
