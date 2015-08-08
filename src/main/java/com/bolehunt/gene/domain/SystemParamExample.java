package com.bolehunt.gene.domain;

import java.util.ArrayList;
import java.util.List;

public class SystemParamExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SYSTEM_PARAM
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SYSTEM_PARAM
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SYSTEM_PARAM
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYSTEM_PARAM
     *
     * @mbggenerated
     */
    public SystemParamExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYSTEM_PARAM
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYSTEM_PARAM
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYSTEM_PARAM
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYSTEM_PARAM
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYSTEM_PARAM
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYSTEM_PARAM
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYSTEM_PARAM
     *
     * @mbggenerated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYSTEM_PARAM
     *
     * @mbggenerated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYSTEM_PARAM
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYSTEM_PARAM
     *
     * @mbggenerated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SYSTEM_PARAM
     *
     * @mbggenerated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andParamGroupIsNull() {
            addCriterion("param_group is null");
            return (Criteria) this;
        }

        public Criteria andParamGroupIsNotNull() {
            addCriterion("param_group is not null");
            return (Criteria) this;
        }

        public Criteria andParamGroupEqualTo(String value) {
            addCriterion("param_group =", value, "paramGroup");
            return (Criteria) this;
        }

        public Criteria andParamGroupNotEqualTo(String value) {
            addCriterion("param_group <>", value, "paramGroup");
            return (Criteria) this;
        }

        public Criteria andParamGroupGreaterThan(String value) {
            addCriterion("param_group >", value, "paramGroup");
            return (Criteria) this;
        }

        public Criteria andParamGroupGreaterThanOrEqualTo(String value) {
            addCriterion("param_group >=", value, "paramGroup");
            return (Criteria) this;
        }

        public Criteria andParamGroupLessThan(String value) {
            addCriterion("param_group <", value, "paramGroup");
            return (Criteria) this;
        }

        public Criteria andParamGroupLessThanOrEqualTo(String value) {
            addCriterion("param_group <=", value, "paramGroup");
            return (Criteria) this;
        }

        public Criteria andParamGroupLike(String value) {
            addCriterion("param_group like", value, "paramGroup");
            return (Criteria) this;
        }

        public Criteria andParamGroupNotLike(String value) {
            addCriterion("param_group not like", value, "paramGroup");
            return (Criteria) this;
        }

        public Criteria andParamGroupIn(List<String> values) {
            addCriterion("param_group in", values, "paramGroup");
            return (Criteria) this;
        }

        public Criteria andParamGroupNotIn(List<String> values) {
            addCriterion("param_group not in", values, "paramGroup");
            return (Criteria) this;
        }

        public Criteria andParamGroupBetween(String value1, String value2) {
            addCriterion("param_group between", value1, value2, "paramGroup");
            return (Criteria) this;
        }

        public Criteria andParamGroupNotBetween(String value1, String value2) {
            addCriterion("param_group not between", value1, value2, "paramGroup");
            return (Criteria) this;
        }

        public Criteria andParamValueIsNull() {
            addCriterion("param_value is null");
            return (Criteria) this;
        }

        public Criteria andParamValueIsNotNull() {
            addCriterion("param_value is not null");
            return (Criteria) this;
        }

        public Criteria andParamValueEqualTo(Integer value) {
            addCriterion("param_value =", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueNotEqualTo(Integer value) {
            addCriterion("param_value <>", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueGreaterThan(Integer value) {
            addCriterion("param_value >", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueGreaterThanOrEqualTo(Integer value) {
            addCriterion("param_value >=", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueLessThan(Integer value) {
            addCriterion("param_value <", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueLessThanOrEqualTo(Integer value) {
            addCriterion("param_value <=", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueIn(List<Integer> values) {
            addCriterion("param_value in", values, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueNotIn(List<Integer> values) {
            addCriterion("param_value not in", values, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueBetween(Integer value1, Integer value2) {
            addCriterion("param_value between", value1, value2, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueNotBetween(Integer value1, Integer value2) {
            addCriterion("param_value not between", value1, value2, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamDescChiIsNull() {
            addCriterion("param_desc_chi is null");
            return (Criteria) this;
        }

        public Criteria andParamDescChiIsNotNull() {
            addCriterion("param_desc_chi is not null");
            return (Criteria) this;
        }

        public Criteria andParamDescChiEqualTo(String value) {
            addCriterion("param_desc_chi =", value, "paramDescChi");
            return (Criteria) this;
        }

        public Criteria andParamDescChiNotEqualTo(String value) {
            addCriterion("param_desc_chi <>", value, "paramDescChi");
            return (Criteria) this;
        }

        public Criteria andParamDescChiGreaterThan(String value) {
            addCriterion("param_desc_chi >", value, "paramDescChi");
            return (Criteria) this;
        }

        public Criteria andParamDescChiGreaterThanOrEqualTo(String value) {
            addCriterion("param_desc_chi >=", value, "paramDescChi");
            return (Criteria) this;
        }

        public Criteria andParamDescChiLessThan(String value) {
            addCriterion("param_desc_chi <", value, "paramDescChi");
            return (Criteria) this;
        }

        public Criteria andParamDescChiLessThanOrEqualTo(String value) {
            addCriterion("param_desc_chi <=", value, "paramDescChi");
            return (Criteria) this;
        }

        public Criteria andParamDescChiLike(String value) {
            addCriterion("param_desc_chi like", value, "paramDescChi");
            return (Criteria) this;
        }

        public Criteria andParamDescChiNotLike(String value) {
            addCriterion("param_desc_chi not like", value, "paramDescChi");
            return (Criteria) this;
        }

        public Criteria andParamDescChiIn(List<String> values) {
            addCriterion("param_desc_chi in", values, "paramDescChi");
            return (Criteria) this;
        }

        public Criteria andParamDescChiNotIn(List<String> values) {
            addCriterion("param_desc_chi not in", values, "paramDescChi");
            return (Criteria) this;
        }

        public Criteria andParamDescChiBetween(String value1, String value2) {
            addCriterion("param_desc_chi between", value1, value2, "paramDescChi");
            return (Criteria) this;
        }

        public Criteria andParamDescChiNotBetween(String value1, String value2) {
            addCriterion("param_desc_chi not between", value1, value2, "paramDescChi");
            return (Criteria) this;
        }

        public Criteria andParamDescEngIsNull() {
            addCriterion("param_desc_eng is null");
            return (Criteria) this;
        }

        public Criteria andParamDescEngIsNotNull() {
            addCriterion("param_desc_eng is not null");
            return (Criteria) this;
        }

        public Criteria andParamDescEngEqualTo(String value) {
            addCriterion("param_desc_eng =", value, "paramDescEng");
            return (Criteria) this;
        }

        public Criteria andParamDescEngNotEqualTo(String value) {
            addCriterion("param_desc_eng <>", value, "paramDescEng");
            return (Criteria) this;
        }

        public Criteria andParamDescEngGreaterThan(String value) {
            addCriterion("param_desc_eng >", value, "paramDescEng");
            return (Criteria) this;
        }

        public Criteria andParamDescEngGreaterThanOrEqualTo(String value) {
            addCriterion("param_desc_eng >=", value, "paramDescEng");
            return (Criteria) this;
        }

        public Criteria andParamDescEngLessThan(String value) {
            addCriterion("param_desc_eng <", value, "paramDescEng");
            return (Criteria) this;
        }

        public Criteria andParamDescEngLessThanOrEqualTo(String value) {
            addCriterion("param_desc_eng <=", value, "paramDescEng");
            return (Criteria) this;
        }

        public Criteria andParamDescEngLike(String value) {
            addCriterion("param_desc_eng like", value, "paramDescEng");
            return (Criteria) this;
        }

        public Criteria andParamDescEngNotLike(String value) {
            addCriterion("param_desc_eng not like", value, "paramDescEng");
            return (Criteria) this;
        }

        public Criteria andParamDescEngIn(List<String> values) {
            addCriterion("param_desc_eng in", values, "paramDescEng");
            return (Criteria) this;
        }

        public Criteria andParamDescEngNotIn(List<String> values) {
            addCriterion("param_desc_eng not in", values, "paramDescEng");
            return (Criteria) this;
        }

        public Criteria andParamDescEngBetween(String value1, String value2) {
            addCriterion("param_desc_eng between", value1, value2, "paramDescEng");
            return (Criteria) this;
        }

        public Criteria andParamDescEngNotBetween(String value1, String value2) {
            addCriterion("param_desc_eng not between", value1, value2, "paramDescEng");
            return (Criteria) this;
        }

        public Criteria andEnabledIsNull() {
            addCriterion("enabled is null");
            return (Criteria) this;
        }

        public Criteria andEnabledIsNotNull() {
            addCriterion("enabled is not null");
            return (Criteria) this;
        }

        public Criteria andEnabledEqualTo(Integer value) {
            addCriterion("enabled =", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotEqualTo(Integer value) {
            addCriterion("enabled <>", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledGreaterThan(Integer value) {
            addCriterion("enabled >", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledGreaterThanOrEqualTo(Integer value) {
            addCriterion("enabled >=", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledLessThan(Integer value) {
            addCriterion("enabled <", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledLessThanOrEqualTo(Integer value) {
            addCriterion("enabled <=", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledIn(List<Integer> values) {
            addCriterion("enabled in", values, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotIn(List<Integer> values) {
            addCriterion("enabled not in", values, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledBetween(Integer value1, Integer value2) {
            addCriterion("enabled between", value1, value2, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotBetween(Integer value1, Integer value2) {
            addCriterion("enabled not between", value1, value2, "enabled");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SYSTEM_PARAM
     *
     * @mbggenerated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SYSTEM_PARAM
     *
     * @mbggenerated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value) {
            super();
            this.condition = condition;
            this.value = value;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.betweenValue = true;
        }
    }
}