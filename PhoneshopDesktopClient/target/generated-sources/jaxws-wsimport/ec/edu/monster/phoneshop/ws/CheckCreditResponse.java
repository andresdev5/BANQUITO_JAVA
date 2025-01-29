
package ec.edu.monster.phoneshop.ws;

import java.math.BigDecimal;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
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
 *         <element name="maxAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         <element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="eligible" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "maxAmount",
    "message",
    "eligible"
})
@XmlRootElement(name = "checkCreditResponse")
public class CheckCreditResponse {

    @XmlElement(required = true)
    protected BigDecimal maxAmount;
    @XmlElement(required = true)
    protected String message;
    protected boolean eligible;

    /**
     * Obtiene el valor de la propiedad maxAmount.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    /**
     * Define el valor de la propiedad maxAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMaxAmount(BigDecimal value) {
        this.maxAmount = value;
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
     * Obtiene el valor de la propiedad eligible.
     * 
     */
    public boolean isEligible() {
        return eligible;
    }

    /**
     * Define el valor de la propiedad eligible.
     * 
     */
    public void setEligible(boolean value) {
        this.eligible = value;
    }

}
