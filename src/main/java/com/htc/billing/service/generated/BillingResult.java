//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.08.19 at 11:24:48 a. m. CST 
//


package com.htc.billing.service.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for billingResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="billingResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="billingCode" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codeEmployee" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="nameEmployee" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nameClient" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="paymentType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dateOfSell" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="totalSell" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="productsDetails" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="codProduct" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *                   &lt;element name="quantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="presentationProduct" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="nameProduct" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="unitPriceProduct" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *                   &lt;element name="subtotal" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "billingResult", propOrder = {
    "billingCode",
    "codeEmployee",
    "nameEmployee",
    "nameClient",
    "paymentType",
    "dateOfSell",
    "totalSell",
    "productsDetails"
})
public class BillingResult {

    protected long billingCode;
    protected long codeEmployee;
    @XmlElement(required = true)
    protected String nameEmployee;
    @XmlElement(required = true)
    protected String nameClient;
    @XmlElement(required = true)
    protected String paymentType;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateOfSell;
    protected double totalSell;
    @XmlElement(required = true)
    protected List<BillingResult.ProductsDetails> productsDetails;

    /**
     * Gets the value of the billingCode property.
     * 
     */
    public long getBillingCode() {
        return billingCode;
    }

    /**
     * Sets the value of the billingCode property.
     * 
     */
    public void setBillingCode(long value) {
        this.billingCode = value;
    }

    /**
     * Gets the value of the codeEmployee property.
     * 
     */
    public long getCodeEmployee() {
        return codeEmployee;
    }

    /**
     * Sets the value of the codeEmployee property.
     * 
     */
    public void setCodeEmployee(long value) {
        this.codeEmployee = value;
    }

    /**
     * Gets the value of the nameEmployee property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameEmployee() {
        return nameEmployee;
    }

    /**
     * Sets the value of the nameEmployee property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameEmployee(String value) {
        this.nameEmployee = value;
    }

    /**
     * Gets the value of the nameClient property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameClient() {
        return nameClient;
    }

    /**
     * Sets the value of the nameClient property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameClient(String value) {
        this.nameClient = value;
    }

    /**
     * Gets the value of the paymentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * Sets the value of the paymentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentType(String value) {
        this.paymentType = value;
    }

    /**
     * Gets the value of the dateOfSell property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateOfSell() {
        return dateOfSell;
    }

    /**
     * Sets the value of the dateOfSell property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateOfSell(XMLGregorianCalendar value) {
        this.dateOfSell = value;
    }

    /**
     * Gets the value of the totalSell property.
     * 
     */
    public double getTotalSell() {
        return totalSell;
    }

    /**
     * Sets the value of the totalSell property.
     * 
     */
    public void setTotalSell(double value) {
        this.totalSell = value;
    }

    /**
     * Gets the value of the productsDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the productsDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProductsDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BillingResult.ProductsDetails }
     * 
     * 
     */
    public List<BillingResult.ProductsDetails> getProductsDetails() {
        if (productsDetails == null) {
            productsDetails = new ArrayList<BillingResult.ProductsDetails>();
        }
        return this.productsDetails;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="codProduct" type="{http://www.w3.org/2001/XMLSchema}long"/>
     *         &lt;element name="quantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="presentationProduct" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="nameProduct" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="unitPriceProduct" type="{http://www.w3.org/2001/XMLSchema}double"/>
     *         &lt;element name="subtotal" type="{http://www.w3.org/2001/XMLSchema}double"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "codProduct",
        "quantity",
        "presentationProduct",
        "nameProduct",
        "unitPriceProduct",
        "subtotal"
    })
    public static class ProductsDetails {

        protected long codProduct;
        protected int quantity;
        @XmlElement(required = true)
        protected String presentationProduct;
        @XmlElement(required = true)
        protected String nameProduct;
        protected double unitPriceProduct;
        protected double subtotal;

        /**
         * Gets the value of the codProduct property.
         * 
         */
        public long getCodProduct() {
            return codProduct;
        }

        /**
         * Sets the value of the codProduct property.
         * 
         */
        public void setCodProduct(long value) {
            this.codProduct = value;
        }

        /**
         * Gets the value of the quantity property.
         * 
         */
        public int getQuantity() {
            return quantity;
        }

        /**
         * Sets the value of the quantity property.
         * 
         */
        public void setQuantity(int value) {
            this.quantity = value;
        }

        /**
         * Gets the value of the presentationProduct property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPresentationProduct() {
            return presentationProduct;
        }

        /**
         * Sets the value of the presentationProduct property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPresentationProduct(String value) {
            this.presentationProduct = value;
        }

        /**
         * Gets the value of the nameProduct property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNameProduct() {
            return nameProduct;
        }

        /**
         * Sets the value of the nameProduct property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNameProduct(String value) {
            this.nameProduct = value;
        }

        /**
         * Gets the value of the unitPriceProduct property.
         * 
         */
        public double getUnitPriceProduct() {
            return unitPriceProduct;
        }

        /**
         * Sets the value of the unitPriceProduct property.
         * 
         */
        public void setUnitPriceProduct(double value) {
            this.unitPriceProduct = value;
        }

        /**
         * Gets the value of the subtotal property.
         * 
         */
        public double getSubtotal() {
            return subtotal;
        }

        /**
         * Sets the value of the subtotal property.
         * 
         */
        public void setSubtotal(double value) {
            this.subtotal = value;
        }

    }

}
