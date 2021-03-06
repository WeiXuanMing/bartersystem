package com.daming.bartersystem.dao;

import com.daming.bartersystem.entitys.UserReview;
import com.daming.bartersystem.entitys.UserReviewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserReviewMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_review
     *
     * @mbg.generated
     */
    long countByExample(UserReviewExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_review
     *
     * @mbg.generated
     */
    int deleteByExample(UserReviewExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_review
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer userReviewId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_review
     *
     * @mbg.generated
     */
    int insert(UserReview record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_review
     *
     * @mbg.generated
     */
    int insertSelective(UserReview record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_review
     *
     * @mbg.generated
     */
    List<UserReview> selectByExampleWithBLOBs(UserReviewExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_review
     *
     * @mbg.generated
     */
    List<UserReview> selectByExample(UserReviewExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_review
     *
     * @mbg.generated
     */
    UserReview selectByPrimaryKey(Integer userReviewId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_review
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") UserReview record, @Param("example") UserReviewExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_review
     *
     * @mbg.generated
     */
    int updateByExampleWithBLOBs(@Param("record") UserReview record, @Param("example") UserReviewExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_review
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") UserReview record, @Param("example") UserReviewExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_review
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(UserReview record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_review
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(UserReview record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_review
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(UserReview record);
}