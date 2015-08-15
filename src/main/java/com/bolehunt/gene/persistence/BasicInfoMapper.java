package com.bolehunt.gene.persistence;

import com.bolehunt.gene.domain.BasicInfo;
import com.bolehunt.gene.domain.BasicInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BasicInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_info
     *
     * @mbggenerated
     */
    int countByExample(BasicInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_info
     *
     * @mbggenerated
     */
    int deleteByExample(BasicInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_info
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_info
     *
     * @mbggenerated
     */
    int insert(BasicInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_info
     *
     * @mbggenerated
     */
    int insertSelective(BasicInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_info
     *
     * @mbggenerated
     */
    List<BasicInfo> selectByExample(BasicInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_info
     *
     * @mbggenerated
     */
    BasicInfo selectByPrimaryKey(Integer userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_info
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") BasicInfo record, @Param("example") BasicInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_info
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") BasicInfo record, @Param("example") BasicInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(BasicInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(BasicInfo record);
}