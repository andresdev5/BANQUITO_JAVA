
package ec.edu.monster.phoneshop.ws;

import java.math.BigDecimal;
import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para movement complex type.</p>
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.</p>
 * 
 * <pre>{@code
 * <complexType name="movement">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="amount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         <element name="type" type="{http://monster.edu.ec/app/ws}movementType"/>
 *         <element name="number" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="reference" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="targetAccount" type="{http://monster.edu.ec/app/ws}bankAccount"/>
 *         <element name="sourceAccount" type="{http://monster.edu.ec/app/ws}bankAccount" minOccurs="0"/>
 *         <element name="sender" type="{http://monster.edu.ec/app/ws}user"/>
 *         <element name="createdAt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         <element name="updatedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "movement", propOrder = {
    "id",
    "amount",
    "type",
    "number",
    "reference",
    "targetAccount",
    "sourceAccount",
    "sender",
    "createdAt",
    "updatedAt"
})
public class Movement {

    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected BigDecimal amount;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected MovementType type;
    protected int number;
    @XmlElement(required = true)
    protected String reference;
    @XmlElement(required = true)
    protected BankAccount targetAccount;
    protected BankAccount sourceAccount;
    @XmlElement(required = true)
    protected User sender;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdAt;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar updatedAt;

    /**
     * Obtiene el valor de la propiedad id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Obtiene el valor de la propiedad amount.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Define el valor de la propiedad amount.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmount(BigDecimal value) {
        this.amount = value;
    }

    /**
     * Obtiene el valor de la propiedad type.
     * 
     * @return
     *     possible object is
     *     {@link MovementType }
     *     
     */
    public MovementType getType() {
        return type;
    }

    /**
     * Define el valor de la propiedad type.
     * 
     * @param value
     *     allowed object is
     *     {@link MovementType }
     *     
     */
    public void setType(MovementType value) {
        this.type = value;
    }

    /**
     * Obtiene el valor de la propiedad number.
     * 
     */
    public int getNumber() {
        return number;
    }

    /**
     * Define el valor de la propiedad number.
     * 
     */
    public void setNumber(int value) {
        this.number = value;
    }

    /**
     * Obtiene el valor de la propiedad reference.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReference() {
        return reference;
    }

    /**
     * Define el valor de la propiedad reference.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReference(String value) {
        this.reference = value;
    }

    /**
     * Obtiene el valor de la propiedad targetAccount.
     * 
     * @return
     *     possible object is
     *     {@link BankAccount }
     *     
     */
    public BankAccount getTargetAccount() {
        return targetAccount;
    }

    /**
     * Define el valor de la propiedad targetAccount.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAccount }
     *     
     */
    public void setTargetAccount(BankAccount value) {
        this.targetAccount = value;
    }

    /**
     * Obtiene el valor de la propiedad sourceAccount.
     * 
     * @return
     *     possible object is
     *     {@link BankAccount }
     *     
     */
    public BankAccount getSourceAccount() {
        return sourceAccount;
    }

    /**
     * Define el valor de la propiedad sourceAccount.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAccount }
     *     
     */
    public void setSourceAccount(BankAccount value) {
        this.sourceAccount = value;
    }

    /**
     * Obtiene el valor de la propiedad sender.
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getSender() {
        return sender;
    }

    /**
     * Define el valor de la propiedad sender.
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setSender(User value) {
        this.sender = value;
    }

    /**
     * Obtiene el valor de la propiedad createdAt.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreatedAt() {
        return createdAt;
    }

    /**
     * Define el valor de la propiedad createdAt.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreatedAt(XMLGregorianCalendar value) {
        this.createdAt = value;
    }

    /**
     * Obtiene el valor de la propiedad updatedAt.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Define el valor de la propiedad updatedAt.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUpdatedAt(XMLGregorianCalendar value) {
        this.updatedAt = value;
    }

}
