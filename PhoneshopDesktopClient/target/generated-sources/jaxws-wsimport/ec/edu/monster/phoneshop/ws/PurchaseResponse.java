
package ec.edu.monster.phoneshop.ws;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.</p>
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.</p>
 * 
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="credit" type="{http://monster.edu.ec/app/ws}appliedCreditResponse" minOccurs="0"/>
 *         <element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="status" type="{http://monster.edu.ec/app/ws}responseStatus"/>
 *         <element name="invoicePath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "credit",
    "message",
    "status",
    "invoicePath"
})
@XmlRootElement(name = "purchaseResponse")
public class PurchaseResponse {

    protected AppliedCreditResponse credit;
    protected String message;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected ResponseStatus status;
    @XmlElement(required = true)
    protected String invoicePath;

    /**
     * Obtiene el valor de la propiedad credit.
     * 
     * @return
     *     possible object is
     *     {@link AppliedCreditResponse }
     *     
     */
    public AppliedCreditResponse getCredit() {
        return credit;
    }

    /**
     * Define el valor de la propiedad credit.
     * 
     * @param value
     *     allowed object is
     *     {@link AppliedCreditResponse }
     *     
     */
    public void setCredit(AppliedCreditResponse value) {
        this.credit = value;
    }

    /**
     * Obtiene el valor de la propiedad message.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Define el valor de la propiedad message.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Obtiene el valor de la propiedad status.
     * 
     * @return
     *     possible object is
     *     {@link ResponseStatus }
     *     
     */
    public ResponseStatus getStatus() {
        return status;
    }

    /**
     * Define el valor de la propiedad status.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseStatus }
     *     
     */
    public void setStatus(ResponseStatus value) {
        this.status = value;
    }

    /**
     * Obtiene el valor de la propiedad invoicePath.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoicePath() {
        return invoicePath;
    }

    /**
     * Define el valor de la propiedad invoicePath.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoicePath(String value) {
        this.invoicePath = value;
    }

}
