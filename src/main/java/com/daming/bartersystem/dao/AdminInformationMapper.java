package com.daming.bartersystem.dao;

import com.daming.bartersystem.entitys.AdminInformation;
import com.daming.bartersystem.entitys.AdminInformationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminInformationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_information
     *
     * @mbg.generated
     */
    long countByExample(AdminInformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_information
     *
     * @mbg.generated
     */
    int deleteByExample(AdminInformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_information
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer adminId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_information
     *
     * @mbg.generated
     */
    int insert(AdminInformation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_information
     *
     * @mbg.generated
     */
    int insertSelective(AdminInformation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_information
     *
     * @mbg.generated
     */
    List<AdminInformation> selectByExample(AdminInformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_information
     *
     * @mbg.generated
     */
    AdminInformation selectByPrimaryKey(Integer adminId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_information
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") AdminInformation record, @Param("example") AdminInformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_information
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") AdminInformation record, @Param("example") AdminInformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_information
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(AdminInformation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table admin_information
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(AdminInformation record);
}