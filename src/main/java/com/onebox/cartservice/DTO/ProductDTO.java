package com.onebox.cartservice.DTO;


public class ProductDTO {
    private Long id;
    private String description;
    private int amount;

    public ProductDTO() {
    }

    public ProductDTO(long id, String description, int amount) {
        this.id = id;
        this.description = description;
        this.amount = amount;
    }


    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
