<#assign baseUrl=request.getContextPath()>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="utf-8">
	    <title>主页</title>
	    <link rel="stylesheet" type="text/css" href="${baseUrl}/resources/css/index.css">
	    <script type="text/javascript" src="${baseUrl}/resources/js/jquery-3.3.1.min.js"></script>
	    <script type="text/javascript" src="${baseUrl}/resources/js/index.js"></script>
	</head>
	<body>
		<input id="hiddenInput" type="hidden" value="${baseUrl}"/>
	    <div id="header">
	        <div id="image-nav">
	            <img src="${baseUrl}/resources/images/logo.png" />
	        </div>
	        <div id="label-nav">
	            <div id="label1">HOME</div>
	            <div id="label2">CONTACT</div>
	        </div>
	    </div>
	    <div id="webbody">
	        <div id="inputbody">
	            <div id="uploadimagelabel">
	            	<div class="yellowline"></div>
	            	<div class="fontlabel">传入图片</div>
	            </div>
	            <div id="uploadimage">
	            	<div id="uploadimagebutton">
	            		<img src="${baseUrl}/resources/images/uploadimage.png"/>
	            		<input id="uploadcontentimage" type="file" onclick="clearImage(this)" onchange="ShowImage(this)"/>
	            	</div>
	            </div>
	            <div id="middleline">
	            	<div class="line1"></div>
	            	<div class="fontlabel">选择模板</div>
	            	<div class="line2"></div>
	            </div>
	            <div id="choosetempletediv">
	            	<div id="templetebuttondiv">
	            	    <button id="officialtempletebutton">官方模板</button>
	        			<button id="uploadtempletebutton">上传模板</button>
	            	</div>
	            	<div id="officialtempletediv">
	            		<div class="templeteimage"><img src="${baseUrl}/resources/images/style1.jpg"/></div>
	            		<div class="templeteimage"><img src="${baseUrl}/resources/images/style2.jpg"/></div>
	            		<div class="templeteimage"><img src="${baseUrl}/resources/images/style3.jpg"/></div>
	            		<div class="templeteimage"><img src="${baseUrl}/resources/images/style4.jpg"/></div>
	            		<div class="templeteimage"><img src="${baseUrl}/resources/images/style5.jpg"/></div>
	            		<div class="templeteimage"><img src="${baseUrl}/resources/images/style6.jpg"/></div>
	            		<div class="templeteimage"><img src="${baseUrl}/resources/images/style7.jpg"/></div>
	            		<div class="templeteimage"><img src="${baseUrl}/resources/images/style8.jpg"/></div>
	            		<div class="templeteimage"><img src="${baseUrl}/resources/images/style9.png"/></div>
	            		<div class="templeteimage"><img src="${baseUrl}/resources/images/style10.jpg"/></div>
	            		<div class="templeteimage"><img src="${baseUrl}/resources/images/style11.jpg"/></div>
	            		<div class="templeteimage"><img src="${baseUrl}/resources/images/style12.jpg"/></div>
	            	</div>
	            	<div id="uploadtempletediv">
	            		<img src="${baseUrl}/resources/images/uploadimage.png"/>
	           			<input id="uploadtempleteimage" type="file" onchange="ShowImage(this)"/>
	            	</div>
	            	<div id="confirmdiv">
	            		<button id="confirmbutton">确&nbsp;&nbsp;认</button>
	            	</div>
	            </div>
	        </div>
	        <div id="outputbody">
	        	<div id="resultdiv">
	        		<img src="${baseUrl}/resources/images/result.jpeg"/>
	        	</div>
	        	<div id="downloaddiv">
	        		<div id="linediv"></div>
	        		<div id="labeldiv">效果实现</div>
	        		<div id="downloadpicturediv">
	        			<img src="${baseUrl}/resources/images/download.png"/>
	        		</div>
	        	</div>
	        </div>
	    </div>
	</body>

</html>