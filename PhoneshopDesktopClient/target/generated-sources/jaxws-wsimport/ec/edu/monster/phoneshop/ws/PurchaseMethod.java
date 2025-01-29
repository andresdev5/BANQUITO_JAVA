
package ec.edu.monster.phoneshop.ws;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * 
 * 
 * <p>Clase Java para purchaseMethod.</p>
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.</p>
 * <pre>{@code
 * <simpleType name="purchaseMethod">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="CASH"/>
 *     <enumeration value="CREDIT"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "purchaseMethod")
@XmlEnum
public enum PurchaseMethod {

    CASH,
    CREDIT;

    public String value() {
        return name();
    }

    public static PurchaseMethod fromValue(String v) {
        return valueOf(v);
    }

}
