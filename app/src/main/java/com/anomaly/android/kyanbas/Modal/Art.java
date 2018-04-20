package com.anomaly.android.kyanbas.Modal;

/**
 * Created by Harshil on 20-04-2018.
 */

public class Art {

    private Integer id;
    private String name;
    private Category category;
    private String product;
    private String thumbnailPicture;
    private String nicename;
    private User user;
    private Integer price;
    private Integer hasSpecification;
    private String specifications;
    private String productType;
    private String deliveryType;
    private String description;
    private String available;

    public Art(Integer id, String name, Category category, String product, String thumbnailPicture, String nicename, User user, Integer price, Integer hasSpecification, String specifications, String productType, String deliveryType, String description, String available) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.product = product;
        this.thumbnailPicture = thumbnailPicture;
        this.nicename = nicename;
        this.user = user;
        this.price = price;
        this.hasSpecification = hasSpecification;
        this.specifications = specifications;
        this.productType = productType;
        this.deliveryType = deliveryType;
        this.description = description;
        this.available = available;
    }

    public Art(Integer id, String name, String thumbnailPicture,String description,int price,User user) {
        this.id = id;
        this.name = name;
        this.thumbnailPicture = thumbnailPicture;
        this.price = price;
        this.user=user;

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getThumbnailPicture() {
        return thumbnailPicture;
    }

    public void setThumbnailPicture(String thumbnailPicture) {
        this.thumbnailPicture = thumbnailPicture;
    }

    public String getNicename() {
        return nicename;
    }

    public void setNicename(String nicename) {
        this.nicename = nicename;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getHasSpecification() {
        return hasSpecification;
    }

    public void setHasSpecification(Integer hasSpecification) {
        this.hasSpecification = hasSpecification;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }
}
