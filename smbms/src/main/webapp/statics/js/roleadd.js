



var roleName = null;
var roleCode =null;
var addBtn = null;
var backBtn = null;


$(function(){
    roleCode = $("#roleCode");
    roleName = $("#roleName");
    addBtn = $("#add");
    backBtn = $("#back");
    //初始化的时候，要把所有的提示信息变为：* 以提示必填项，更灵活，不要写在页面上
    roleCode.next().html("*");
    roleName.next().html("*");



    /** 验证
     * 失焦\获焦
     * jquery的方法传递*/
   /* roleCode.bind("blur",function(){*/
        //ajax后台验证--userCode是否已存在
        //user.do?method=ucexist&userCode=**
      /*  $.ajax({
            type:"GET",//请求类型
            url:path+"/role/ucexists.html",//请求的url
            data:{userCode:userCode.val()},//请求参数
            dataType:"json",//ajax接口（请求url）返回的数据类型
            success:function(data){//data：返回数据（json对象）
                if(data.userCode == "exist"){//账号已存在，错误提示
                    validateTip(userCode.next(),{"color":"red"},imgNo+ " 该用户账号已存在",false);
                }else{//账号可用，正确提示
                    validateTip(userCode.next(),{"color":"green"},imgYes+" 该账号可以使用",true);
                }
            },
            error:function(data){//当访问时候，404，500 等非200的错误状态码
                validateTip(userCode.next(),{"color":"red"},imgNo+" 您访问的页面不存在",false);
            }
        });*/

/*
    }).bind("focus",function(){
        //显示友情提示
        validateTip(userCode.next(),{"color":"#666666"},"* 用户编码是您登录系统的账号",false);
    }).focus();*/









   /* phone.bind("focus",function(){
        validateTip(phone.next(),{"color":"#666666"},"* 请输入手机号",false);
    }).bind("blur",function(){
        var patrn=/^(13[0-9]|15[0-9]|18[0-9])\d{8}$/;
        if(phone.val().match(patrn)){
            validateTip(phone.next(),{"color":"green"},imgYes,true);
        }else{
            validateTip(phone.next(),{"color":"red"},imgNo + " 您输入的手机号格式不正确",false);
        }
    });*/



    addBtn.bind("click",function(){
        /*if(userCode.attr("validateStatus") != "true"){
            userCode.blur();
        }else if(userName.attr("validateStatus") != "true"){
            userName.blur();
        }else if(userPassword.attr("validateStatus") != "true"){
            userPassword.blur();
        }else if(ruserPassword.attr("validateStatus") != "true"){
            ruserPassword.blur();
        }else if(birthday.attr("validateStatus") != "true"){
            birthday.blur();
        }else if(phone.attr("validateStatus") != "true"){
            phone.blur();
        }else if(userRole.attr("validateStatus") != "true"){
            userRole.blur();
        }else{*/
        if(confirm("是否确认提交数据")){
            $("#roleForm").submit();
        }
        /*	}*/
    });

    backBtn.on("click",function(){
        if(referer != undefined
            && null != referer
            && "" != referer
            && "null" != referer
            && referer.length > 4){
            window.location.href = referer;
        }else{
            history.back(-1);
        }
    });
});