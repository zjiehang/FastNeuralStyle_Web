$(document).ready(function()
{
	$("#uploadtempletebutton").click(function()
	{
		$("#uploadtempletediv").css("display","block");
		$("#officialtempletediv").css("display","none");
		
		$("#uploadtempletebutton").css("background-color","#525974");
		$("#uploadtempletebutton").css("color","white");
		$("#uploadtempletebutton").css("border-radius","5px 5px 5px 5px");

		$("#officialtempletebutton").css("background-color","#f2f2f2");
		$("#officialtempletebutton").css("color","#777777");
		$("#officialtempletebutton").css("border-radius","0px");
	});

	$("#officialtempletebutton").click(function()
	{
		$("#officialtempletediv").css("display","block");
		$("#uploadtempletediv").css("display","none");
		
		$("#officialtempletebutton").css("background-color","#525974");
		$("#officialtempletebutton").css("color","white");
		$("#officialtempletebutton").css("border-radius","5px 5px 5px 5px");

		$("#uploadtempletebutton").css("background-color","#f2f2f2");
		$("#uploadtempletebutton").css("color","#777777");
		$("#uploadtempletebutton").css("border-radius","0px");
	});
});


$(document).ready(function()
{
	$(".templeteimage").click(function()
	{
		templeteimage_onclick = $(this);
		templeteimageclass = $("#officialtempletediv").find(".templeteimage");
		add_selected_div = true;

		templeteimageclass.each(function()
		{
			if($(this).find("#itemselecteddiv").length>0)
			{
				$(this).find("#itemselecteddiv").remove();
				if($(this)[0]==templeteimage_onclick[0])
				{
					add_selected_div=false;
				}
			}
		});

		if(add_selected_div==true)
		{
			var itemselecteddiv=$("<div></div>");
			itemselecteddiv.attr("id","itemselecteddiv");
			templeteimage_onclick.append(itemselecteddiv);
		}
	});
});

function clearImage(uploadButton)
{
	$(uploadButton).val("");
}

function ShowImage(uploadButton)
{
	checker = /fakepath/;
	if(uploadButton.value!="")
	{
		if(checker.test(uploadButton.value))
		{
			if(!/image\/\w+/.test(uploadButton.files[0].type))
			{  
				alert("请确保文件为图像类型.png,.jpg,.gif");  
				return false;
			} 
			var fr = new FileReader();
			fr.readAsDataURL(uploadButton.files[0]);
			fr.onload = function (e)
			{
				$(uploadButton).prev().attr("src", e.target.result);
			}	
		}
		else
		{
			if (/\.jpg$|\.png$|\.gif$/i.test(uploadButton.value)) 
			{
				$(uploadButton).prev().attr("src",uploadButton.value);
			}
			else
			{
				alert("请确保文件为图像类型.png,.jpg,.gif");  
				return false;
			}
		}
	}
}

$(document).ready(function()
{
	$("#confirmbutton").click(function()
	{
		var baseUrl = $("#hiddenInput").val();
		if(!$("#uploadcontentimage").val())
		{
			alert("请上传内容图片!");
			return false;
		}
		
		if($("#officialtempletediv").css("display")!="none")
		{
			if($("#itemselecteddiv").length<=0)
			{
				alert("请选择模版图片!");
				return false;
			}
		}
		else if($("#uploadtempletediv").css("display")!="none")
		{
			if(!$("#uploadtempleteimage").val())
			{
				alert("请上传模版图片!");
				return false;
			}
		}
		
		var formData = new FormData();
		formData.append("content",$("#uploadcontentimage")[0].files[0]);
		if($("#officialtempletediv").css("display")!="none")
		{
			var stylepath = $("#itemselecteddiv").prev().attr("src");
			stylepath = stylepath.replace(baseUrl,"");
			formData.append("stylepath",stylepath);
		}
		else if($("#uploadtempletediv").css("display")!="none")
		{
			formData.append("style",$("#uploadtempleteimage")[0].files[0]);
		}
		var currentTime= new Date();
		$.ajax(
		{
			url: baseUrl+"/createResult",
			type: 'POST',
			data: formData,
			dataType: 'JSON',
			cache:false,
			processData:false,
			contentType:false,
			async:true,
			beforeSend:function(XMLHttpRequest)
			{   
		          $("#resultdiv img").attr("src",baseUrl+"/resources/images/loading.gif"); //在后台返回success之前显示loading图标  
		          $("#resultdiv img").css("height","8%");
		          $("#resultdiv img").css("top","46%");
			},   
			success:function(data)
			{
				if(data.success==true)
				{	
					$("#resultdiv img").attr("src", data.fileName);
					$("#resultdiv img").css("height","60%");
					$("#resultdiv img").css("top","20%");
					console.log(parseInt(new Date()-currentTime)/1000);
				}
				else
				{
					$("#resultdiv img").attr("src", baseUrl+"/resources/images/result.jpeg");
					$("#resultdiv img").css("height","60%");
					$("#resultdiv img").css("top","20%");
					alert("很抱歉,服务器出错!");
				}
			},
			error:function(data)
			{
				$("#resultdiv img").attr("src", baseUrl+"/resources/images/result.jpeg");
				$("#resultdiv img").css("height","60%");
				$("#resultdiv img").css("top","20%");
				alert("很抱歉,服务器出错!");
			}
		});
	});
});


