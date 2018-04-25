package com.anomaly.android.kyanbas.Modal;

/**
 * Created by Harshil on 20-04-2018.
 */

public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String email;
    private UserAddress address;
    private Integer isVerified;
    private String profilePicture;
    private String thumbnailPicture;
    private String nicename;

    public User(Integer id, String firstName, String lastName, String contactNumber, String email, UserAddress address, Integer isVerified, String profilePicture, String thumbnailPicture, String nicename) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
        this.isVerified = isVerified;
        this.profilePicture = profilePicture;
        this.thumbnailPicture = thumbnailPicture;
        this.nicename = nicename;
    }

    public User(Integer id, String firstName, String lastName, String profilePicture, String thumbnailPicture, String nicename) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
        this.isVerified = isVerified;
        this.profilePicture = profilePicture;
        this.thumbnailPicture = thumbnailPicture;
        this.nicename = nicename;
    }

    public User(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserAddress getAddress() {
        return address;
    }

    public void setAddress(UserAddress address) {
        this.address = address;
    }

    public Integer getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Integer isVerified) {
        this.isVerified = isVerified;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
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
}
