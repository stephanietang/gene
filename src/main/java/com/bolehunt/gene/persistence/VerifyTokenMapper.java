package com.bolehunt.gene.persistence;

import com.bolehunt.gene.domain.VerifyToken;
import com.bolehunt.gene.domain.VerifyTokenExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface VerifyTokenMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table verify_token
     *
     * @mbggenerated Sat Jun 06 22:16:14 CST 2015
     */
    int countByExample(VerifyTokenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table verify_token
     *
     * @mbggenerated Sat Jun 06 22:16:14 CST 2015
     */
    int deleteByExample(VerifyTokenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table verify_token
     *
     * @mbggenerated Sat Jun 06 22:16:14 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table verify_token
     *
     * @mbggenerated Sat Jun 06 22:16:14 CST 2015
     */
    int insert(VerifyToken record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table verify_token
     *
     * @mbggenerated Sat Jun 06 22:16:14 CST 2015
     */
    int insertSelective(VerifyToken record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table verify_token
     *
     * @mbggenerated Sat Jun 06 22:16:14 CST 2015
     */
    List<VerifyToken> selectByExample(VerifyTokenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table verify_token
     *
     * @mbggenerated Sat Jun 06 22:16:14 CST 2015
     */
    VerifyToken selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table verify_token
     *
     * @mbggenerated Sat Jun 06 22:16:14 CST 2015
     */
    int updateByExampleSelective(@Param("record") VerifyToken record, @Param("example") VerifyTokenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table verify_token
     *
     * @mbggenerated Sat Jun 06 22:16:14 CST 2015
     */
    int updateByExample(@Param("record") VerifyToken record, @Param("example") VerifyTokenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table verify_token
     *
     * @mbggenerated Sat Jun 06 22:16:14 CST 2015
     */
    int updateByPrimaryKeySelective(VerifyToken record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table verify_token
     *
     * @mbggenerated Sat Jun 06 22:16:14 CST 2015
     */
    int updateByPrimaryKey(VerifyToken record);
}