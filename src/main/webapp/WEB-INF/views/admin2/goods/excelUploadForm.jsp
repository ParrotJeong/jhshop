<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />	
<!DOCTYPE html>
<%




%>

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
				url: '/admin2/goods/excelGoodsUpload.do',
				processData: false,
				contentType: false,
				cache: false,
				data: data,
				success: function(result){
					alert("업로드 완료");
					var tblresult = result;
					var str ="";

					$.each(tblresult, function (i){
						str += '<TR>'
						str += '<TD>' + tblresult[i].goods_title + '</TD><TD>' + tblresult[i].goods_id + '</TD><TD>' + tblresult[i].goods_isbn + '</TD>'
						str += '<TR>'
					});
					$("#excletable").append(str);




				}
			});

		}
		function fn_excel_image_data_upload(){
			var form = $("#excelImageForm")[0];
			var data = new FormData(form);
			$.ajax({
				enctype:"multipart/form-data",
				method:"post",
				url: '/admin2/goods/excelImageUpload.do',
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
<%--	<input   type="submit" value="엑셀 업로드">--%>
</form>


<br><br><br>
<%--상품이미지 정보엑셀업로드 : <br/>--%>
<%--<form name="excelImageForm" id="excelImageForm" enctype="multipart/form-data" method="post" >--%>
<%--	<input type="file" id="excelImageFile" name="excleImageFile" value="엑셀 이미지 업로드" /><br><br>--%>
<%--	<input  type='button' value='엑셀  이미지 업로드' onClick="fn_excel_image_data_upload()"/>--%>

<%--</form>--%>


<%--<div>--%>



<%--	<c:forEach var="item" items="${excelMap}" begin="0" end="10" step="1" varStatus="status">--%>
<%--		<TR>--%>
<%--			<TD>--%>
<%--				<stvrong>${item}</stvrong>--%>
<%--			</TD>--%>

<%--		</TR>--%>

<%--	</c:forEach>--%>
<%--</div>--%>



<div>
	<table border="1">
		<tr>
			<td>제목</td>
			<td>책ID</td>
			<td>ISBN</td>
			<td>이미지등록</td>
		</tr>
		<tr>
			<td>하이</td>
			<td>1234</td>
			<td>4444</td>
			<td>이미지등록</td>
		</tr><tr>
		<td>헬로우</td>
		<td>44443</td>
		<td>FFDDS</td>
		<td>이미지등록</td>
	</tr>
	</table>
	<table id="excletable">

	</table>
</div>
</body>