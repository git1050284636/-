<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/head.jsp" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>角色管理页面 >> 角色修改页面</span>
    </div>
    <div class="providerAdd">
        <form id="userForm" enctype="multipart/form-data" name="userForm" method="post"
              action="${pageContext.request.contextPath }/role/modifysave.html">
            <input type="hidden" name="method" value="modifyexe">
            <%--			<input type="hidden" name="uid" value="${user.id }"/>--%>
            <input type="hidden" name="id" value="${role.id }"/>
            <div>
                <label for="roleName">用户编码：</label>
                <input type="text" name="roleName" id="roleName" value="${role.roleName }">
                <font color="red"></font>
            </div>
            <div>
                <label for="roleCode">用户名称：</label>
                <input type="text" name="roleCode" id="roleCode" value="${role.roleCode }">
            </div>
            <div class="providerAddBtn">
                <input type="button" name="save" id="save" value="保存"/>
                <input type="button" id="back" name="back" value="返回"/>
            </div>
        </form>
    </div>
</div>
</section>
<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/rolemodify.js"></script>
