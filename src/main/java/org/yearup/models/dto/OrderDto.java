package org.yearup.models.dto;

import org.yearup.models.Order;
import org.yearup.models.OrderLineItem;

import java.util.List;

public class OrderDto {
    private int orderId;
    private int userId;
    private String date;
    private String address;
    private String city;
    private String state;
    private String zip;
    private double shippingAmount;
    private List<OrderLineItem> lineItems;

    public OrderDto(Order order, List<OrderLineItem> lineItems) {
        this.orderId = order.getOrderId();
        this.userId = order.getUserId();
        this.date = order.getDate();
        this.address = order.getAddress();
        this.city = order.getCity();
        this.state = order.getState();
        this.zip = order.getZip();
        this.shippingAmount = order.getShippingAmount();
        this.lineItems = lineItems;
    }

    public OrderDto(int orderId, int userId, String date, String address, String city, String state, String zip, double shippingAmount, List<OrderLineItem> lineItems) {
        this.orderId = orderId;
        this.userId = userId;
        this.date = date;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.shippingAmount = shippingAmount;
        this.lineItems = lineItems;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public double getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(double shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    public List<OrderLineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<OrderLineItem> lineItems) {
        this.lineItems = lineItems;
    }

}
