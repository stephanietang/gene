package com.bolehunt.gene.domain;

public class Degree {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column degree.id
     *
     * @mbggenerated Wed Jul 15 21:55:19 CST 2015
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column degree.degree_desc
     *
     * @mbggenerated Wed Jul 15 21:55:19 CST 2015
     */
    private String degreeDesc;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column degree.id
     *
     * @return the value of degree.id
     *
     * @mbggenerated Wed Jul 15 21:55:19 CST 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column degree.id
     *
     * @param id the value for degree.id
     *
     * @mbggenerated Wed Jul 15 21:55:19 CST 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column degree.degree_desc
     *
     * @return the value of degree.degree_desc
     *
     * @mbggenerated Wed Jul 15 21:55:19 CST 2015
     */
    public String getDegreeDesc() {
        return degreeDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column degree.degree_desc
     *
     * @param degreeDesc the value for degree.degree_desc
     *
     * @mbggenerated Wed Jul 15 21:55:19 CST 2015
     */
    public void setDegreeDesc(String degreeDesc) {
        this.degreeDesc = degreeDesc == null ? null : degreeDesc.trim();
    }
}