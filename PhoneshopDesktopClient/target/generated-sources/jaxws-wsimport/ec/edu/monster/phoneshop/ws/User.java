
package ec.edu.monster.phoneshop.ws;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para user complex type.</p>
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.</p>
 * 
 * <pre>{@code
 * <complexType name="user">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="username" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="identificationNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="role" type="{http://monster.edu.ec/app/ws}role"/>
 *         <element name="email" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="profile" type="{http://monster.edu.ec/app/ws}userProfile" minOccurs="0"/>
 *         <element name="bankAccounts" type="{http://monster.edu.ec/app/ws}bankAccount" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "user", propOrder = {
    "id",
    "username",
    "identificationNumber",
    "role",
    "email",
    "profile",
    "bankAccounts"
})
public class User {

    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected String username;
    @XmlElement(required = true)
    protected String identificationNumber;
    @XmlElement(required = true)
    protected Role role;
    @XmlElement(required = true)
    protected String email;
    protected UserProfile profile;
    protected List<BankAccount> bankAccounts;

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
     * Obtiene el valor de la propiedad username.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * Define el valor de la propiedad username.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Obtiene el valor de la propiedad identificationNumber.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificationNumber() {
        return identificationNumber;
    }

    /**
     * Define el valor de la propiedad identificationNumber.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificationNumber(String value) {
        this.identificationNumber = value;
    }

    /**
     * Obtiene el valor de la propiedad role.
     * 
     * @return
     *     possible object is
     *     {@link Role }
     *     
     */
    public Role getRole() {
        return role;
    }

    /**
     * Define el valor de la propiedad role.
     * 
     * @param value
     *     allowed object is
     *     {@link Role }
     *     
     */
    public void setRole(Role value) {
        this.role = value;
    }

    /**
     * Obtiene el valor de la propiedad email.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define el valor de la propiedad email.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Obtiene el valor de la propiedad profile.
     * 
     * @return
     *     possible object is
     *     {@link UserProfile }
     *     
     */
    public UserProfile getProfile() {
        return profile;
    }

    /**
     * Define el valor de la propiedad profile.
     * 
     * @param value
     *     allowed object is
     *     {@link UserProfile }
     *     
     */
    public void setProfile(UserProfile value) {
        this.profile = value;
    }

    /**
     * Gets the value of the bankAccounts property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bankAccounts property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getBankAccounts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BankAccount }
     * </p>
     * 
     * 
     * @return
     *     The value of the bankAccounts property.
     */
    public List<BankAccount> getBankAccounts() {
        if (bankAccounts == null) {
            bankAccounts = new ArrayList<>();
        }
        return this.bankAccounts;
    }

}
