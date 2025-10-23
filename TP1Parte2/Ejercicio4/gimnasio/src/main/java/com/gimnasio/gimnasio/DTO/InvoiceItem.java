package com.gimnasio.gimnasio.DTO;

public class InvoiceItem {
    private String description;
    private Integer quantity;
    private Double unitPrice;
    private Double lineTotal;

    public InvoiceItem(String description, Integer quantity, Double unitPrice, Double lineTotal) {
        this.description = description;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.lineTotal = lineTotal;
    }

    public String getDescription() { return description; }
    public Integer getQuantity() { return quantity; }
    public Double getUnitPrice() { return unitPrice; }
    public Double getLineTotal() { return lineTotal; }
}
