
var date = new Date();
(function($){
	$.commonlib = {
			options : {},
			dateOptions : {
				min: new Date(1980, 1, 1),
				max: new Date(9999, 12, 31),
				change: function() {},
				
				// Stats options
				icon: "/pages/images/ion/icon/ico_cal01.png",
				minYear: "1980",
				maxYear: "9999",
		  		valueYear: date.getFullYear(), 	//현재날짜로 설정
		  		nowYear: date.getFullYear(),
		  		nowMonth: date.getMonth(),
		  		nowDate: date.getDate()
			},
			
			
			_damAlert :function(message){
				/*$("body").append("<div id='damAlertWindow' style='overflow: hidden;' ><p id='damAlertMessage'></p><a id='btnDamAlertOk' >확인</a></div>");
				var kendoWindow = $("#damAlertWindow").kendoWindow({
					title: false,
					resizable: false,
		            modal: true,
		            width: "460px",
		        	height: "200px",
		            //content : "<p id='damAlertMessage'></p><a id='btnDamAlertOk'>확인</a>",
		            open : function(){
		            	$("#damAlertMessage").html(message);
		            	$("#btnDamAlertOk").kendoButton({
		        			click : function(){
		        				$("#damAlertWindow").data("kendoWindow").close();
		        			}
		        		});
		            }
				}).data("kendoWindow");
				
				kendoWindow.center().open();*/
				alert(message);
			},
			
			_damConfirm :function(message,callback){
				$("body").append("<div id='damConfirmWindow' style='overflow: hidden;' ><p id='damConfirmMessage'></p><a id='btnDamConfirmOk' >확인</a><a id='btnDamConfirmCancel' >취소</a></div>");
				var kendoWindow = $("#damConfirmWindow").kendoWindow({
					title: false,
					resizable: false,
		            modal: true,
		            width: "460px",
		        	height: "200px",
		            //content : "<p id='damAlertMessage'></p><a id='btnDamAlertOk'>확인</a>",
		            open : function(){
		            	$("#damConfirmMessage").html(message);
		            	$("#btnDamConfirmOk").kendoButton({
		        			click : function(){
		        				callback;
		        			}
		        		});
		            	
		            	$("#btnDamConfirmCancel").kendoButton({
		        			click : function(){
		        				$("#damConfirmWindow").data("kendoWindow").close();
		        			}
		        		});
		            }
				}).data("kendoWindow");
				
				kendoWindow.center().open();
			},
			
			_showLoadingMessage : function(obj){
				if(obj == undefined){
					kendo.ui.progress($("body"), true);
				}else{
					kendo.ui.progress(obj, true);
				}
			},

		    _showClosingMessage : function(obj){
		    	if(obj == undefined){
		    		kendo.ui.progress($("body"), false);
		    	}else{
		    		kendo.ui.progress(obj, false);
		    	}
		    },
		    
		    _openErrorWindow : function(data){
		    	$("#errorMessage").html(data["message"]);
		    	$("#errorTrace").html(data["trace"]);
		    	$("#errorLayer").data("kendoWindow").open().center();
		    },
		    
		    _replaceAll : function(str,orgStr,newStr){
				return str.split(orgStr).join(newStr);
			},

			_openCpPopup : function (xPosition,yPosition){
				$("body").append("<div id='cpPopupLayer' style='overflow: hidden; padding: 0;'></div>");
				
				var windows = $("#cpPopupLayer").kendoWindow({
			        width: "500px",
			        height: "420px",
			        modal: true,
			        animation : {open :{effects:"expand:vertical fadeIn"},close :{effects:"expand:vertical fadeIn", reverse: true}},
			        visible : false,
			        actions: ["Minimize", "Maximize", "Close"],
			        title: "CP 찾기",
			        content : "/cp/searchForm",
			        iframe: true,
			        close: function() {
			        	$(this.element).parent().remove();
		           	}
			   	}).data("kendoWindow");
				
				if(xPosition == undefined){
					windows.center();
				}else{
					$("#cpPopupLayer").closest(".k-window").css({
					     top: yPosition,
					     left: xPosition,
					});
				}
		      	
				windows.open();
			},
			
			_closeCpPopup : function(){
				$("#cpPopupLayer").data("kendoWindow").close();
			},
			
			_checkFileExtension : function(e){
				$.each(e.files, function(index, value) {
					if((value.extension.toUpperCase() != ".JPG" && value.extension.toUpperCase() != ".JPEG" && value.extension.toUpperCase() != ".PNG")) {
				        e.preventDefault();
				        alert("Please upload jpg image files");
				   	}
				});
			},
			
			_checkContentsFileExtension : function(e){
				$.each(e.files, function(index, value) {
					if((value.extension.toUpperCase() != ".JPG" && value.extension.toUpperCase() != ".JPEG" && value.extension.toUpperCase() != ".PNG" && value.extension.toUpperCase() != ".GIF")) {
				        e.preventDefault();
				        alert("Please upload jpg image files");
				   	}
				});
			},
			
			_openWriterPopup : function (xPosition,yPosition){
				$("body").append("<div id='writerPopupLayer' style='overflow: hidden; padding: 0;'></div>");
				
				var windows = $("#writerPopupLayer").kendoWindow({
			        width: "500px",
			        height: "470px",
			        modal: true,
			        animation : {open :{effects:"expand:vertical fadeIn"},close :{effects:"expand:vertical fadeIn", reverse: true}},
			        visible : false,
			        actions: ["Minimize", "Maximize", "Close"],
			        title: "작가 찾기",
			        content : "/writer/searchForm",
			        iframe: true,
			        close: function() {
			        	$(this.element).parent().remove();
		           	}
			   	}).data("kendoWindow");
				
				if(xPosition == undefined){
					windows.center();
				}else{
					$("#writerPopupLayer").closest(".k-window").css({
					     top: yPosition,
					     left: xPosition,
					});
				}
		      	
				windows.open();
			},
			
			_validationSaveForm: function (uri, layout){
				if ($("#inputForm").data("kendoValidator").validate()) {
					if(confirm('저장하시겠습니까?')){
						$.showLoadingMessage();
								
						$("#inputForm").ajaxSubmit({
							url: uri,
							type:'POST',
							success:function(data, statusText, xhr){
								$.showClosingMessage();
								alert('success');
								parent.fnSearch();
								parent.$("#"+layout).data("kendoWindow").close();
							}
						});
					}
				}else{
					$("#inputForm").data("kendoValidator").hideMessages();
					var errors = $("#inputForm").data("kendoValidator").errors();
					
					$(errors).each(function(index, element){
						if(index == 0){
							alert(this);
							return;
						}
					});
					
					$('k-invalid:first').focus();
				}
			},
			
			_closeWriterPopup : function(){
				$("#writerPopupLayer").data("kendoWindow").close();
			},
			
			_openBookSearchPopup : function (xPosition,yPosition){
				$("body").append("<div id='bookPopupLayer' style='overflow: hidden; padding: 0;'></div>");
				
				var windows = $("#bookPopupLayer").kendoWindow({
			        width: "500px",
			        height: "500px",
			        modal: true,
			        animation : {open :{effects:"expand:vertical fadeIn"},close :{effects:"expand:vertical fadeIn", reverse: true}},
			        visible : false,
			        actions: ["Minimize", "Maximize", "Close"],
			        title: "작품정보 찾기",
			        content : "/contract/bookSearchForm",
			        iframe: true,
			        close: function() {
		           		$(this.element).parent().remove();
		           	}
			   	}).data("kendoWindow");
				
				if(xPosition == undefined){
					windows.center();
				}else{
					$("#bookPopupLayer").closest(".k-window").css({
					     top: yPosition,
					     left: xPosition,
					});
				}
		      	
				windows.open();
			},
			
			_closeBookSearchPopup : function(){
				$("#transcodingPopupLayer").data("kendoWindow").close();
			},
			
			_getCommonCode : function(obj,grpCd,inputType,defaultVal){
				var optionLabel = '';
				if(inputType == "L"){
					optionLabel = '전체';
				}else{
					optionLabel = '선택';
				}
				
				return $(obj).kendoDropDownList({
					dataSource: {
				        transport: {
				            read : {
				            	url : $.commonlib.options.contextPath + "/dam/common/code.json?grpCode=" + grpCd,
				            	dataType : "json"
				            }
				      	},
				      	schema: {
				            data: "result",
				            model: {   
					            fields: {
					            	commonCode: { type: "string" },
					            	commonCodeNm: { type: "string" }
					        	}
				            }
				      	}
				   	},
					dataTextField: "commonCodeNm",
					dataValueField: "commonCode",
					optionLabel: {
						commonCodeNm: optionLabel,
						commonCode: ""
					},
					index : 0,
					autoBind: false
				}).data('kendoDropDownList').value(defaultVal);
			},
			
			_getTranscodingFormat : function(obj,assetTp,inputType,defaultVal){
				var optionLabel = '';
				if(inputType == "L"){
					optionLabel = '전체';
				}else{
					optionLabel = '선택';
				}
				
				return $(obj).kendoDropDownList({
					dataSource: {
				        transport: {
				            read : {
				            	url : $.commonlib.options.contextPath + "/dam/common/format.json?assetTp=" + assetTp,
				            	dataType : "json"
				            }
				      	},
				      	schema: {
				            data: "result",
				            model: {   
					            fields: {
					            	transFmtId: { type: "string" },
					            	transFmt: { type: "string" }
					        	}
				            }
				      	}
				   	},
					dataTextField: "transFmt",
					dataValueField: "transFmtId",
					optionLabel: {
						transFmt: optionLabel,
						transFmtId: ""
					},
					index : 0,
					autoBind: false
				}).data('kendoDropDownList').value(defaultVal);
			},
			
			_getTemplate : function(templateId){
				var result = '';
				$.ajax({
					url: $.commonlib.options.contextPath + "/template/getTemplate.json?templateId=" + templateId,
					type:'GET',
					async: false,
					success:function(data, statusText, xhr){
						result = data['result'];
					}
				});
				
				return result;
			},
			
			_isObjectExist : function(obj){
				if($(obj).length > 0){
					return true;
				}else{
					return false;
				}
			},
			
			_objectToJSONString : function(object){
				var isArray = (object.join && object.pop && object.push
	                    && object.reverse && object.shift && object.slice && object.splice);
			    var results = [];
			 
			    for (var i in object) {
			        var value = object[i];
			         
			        if (typeof value == "object") 
			            results.push((isArray ? "" : "\"" + i.toString() + "\" : ")
			                             + this(value));
			        else if (value)
			            results.push((isArray ? "" : "\"" + i.toString() + "\" : ") 
			                            + (typeof value == "string" ? "\"" + value + "\"" : value));
			    }
			     
			    return (isArray ? "[" : "{") + results.join(", ") + (isArray ? "]" : "}");
			},
			
			_kendoDateField: function(obj, opts) {
				var options = $.extend($.commonlib.dateOptions, opts);
				
				var kendoDatePicker = $(obj).kendoDatePicker({
					format: "yyyy-MM-dd",
					culture: "ko-KR",
					min: options["min"],
					max: options["max"],
					value: options["value"],
					animation: {
						open: {
							effects: "fadeIn",
							duration: 300
						},
						close: {
							effects: "fadeOut",
							duration: 500
						}
					},
					footer: "오늘",
					open: options["open"],
					close: options["close"],
					change: options["change"]
				}).data("kendoDatePicker");
				
				return kendoDatePicker;
			},
			
			//Month Field
			_kendoMonthField: function(obj, opts) {
				var options = $.extend($.commonlib.dateOptions, opts);
				
				var kendoDatePicker = $(obj).kendoDatePicker({
					value: options["value"],
					format: "yyyy-MM", 
					culture: "ko-KR",
					start: "year",
		       	 	depth: "year", // 월까지만 출력가능
					min: options["min"],
		      		max: options["max"],	
		      		value: options["value"],
					animation: {
						open: {
							effects: "fadeIn",
							duration: 300
						},
						close: {
							effects: "fadeOut",
							duration: 500
						}
					},
					open: options["open"],
					close: options["close"],
					change: options["change"]
				}).data("kendoDatePicker");
				
				return kendoDatePicker;
			},
			
			_kendoYearField: function(obj, opts) {
				var options = $.extend($.commonlib.dateOptions, opts);
				var kendoYearPicker = $(obj).kendoDatePicker({
					 format: "yyyy",
					 culture: "ko-KR",
		        	 start: "decade",
		        	 depth: "decade", // 년도만까지만 출력가능
		       		 min: options["min"],
		       		 max: options["max"],
		       		 value: options["value"],
		       		 animation: {
						open: {
							effects: "fadeIn",
							duration: 300
						},
						close: {
							effects: "fadeOut",
							duration: 500
						}
					},
					footer: "올해",
					open: options["open"],
					close: options["close"],
					change: options["change"]
				}).data("kendoDatePicker");
				
				return kendoYearPicker;
			},
			
			_makeNoDataFoundRow : function(e){
				var grid = e.sender;
				if (grid.dataSource.total() == 0) {
					var colCount = grid.columns.length;
					$(e.sender.wrapper)
				    	.find('tbody')
				    	.append('<tr class="kendo-data-row"><td colspan="' + colCount + '" class="no-data" align="center">' + $.commonlib.options.noDataFoundMessage + '</td></tr>');
				}
			},
			
			_checkList: function(obj, list) {
				/* 전체선택/전체해제 버튼 이벤트 */
				$(obj).on("click", function(e) {
					if(!$(obj).prop("checked")) {
						$(list).prop("checked",false);
						//$(obj).prop("checked",false);
					}else{
						$(list).prop("checked",true);
						//$(obj).prop("checked",true);
					}
				});
			
				$(list).on("click", function(e) {
					var result = true;
					$(list).each(function(index, element) {
						if(!$(this).prop("checked")) {
							result = false;
							return false;
						}
					});
					
					if(!result) {
						$(obj).prop("checked",false);
					}else{
						$(obj).prop("checked",true);
					}
				});
			},
			
			_fieldErrorMessage : function(validator){
				validator.hideMessages();
				var errors = validator.errors();
				
				$(errors).each(function(index,element) {
					 if(index == 0){
			        	 alert(this);
			        	 return;
			         }
			    });
				
				$('input.k-textbox.k-invalid:first').focus();
			},
			
			_initErrorLayer : function(){
				$("#errorLayer").kendoWindow({
			        width: "1020px",
			        //height: "420px",
			        modal: true,
			        animation : {open :{effects:"expand:vertical fadeIn"},close :{effects:"expand:vertical fadeIn", reverse: true}},
			        visible : false,
			        actions: ["Minimize", "Maximize", "Close"],
			        title: "오류 메시지"
			   	});
				
				$('.btnError button').click(function(){
					console.log('test');
					if($(this).parent().hasClass('blue')){
						$(this).parent().removeClass('blue');
						$(this).text('Close');
						$(this).parent().parent().parent().find('.detailView').show();
					}else{
						$(this).parent().addClass('blue');
						$(this).text('Detail View');
						$(this).parent().parent().parent().find('.detailView').hide();
					}
				});
			},
			
			init : function(settings){
				$.commonlib.options 	= $.extend(true,$.commonlib.defaultOptions, settings);
				
				$.commonlib._initErrorLayer();
				
				$.ajaxSetup({
					type:'POST',
					dataType:'json',
					async: false,
					/*headers : {
						Accepts: "application/json; charset=utf-8",
						"Content-Type": "application/json; charset=utf-8" 
					},*/
					beforeSend :function(XMLHttpRequest){
						
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
						$.showClosingMessage();
						if(XMLHttpRequest.status == 401){
							alert('로그인을 하지 않았거나 세션정보가 만료되었습니다! 다시 로그인을 하십시요!');
							var form = $("form").get(0);
							$(form).attr("target","_self");
							$(form).attr("action",$.commonlib.options.homeUrl);
							$(form).submit();
						}if(XMLHttpRequest.status == 701){
							var result = eval("(" + XMLHttpRequest.responseText + ")");
							$.openErrorWindow(result);
						}else {
							var result = eval("(" + XMLHttpRequest.responseText + ")");
							$.openErrorWindow(result);
						}
					}
				});
				
				kendo.data.DataSource.prototype.options.error = function (e) {
					$.showClosingMessage();
					if(e.status == 401){
						alert('로그인을 하지 않았거나 세션정보가 만료되었습니다! 다시 로그인을 하십시요!');
						var form = $("form").get(0);
						$(form).attr("target","_self");
						$(form).attr("action",$.commonlib.options.homeUrl);
						$(form).submit();
					}if(e.status == 701){
						var result = $.parseJSON(e.xhr.responseText);
						$.openErrorWindow(result);
					}else {
						var result = $.parseJSON(e.xhr.responseText);
						$.openErrorWindow(result);
					}
				};
			}
		};
	
	$.commonlib.defaultOptions = {
		contextPath: "",
		imgPath : 	"/pages/images",
		dateType : "-",
		homeUrl : "http://localhost/",
		noDataFoundMessage : "등록된 데이터가 없습니다." 
	};
	
	$.fn.extend({
		getCommonCode : function(grpCd,inputType,defaultVal){
			return this.each(function(){
				(new $.commonlib._getCommonCode(this, grpCd,inputType,defaultVal));
			});
		},
		
		getTranscodingFormat : function(assetTp,inputType,defaultVal){
			return this.each(function(){
				(new $.commonlib._getTranscodingFormat(this, assetTp,inputType,defaultVal));
			});
		},
		
		makeYearField : function(opts) {
			return new $.commonlib._kendoYearField(this, opts); 
		},
		
		makeMonthField : function(opts) {
			return new $.commonlib._kendoMonthField(this, opts); 
		},
		
		makeDateField : function(opts) {
			return new $.commonlib._kendoDateField(this, opts); 
		},
		
		checkList : function(list){
			return new $.commonlib._checkList(this,list);
		}
	});
	
	$.openErrorWindow				= $.commonlib._openErrorWindow;
	$.showLoadingMessage    		= $.commonlib._showLoadingMessage;
	$.showClosingMessage    		= $.commonlib._showClosingMessage;
	//$.damAlert						= $.commonlib._damAlert;
	//$.damConfirm					= $.commonlib._damConfirm;
	$.fieldErrorMessage				= $.commonlib._fieldErrorMessage;
	$.strReplaceAll        	 		= $.commonlib._replaceAll;
	$.objectToJSONString			= $.commonlib._objectToJSONString;
	$.getTemplate					= $.commonlib._getTemplate;
	$.openCpPopup					= $.commonlib._openCpPopup;
	$.closeCpPopup					= $.commonlib._closeCpPopup;
	$.openWriterPopup				= $.commonlib._openWriterPopup;
	$.closeWriterPopup				= $.commonlib._closeWriterPopup;
	$.openBookSearchPopup			= $.commonlib._openBookSearchPopup;
	$.closeBookSearchPopup			= $.commonlib._closeBookSearchPopup;
	$.makeNoDataFoundRow			= $.commonlib._makeNoDataFoundRow;
	$.validationSaveForm			= $.commonlib._validationSaveForm;
	$.checkFileExtension			= $.commonlib._checkFileExtension;
	$.checkContentsFileExtension	= $.commonlib._checkContentsFileExtension;
	
})(jQuery);