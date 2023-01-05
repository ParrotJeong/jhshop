<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />	
<!DOCTYPE html>

<meta charset="utf-8">
<head>
	<script  src="http://code.jquery.com/jquery-latest.min.js"></script>

	<script>
		function fn_excel_goods_data_upload(){
			var form = $("#excelUpForm")[0];
			var data = new FormData(form);
			$.ajax({
				enctype:"multipart/form-data",
				method:"post",
				url: '/etest1/excelGoodsUpload.do',
				processData: false,
				contentType: false,
				cache: false,
				data: data,
				success: function(result){
					alert(result);

				}
			});

		}
		function fn_excel_image_data_upload(){
			var form = $("#excelImageForm")[0];
			var data = new FormData(form);
			$.ajax({
				enctype:"multipart/form-data",
				method:"post",
				url: '/etest1/excelImageUpload.do',
				processData: false,
				contentType: false,
				cache: false,
				data: data,
				success: function(result){
					alert(result);

				}
			});

		}
	</script>
	<title>엑셀 업로드 폼</title>
</head>
<body>
상품정보엑셀업로드 : <br/>

<form name="excelUpForm" id="excelUpForm" enctype="multipart/form-data" method="post" >
	<input type="file" id="excelFile" name="excleFile" value="엑셀 업로드" /><br><br>
	<input  type='button' value='엑셀 업로드' onClick="fn_excel_goods_data_upload()"/>
</form>


<br><br><br>
상품이미지 정보엑셀업로드 : <br/>
<form name="excelImageForm" id="excelImageForm" enctype="multipart/form-data" method="post" >
	<input type="file" id="excelImageFile" name="excleImageFile" value="엑셀 이미지 업로드" /><br><br>
	<input  type='button' value='엑셀  이미지 업로드' onClick="fn_excel_image_data_upload()"/>

</form>
</body>