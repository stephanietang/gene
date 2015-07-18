package com.bolehunt.gene.persistence;

import com.bolehunt.gene.domain.Degree;
import com.bolehunt.gene.domain.DegreeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DegreeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table degree
     *
     * @mbggenerated Wed Jul 15 21:55:19 CST 2015
     */
    int countByExample(DegreeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table degree
     *
     * @mbggenerated Wed Jul 15 21:55:19 CST 2015
     */
    int deleteByExample(DegreeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table degree
     *
     * @mbggenerated Wed Jul 15 21:55:19 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table degree
     *
     * @mbggenerated Wed Jul 15 21:55:19 CST 2015
     */
    int insert(Degree record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table degree
     *
     * @mbggenerated Wed Jul 15 21:55:19 CST 2015
     */
    int insertSelective(Degree record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table degree
     *
     * @mbggenerated Wed Jul 15 21:55:19 CST 2015
     */
    List<Degree> selectByExample(DegreeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table degree
     *
     * @mbggenerated Wed Jul 15 21:55:19 CST 2015
     */
    Degree selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table degree
     *
     * @mbggenerated Wed Jul 15 21:55:19 CST 2015
     */
    int updateByExampleSelective(@Param("record") Degree record, @Param("example") DegreeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table degree
     *
     * @mbggenerated Wed Jul 15 21:55:19 CST 2015
     */
    int updateByExample(@Param("record") Degree record, @Param("example") DegreeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table degree
     *
     * @mbggenerated Wed Jul 15 21:55:19 CST 2015
     */
    int updateByPrimaryKeySelective(Degree record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table degree
     *
     * @mbggenerated Wed Jul 15 21:55:19 CST 2015
     */
    int updateByPrimaryKey(Degree record);
}