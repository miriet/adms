<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script>
$(document).ready(function(){
	
	$("#btnSave").kendoButton({
		click : function(){
			save();
		}
	});
	
	$("#btnClose").kendoButton({
		click : function(){
			parent.$("#writeFormLayer").data("kendoWindow").close();
		}
	});
	
	var menuTree = {
            transport: {
                read: {
                    url: function(options) {
                        return kendo.format("/common/menuPermissionTree/{0}?groupId=" + $("#groupId").val(), options.menuId);
                    },
					dataType: "json"
                }
            },
            schema: {
            	data: "result",
                model: {
                	 hasChildren: "hasChildren",
                     id: "menuId",
                     children: "menuPermissionTree"
                }
            }
        };
	
	var rootMenus = new kendo.data.HierarchicalDataSource({
        transport: {
            read: {
                url: "/common/rootMenuTree",
                dataType: "json"
            }
        },
        schema: {
        	data: "result",
            model: {
                hasChildren: "hasChildren",
                id: "menuId",
                children: menuTree,
                expanded: true
            }
        }
    });
	
	$("#treeview").kendoTreeView({
		checkboxes: {
            checkChildren: true
        },
        check: onCheck,
        dataSource: rootMenus,
        dataTextField: ["menuName", "menuName", "permissionName"]
    });
});

function save(){
	var checkedNodes = [];
	var treeView = $("#treeview").data("kendoTreeView");
    
	if(confirm('저장하시겠습니까?')){
		$.showLoadingMessage();
		
		checkedNodeIds(treeView.dataSource.view(), checkedNodes);
		
		$(checkedNodes).each(function(index,element){
			$("#inputForm").append("<input type='hidden' name='selectedPermission' value='" + element + "' />");
		});
		
		$("#inputForm").ajaxSubmit({
			url: "/group/setAuth",
			type:'POST',
			success:function(data, statusText, xhr){
				$.showClosingMessage();
				alert("저장되었습니다.");
				parent.fnSearch();
				parent.$("#writeFormLayer").data("kendoWindow").close();
			}
		});
	}

	//alert(message);
}

function onCheck(){
	/*  var checkedNodes = [],
    treeView = $("#treeview").data("kendoTreeView"),
    message;

	checkedNodeIds(treeView.dataSource.view(), checkedNodes);
	
	if (checkedNodes.length > 0) {
	    message = "IDs of checked nodes: " + checkedNodes.join(",");
	} else {
	    message = "No nodes checked.";
	}

	console.log(message); */
	//$("#result").html(message);
}

function checkedNodeIds(nodes, checkedNodes) {
    for (var i = 0; i < nodes.length; i++) {
        if (nodes[i].checked && !nodes[i].hasChildren) {
            checkedNodes.push(nodes[i].permissionId);
        }

        if (nodes[i].hasChildren) {
            checkedNodeIds(nodes[i].children.view(), checkedNodes);
        }
    }
}

</script>
</head>
<body class="index">
<form id="inputForm" name="inputForm" method="post" >
	<input type="hidden" id="groupId" name="groupId" value="${groupId}"/>
	set Auth form
	
	<div id="treeview" style="border:1px solid; border-color:#e6e6e6"></div>
	
	<div class="btnModifyMenu mt10" style="text-align: right;">
		<span id="btnSave" class="button blue Large"><input type="button" value="저장"></span>
		<span id="btnClose" class="button black Large"><input type="button" value="닫기"></span>
	</div>
</form>
</body>
</html>
