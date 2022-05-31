<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/cms/taglibrary" prefix="cms" %>
<script>
	$(document).ready(function(){
		$("#panelbar").kendoPanelBar({
            expandMode: "single"
        });
        //$("#panelbar").kendoTreeView();
		$("span.k-icon.k-i-arrow-s.k-panelbar-expand").remove();
		$("span.k-icon.k-i-arrow-n.k-panelbar-collapse").remove();
		//initializeMenu();
	});
	
	var onSelect = function(e) {
        // access the selected item via e.item (HTMLElement)

        // detach select event handler via unbind()
       // panelBar.data("kendoPanelBar").unbind("select", onSelect);
    };
</script>
<s:eval expression="T(postech.adms.common.context.CmsRequestContext).getCmsRequestContext().getCurrentMenu()" var="currentMenu" />
<div class="navigation">
 	<ul id="panelbar" style="height:700px">
 		<cms:menu menuId="1" isLeaf="false" />
 		<c:forEach var="result" items="${result}">
 			<c:choose>
 				<c:when test="${currentMenu ne null && currentMenu.parentMenu.menuId eq result.menuId }">
 					<li class="k-state-active icon">${result.name} 
       			</c:when>
 				<c:otherwise>
        			<li class="icon">${result.name}
    			</c:otherwise>
 			</c:choose>
 				<cms:menu menuId="${result.menuId}" isLeaf="true"/>
 				<c:if test="${fn:length(result) > 0}">
 				<ul>
 					<c:forEach var="subResult" items="${result}">
 						<c:choose>
			 				<c:when test="${currentMenu ne null && currentMenu.menuId eq subResult.menuId }">
			 					<li class="on"><a href="${subResult.menuPath}" style="padding:9px"><span class="k-link k-state-selected" style="background: #cfebf3;">${subResult.name}</span></a></li>
			       			</c:when>
			 				<c:otherwise>
			        			<li ><a href="${subResult.menuPath}" style="padding:3px 30px 11px 27px;">${subResult.name}</a></li>
			    			</c:otherwise>
			 			</c:choose>
 					</c:forEach>
 				</ul>
 				</c:if>
 			</li>
 		</c:forEach>
 	</ul>
</div>


