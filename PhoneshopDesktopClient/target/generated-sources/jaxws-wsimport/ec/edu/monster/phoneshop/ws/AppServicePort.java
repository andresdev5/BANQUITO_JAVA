
package ec.edu.monster.phoneshop.ws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the XML-WS Tools.
 * XML-WS Tools 4.0.2
 * Generated source version: 3.0
 * 
 */
@WebService(name = "AppServicePort", targetNamespace = "http://monster.edu.ec/app/ws")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface AppServicePort {


    /**
     * 
     * @param getMovementsByIdentificationRequest
     * @return
     *     returns ec.edu.monster.phoneshop.ws.GetMovementsByIdentificationResponse
     */
    @WebMethod
    @WebResult(name = "getMovementsByIdentificationResponse", targetNamespace = "http://monster.edu.ec/app/ws", partName = "getMovementsByIdentificationResponse")
    public GetMovementsByIdentificationResponse getMovementsByIdentification(
        @WebParam(name = "getMovementsByIdentificationRequest", targetNamespace = "http://monster.edu.ec/app/ws", partName = "getMovementsByIdentificationRequest")
        GetMovementsByIdentificationRequest getMovementsByIdentificationRequest);

    /**
     * 
     * @param createProductRequest
     * @return
     *     returns ec.edu.monster.phoneshop.ws.CreateProductResponse
     */
    @WebMethod
    @WebResult(name = "createProductResponse", targetNamespace = "http://monster.edu.ec/app/ws", partName = "createProductResponse")
    public CreateProductResponse createProduct(
        @WebParam(name = "createProductRequest", targetNamespace = "http://monster.edu.ec/app/ws", partName = "createProductRequest")
        CreateProductRequest createProductRequest);

    /**
     * 
     * @param deleteProductRequest
     * @return
     *     returns ec.edu.monster.phoneshop.ws.DeleteProductResponse
     */
    @WebMethod
    @WebResult(name = "deleteProductResponse", targetNamespace = "http://monster.edu.ec/app/ws", partName = "deleteProductResponse")
    public DeleteProductResponse deleteProduct(
        @WebParam(name = "deleteProductRequest", targetNamespace = "http://monster.edu.ec/app/ws", partName = "deleteProductRequest")
        DeleteProductRequest deleteProductRequest);

    /**
     * 
     * @param applyForCreditRequest
     * @return
     *     returns ec.edu.monster.phoneshop.ws.ApplyForCreditResponse
     */
    @WebMethod
    @WebResult(name = "applyForCreditResponse", targetNamespace = "http://monster.edu.ec/app/ws", partName = "applyForCreditResponse")
    public ApplyForCreditResponse applyForCredit(
        @WebParam(name = "applyForCreditRequest", targetNamespace = "http://monster.edu.ec/app/ws", partName = "applyForCreditRequest")
        ApplyForCreditRequest applyForCreditRequest);

    /**
     * 
     * @param checkCreditRequest
     * @return
     *     returns ec.edu.monster.phoneshop.ws.CheckCreditResponse
     */
    @WebMethod
    @WebResult(name = "checkCreditResponse", targetNamespace = "http://monster.edu.ec/app/ws", partName = "checkCreditResponse")
    public CheckCreditResponse checkCredit(
        @WebParam(name = "checkCreditRequest", targetNamespace = "http://monster.edu.ec/app/ws", partName = "checkCreditRequest")
        CheckCreditRequest checkCreditRequest);

    /**
     * 
     * @param getProductRequest
     * @return
     *     returns ec.edu.monster.phoneshop.ws.GetProductResponse
     */
    @WebMethod
    @WebResult(name = "getProductResponse", targetNamespace = "http://monster.edu.ec/app/ws", partName = "getProductResponse")
    public GetProductResponse getProduct(
        @WebParam(name = "getProductRequest", targetNamespace = "http://monster.edu.ec/app/ws", partName = "getProductRequest")
        GetProductRequest getProductRequest);

    /**
     * 
     * @param updateProductRequest
     * @return
     *     returns ec.edu.monster.phoneshop.ws.UpdateProductResponse
     */
    @WebMethod
    @WebResult(name = "updateProductResponse", targetNamespace = "http://monster.edu.ec/app/ws", partName = "updateProductResponse")
    public UpdateProductResponse updateProduct(
        @WebParam(name = "updateProductRequest", targetNamespace = "http://monster.edu.ec/app/ws", partName = "updateProductRequest")
        UpdateProductRequest updateProductRequest);

    /**
     * 
     * @param loginRequest
     * @return
     *     returns ec.edu.monster.phoneshop.ws.LoginResponse
     */
    @WebMethod
    @WebResult(name = "loginResponse", targetNamespace = "http://monster.edu.ec/app/ws", partName = "loginResponse")
    public LoginResponse login(
        @WebParam(name = "loginRequest", targetNamespace = "http://monster.edu.ec/app/ws", partName = "loginRequest")
        LoginRequest loginRequest);

    /**
     * 
     * @param getProductsRequest
     * @return
     *     returns ec.edu.monster.phoneshop.ws.GetProductsResponse
     */
    @WebMethod
    @WebResult(name = "getProductsResponse", targetNamespace = "http://monster.edu.ec/app/ws", partName = "getProductsResponse")
    public GetProductsResponse getProducts(
        @WebParam(name = "getProductsRequest", targetNamespace = "http://monster.edu.ec/app/ws", partName = "getProductsRequest")
        GetProductsRequest getProductsRequest);

    /**
     * 
     * @param purchaseRequest
     * @return
     *     returns ec.edu.monster.phoneshop.ws.PurchaseResponse
     */
    @WebMethod
    @WebResult(name = "purchaseResponse", targetNamespace = "http://monster.edu.ec/app/ws", partName = "purchaseResponse")
    public PurchaseResponse purchase(
        @WebParam(name = "purchaseRequest", targetNamespace = "http://monster.edu.ec/app/ws", partName = "purchaseRequest")
        PurchaseRequest purchaseRequest);

}
