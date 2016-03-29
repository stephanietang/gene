package com.bolehunt.gene.domain;

import java.io.Serializable;

public class BasicInfo implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_info.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_info.user_id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_info.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_info.country
     *
     * @mbggenerated
     */
    private Integer country;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_info.city
     *
     * @mbggenerated
     */
    private String city;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_info.tel_no
     *
     * @mbggenerated
     */
    private String telNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_info.experience
     *
     * @mbggenerated
     */
    private Integer experience;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_info.degree
     *
     * @mbggenerated
     */
    private Integer degree;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_info.sex
     *
     * @mbggenerated
     */
    private Integer sex;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_info.born_year
     *
     * @mbggenerated
     */
    private Integer bornYear;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table basic_info
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_info.id
     *
     * @return the value of basic_info.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_info.id
     *
     * @param id the value for basic_info.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_info.user_id
     *
     * @return the value of basic_info.user_id
     *
     * @mbggenerated
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_info.user_id
     *
     * @param userId the value for basic_info.user_id
     *
     * @mbggenerated
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_info.name
     *
     * @return the value of basic_info.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_info.name
     *
     * @param name the value for basic_info.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_info.country
     *
     * @return the value of basic_info.country
     *
     * @mbggenerated
     */
    public Integer getCountry() {
        return country;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_info.country
     *
     * @param country the value for basic_info.country
     *
     * @mbggenerated
     */
    public void setCountry(Integer country) {
        this.country = country;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_info.city
     *
     * @return the value of basic_info.city
     *
     * @mbggenerated
     */
    public String getCity() {
        return city;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_info.city
     *
     * @param city the value for basic_info.city
     *
     * @mbggenerated
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_info.tel_no
     *
     * @return the value of basic_info.tel_no
     *
     * @mbggenerated
     */
    public String getTelNo() {
        return telNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_info.tel_no
     *
     * @param telNo the value for basic_info.tel_no
     *
     * @mbggenerated
     */
    public void setTelNo(String telNo) {
        this.telNo = telNo == null ? null : telNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_info.experience
     *
     * @return the value of basic_info.experience
     *
     * @mbggenerated
     */
    public Integer getExperience() {
        return experience;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_info.experience
     *
     * @param experience the value for basic_info.experience
     *
     * @mbggenerated
     */
    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_info.degree
     *
     * @return the value of basic_info.degree
     *
     * @mbggenerated
     */
    public Integer getDegree() {
        return degree;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_info.degree
     *
     * @param degree the value for basic_info.degree
     *
     * @mbggenerated
     */
    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_info.sex
     *
     * @return the value of basic_info.sex
     *
     * @mbggenerated
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_info.sex
     *
     * @param sex the value for basic_info.sex
     *
     * @mbggenerated
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_info.born_year
     *
     * @return the value of basic_info.born_year
     *
     * @mbggenerated
     */
    public Integer getBornYear() {
        return bornYear;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_info.born_year
     *
     * @param bornYear the value for basic_info.born_year
     *
     * @mbggenerated
     */
    public void setBornYear(Integer bornYear) {
        this.bornYear = bornYear;
    }
}