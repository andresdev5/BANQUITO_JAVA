
package ec.edu.monster.phoneshop.ws;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
 *         <element name="totalInstallments" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="monthlyInstallment" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         <element name="totalAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         <element name="annualInterestRate" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         <element name="installments" type="{http://monster.edu.ec/app/ws}creditInstallment" maxOccurs="unbounded"/>
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
    "totalInstallments",
    "monthlyInstallment",
    "totalAmount",
    "annualInterestRate",
    "installments"
})
@XmlRootElement(name = "applyForCreditResponse")
public class ApplyForCreditResponse {

    protected int totalInstallments;
    @XmlElement(required = true)
    protected BigDecimal monthlyInstallment;
    @XmlElement(required = true)
    protected BigDecimal totalAmount;
    @XmlElement(required = true)
    protected BigDecimal annualInterestRate;
    @XmlElement(required = true)
    protected List<CreditInstallment> installments;

    /**
     * Obtiene el valor de la propiedad totalInstallments.
     * 
     */
    public int getTotalInstallments() {
        return totalInstallments;
    }

    /**
     * Define el valor de la propiedad totalInstallments.
     * 
     */
    public void setTotalInstallments(int value) {
        this.totalInstallments = value;
    }

    /**
     * Obtiene el valor de la propiedad monthlyInstallment.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMonthlyInstallment() {
        return monthlyInstallment;
    }

    /**
     * Define el valor de la propiedad monthlyInstallment.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMonthlyInstallment(BigDecimal value) {
        this.monthlyInstallment = value;
    }

    /**
     * Obtiene el valor de la propiedad totalAmount.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * Define el valor de la propiedad totalAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalAmount(BigDecimal value) {
        this.totalAmount = value;
    }

    /**
     * Obtiene el valor de la propiedad annualInterestRate.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAnnualInterestRate() {
        return annualInterestRate;
    }

    /**
     * Define el valor de la propiedad annualInterestRate.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAnnualInterestRate(BigDecimal value) {
        this.annualInterestRate = value;
    }

    /**
     * Gets the value of the installments property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the installments property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getInstallments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CreditInstallment }
     * </p>
     * 
     * 
     * @return
     *     The value of the installments property.
     */
    public List<CreditInstallment> getInstallments() {
        if (installments == null) {
            installments = new ArrayList<>();
        }
        return this.installments;
    }

}
