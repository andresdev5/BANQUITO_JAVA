
package ec.edu.monster.phoneshop.ws;

import java.util.ArrayList;
import java.util.List;
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
 *         <element name="userId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="items" type="{http://monster.edu.ec/app/ws}purchaseItem" maxOccurs="unbounded"/>
 *         <element name="method" type="{http://monster.edu.ec/app/ws}purchaseMethod"/>
 *         <element name="months" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
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
    "userId",
    "items",
    "method",
    "months"
})
@XmlRootElement(name = "purchaseRequest")
public class PurchaseRequest {

    @XmlElement(required = true)
    protected String userId;
    @XmlElement(required = true)
    protected List<PurchaseItem> items;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected PurchaseMethod method;
    protected Integer months;

    /**
     * Obtiene el valor de la propiedad userId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Define el valor de la propiedad userId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the items property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the items property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getItems().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PurchaseItem }
     * </p>
     * 
     * 
     * @return
     *     The value of the items property.
     */
    public List<PurchaseItem> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return this.items;
    }

    /**
     * Obtiene el valor de la propiedad method.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseMethod }
     *     
     */
    public PurchaseMethod getMethod() {
        return method;
    }

    /**
     * Define el valor de la propiedad method.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseMethod }
     *     
     */
    public void setMethod(PurchaseMethod value) {
        this.method = value;
    }

    /**
     * Obtiene el valor de la propiedad months.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMonths() {
        return months;
    }

    /**
     * Define el valor de la propiedad months.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMonths(Integer value) {
        this.months = value;
    }

}
