<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@page import="cn.com.bluemoon.admin.domain.vo.UserInfo" %>
<c:set var="root" value="${request.contextPath}"/>
<%--  <c:set var="root" value="${request.contextPath}"/> --%>
<!-- localhost -->
<%--  <c:set var="item" value="http://localhost:20010"/> --%>
<%--  <c:set var="order" value="http://localhost:20020"/> --%>
<%--  <c:set var="user" value="http://localhost:20030"/> --%>
<%--  <c:set var="common" value="http://localhost:20040"/> --%>
<%--  <c:set var="activity" value="http://localhost:20090"/> --%>
<!-- 测试机    192.168.241.107 -->
<%-- <c:set var="item" value="http://192.168.241.107:20010"/> --%>
<%-- <c:set var="order" value="http://192.168.241.107:20020"/> --%>
<%-- <c:set var="user" value="http://192.168.241.107:20030"/> --%>
<%-- <c:set var="common" value="http://192.168.241.107:20040"/> --%>

<c:set var="activity" value="${root}/managerApi"/>
<c:set var="item" value="${root}/managerApi"/>
<c:set var="order" value="${root}/managerApi"/>
<c:set var="user" value="${root}/managerApi"/>
<c:set var="common" value="${root}/managerApi"/>
