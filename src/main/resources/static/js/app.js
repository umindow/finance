/**
 * 扩展的框架通用JS独立文件
 * by SK.Loda 2016-05-28
 */
$(document).ready(function() {	
	$(".fa-bug").click(function(){
		if($(this).hasClass("btn-outline"))
			$("#debug").fadeIn();
		else 
			$("#debug").fadeOut();
		$(this).toggleClass("btn-outline");
	});
	
	
	//设置左侧导航条默认打开样式(给前端一个hack方式，定义context_url变量即可覆盖右侧初始化高亮菜单)
	var context = typeof(context_url)=="undefined" ? get("context_url") : context_url;
	
	$("a[url='"+context+"']").parent("li").addClass("active").parent("ul").addClass("in").parent("li").addClass("active");
	
	//全局AJAX设置
	$(document).ajaxStart(function(event, xhr, settings) {
        $('.ladda-button').ladda().ladda( 'start' );
	}).ajaxComplete(function(event, xhr, settings) {
		$('.ladda-button').ladda().ladda( 'stop' );
	});
	
	// 设置jQuery Ajax全局的参数  
	var option = {
			type : "POST",
	        error: function(jqXHR, textStatus, errorThrown){  
	        	var message = "";
	            switch (jqXHR.status){  
	                case(500): 
	                	message = "服务器系统内部错误";
	                    error(message);  
	                    break;  
	                case(401):  
	                	message = "用户未登录";
	                	warning(message);  
	                    break;  
	                case(403):  
	                	message = "不合法的操作";
	                	warning(message);  
	                    break;  
	                case(408):  
	                	message = "请求超时";
	                	warning(message);  
	                    break;  
	                default:  
	                	message = "未知错误";
	                	error(message);  
	            }  
	            sw_alert("操作失败",message,"error");
	        }
	
			
	    };
	
	// CSRF Token
	var token = $("#CSRFToken").val();
	if(token){
		var _headers = {"RequestVerificationToken":token};
		option["headers"] = _headers;
	}
	$.ajaxSetup(option);  


});


//===========================================================
//通用JS方法
//author:SK.Loda
//===========================================================

/**
 * 菜单跳转
 */
function goUrl(url){
	store("context_url", url);
	jumpTo(url);
}


/**
* 刷新当前窗口
*/
function refreshWindow(){
	window.location.reload();
}

/**
* 跳转到url
* @param url
*/
function jumpTo(url){
	window.location.href = url;
}
/**
* 若干秒后消失的提示组件TOAST
*/
function toast(title,msg,type,callback){
	toastr.options = {
          closeButton: true,
          progressBar: true,
          preventDuplicates: false,
          positionClass: "toast-top-center",
          showDuration: 400,
          hideDuration: 1000,
          timeOut: 1500,
          extendedTimeOut: 1000,
		  showEasing: "swing",
		  hideEasing: "linear",
		  showMethod: "fadeIn",
		  hideMethod: "fadeOut",
          onHidden: function(){
          	if(typeof(callback)=="function")
          		callback();
          }
      };
	var $toast = toastr[type](msg, title);
}

function success(msg,callback){
	toast("操作成功",msg,"success",callback);
}

function error(msg,callback){
	toast("出错了",msg,"error",callback);
}

function warning(msg,callback){
	toast("警告",msg,"warning",callback);
}

function info(msg,callback){
	toast("提示",msg,"info",callback);
}

/**
* 二次确认的提示框
* @param _title
* @param _text
* @param _type error|warning|success|input
* @param onconfirm
*/
function sw_confirm(_title,_text,_type,callback){
	swal({ 
	    title: _title, 
	    text: _text, 
	    type: _type, 
	    showCancelButton: true, 
	    closeOnConfirm: false, 
	    closeOnCancel: true, 
	    showLoaderOnConfirm: true,
	    cancelButtonColor: "#4876FF",
	    confirmButtonColor: "#DD6B55",
	    confirmButtonText: "确认", 
	    cancelButtonText: "取消"
	}, function() { 
  	if(typeof(callback)=="function")
  		callback();
	});
}

/**
* 提示框
* @param _title
* @param _text
* @param _type error|warning|success|input
*/
function sw_alert(_title,_text,_type,callback){
  swal({
      title: _title,
      showLoaderOnConfirm: false,
      text: _text,
      type: _type
  },function(){
  	if(typeof(callback)=="function")
  		callback();
  });
}

/**
* 交互输入提示框
* @param _title
* @param _text
* @param _inputPlaceholder
* @param callback
*/
function sw_input(_title,_text,_inputPlaceholder,callback){
  swal({
      title: _title,
      text: _text,
      type: "input",
	  closeOnConfirm: false, 
	  closeOnCancel: true, 
	  showCancelButton: true,
	  showLoaderOnConfirm: true,
	  confirmButtonColor: "#DD6B55",
	  cancelButtonText: "取消",
	  confirmButtonText: "确认", 
	  inputPlaceholder: _inputPlaceholder
  },function(inputValue){   
  	if (inputValue === false) 
  		return false;      
  	
  	if (inputValue === "") {     
  		swal.showInputError("输入项不能为空!");     
  		return false   
  	}

  	if(typeof(callback)=="function")
  		callback(inputValue);
  });
}

/**
 * 关闭提示框
 */
function sw_close(){
	swal.close() 
}

/**
* 字符串去前后空格
*/
String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g, "");
}


/**
 * 浏览器本地缓存存储
 * @param name
 * @param val
 */
function store(name, val) {
	if (typeof (Storage) !== "undefined") {
		localStorage.setItem(name, val);
	} else {
		window.alert('Please use a modern browser to properly view this template!');
	}
}

/**
 * 浏览器本地缓存存储获取
 * @param name
 * @param val
 */
function get(name) {
    if (typeof (Storage) !== "undefined") {
    	return localStorage.getItem(name);
    } else {
    	window.alert('Please use a modern browser to properly view this template!');
    }
}