<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>

<div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>订单管理页面 >> 订单添加页面</span>
        </div>
        <div class="providerAdd">
          <form id="billForm" name="billForm" method="post" action="${pageContext.request.contextPath }/bill/modifysave.html">
				<input type="hidden" name="method" value="modifysave">
				<input type="hidden" name="id" value="${bill.id}">
                <!--div的class 为error是验证错误，ok是验证成功-->
                <div class="">
                    <label for="billCode">订单编码：</label>
                    <input type="text" name="billCode" id="billCode" value="${bill.billCode }" readonly="readonly"> 
                </div>
                <div>
                    <label for="productName">商品名称：</label>
                    <input type="text" name="productName" id="productName" value="${bill.productName }"> 
					<font color="red"></font>
                </div>
                <div>
                    <label for="productUnit">商品单位：</label>
                    <input type="text" name="productUnit" id="productUnit" value="${bill.productUnit }"> 
					<font color="red"></font>
                </div>
                <div>
                    <label for="productCount">商品数量：</label>
                    <input type="text" name="productCount" id="productCount" value="${bill.productCount }"> 
					<font color="red"></font>
                </div>
                <div>
                    <label for="totalPrice">总金额：</label>
                    <input type="text" name="totalPrice" id="totalPrice" value="${bill.totalPrice }"> 
					<font color="red"></font>
                </div>
                <div>
                    <label for="providerId">供应商：</label>
                    <input type="hidden" value="${bill.providerId }" id="pid" />
					<select name="providerId" id="providerId">
                        <option value="1" <c:if test="${bill.providerId eq 1}">selected</c:if>>北京三木堂商贸有限公司</option>
                        <option value="2" <c:if test="${bill.providerId eq 2}">selected</c:if>>石家庄帅益食品贸易有限公司</option>
                        <option value="3" <c:if test="${bill.providerId eq 3}">selected</c:if>>深圳市泰香米业有限公司</option>
                        <option value="4" <c:if test="${bill.providerId eq 4}">selected</c:if>>深圳市喜来客商贸有限公司</option>
                        <option value="5" <c:if test="${bill.providerId eq 5}">selected</c:if>>兴化佳美调味品厂</option>
                        <option value="6" <c:if test="${bill.providerId eq 6}">selected</c:if>>北京纳福尔食用油有限公司</option>
                        <option value="7" <c:if test="${bill.providerId eq 7}">selected</c:if>>北京国粮食用油有限公司</option>
                        <option value="8" <c:if test="${bill.providerId eq 8}">selected</c:if>>慈溪市广和绿色食品厂</option>
                        <option value="9" <c:if test="${bill.providerId eq 9}">selected</c:if>>优百商贸有限公司</option>
                        <option value="10" <c:if test="${bill.providerId eq 10}">selected</c:if>>南京火头军信息技术有限公司</option>
                        <option value="11" <c:if test="${bill.providerId eq 11}">selected</c:if>>广州市白云区美星五金制品厂</option>
                        <option value="12" <c:if test="${bill.providerId eq 12}">selected</c:if>>北京隆盛日化科技</option>
                        <option value="13" <c:if test="${bill.providerId eq 13}">selected</c:if>>山东豪克华光联合发展有限公司</option>
                        <option value="14" <c:if test="${bill.providerId eq 14}">selected</c:if>>无锡喜源坤商行</option>
                        <option value="15" <c:if test="${bill.providerId eq 15}">selected</c:if>>乐摆日用品厂</option>
		        	</select>
					<font color="red"></font>
                </div>
                <div>
                    <label >是否付款：</label>
                    <c:if test="${bill.isPayment == 1 }">
						<input type="radio" name="isPayment" value="1" checked="checked">未付款
						<input type="radio" name="isPayment" value="2" >已付款
					</c:if>
					<c:if test="${bill.isPayment == 2 }">
						<input type="radio" name="isPayment" value="1">未付款
						<input type="radio" name="isPayment" value="2" checked="checked">已付款
					</c:if>
                </div>
                <div class="providerAddBtn">
                  <input type="button" name="save" id="save" value="保存">
				  <input type="button" id="back" name="back" value="返回" >
              	</div>
            </form>
        </div>

    </div>
</section>

<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/billmodify.js"></script>