$(function () {
    setTimeout(loadProperties('./languages/'),5000);
});


function loadProperties(path) {  
	var lan;
	if($("#langge").val()!=null&&$("#langge").val()!=undefined){
		lan=$("#langge").val();
		setCookie("langge",$("#langge").val())
	}else{
		lan=getCookie("langge");
		if(lan==null||lan===undefined){
			lan="zh"
		}
	}
        $.i18n.properties({  
            name:'strings',    
            path:path,   
            mode:'map',  
            language:lan,     
            callback:function(){  
            	  $(".i18n").each(function(){
               	   var a=$(this).attr("class");
            	var b=a.substring(5,a.lenth)
               	   $(this).html($.i18n.prop(b));  
               	   }); 
               $("[data-locale]").each(function(){ 
            	   var a=$(this).text();
               		// console.log($(this).data("locale"));
                   $(this).html($.i18n.prop($(this).data("locale")));  
                
               });  
               $('[data-i18n-value]').each(function () {
                   $(this).val($.i18n.prop($(this).data('i18n-value')));
               });
               
               $('input').each(function (){
            	   if(($(this).attr("placeholder"))!=undefined){
            		   if(($(this).attr("placeholder").indexOf("i18n_"))==0){
                		   $(this).attr("placeholder",$.i18n.prop($(this).attr("placeholder")));
                	   }
            	   }
               });
               
               $("th").each(function(){
            	   
            	    var tdArr = $(this).attr("class");
            	    if(tdArr !=undefined&&tdArr!=null ){
            	    if(tdArr.indexOf("i18n") >= 0){
            	    var vel=	$.i18n.prop(tdArr);
            	    	var ter="   <div class='th-inner'>"+vel+"</div>"+
            	   					" <div class='fht-cell'></div>"
            	    	 $(this).html(ter);  
            	    }
            	    }
            	     
            	  });
            }  
        });  
    }  
function loadPropertiesalert(alsss,path){
	var lan;
	if($("#langge").val()!=null&&$("#langge").val()!=undefined){
		lan=$("#langge").val();
		setCookie("langge",$("#langge").val())
	}else{
		lan=getCookie("langge");
		if(lan==null||lan===undefined){
			lan="zh"
		}
	}
        $.i18n.properties({  
            name:'strings',    
            path:path,   
            mode:'map',  
            language:lan,     
            callback:function(){  
            	vel= $.i18n.prop(alsss);
            	
            }  
        });  
        return vel;
}
function setCookie(name,value)
{
var Days = 30;
var exp = new Date();
exp.setTime(exp.getTime() + Days*24*60*60*1000);
document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}
function getCookie(name)
{
var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
if(arr=document.cookie.match(reg)){
	return unescape(arr[2]);
}
 
return null;
}