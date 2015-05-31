
$(function() {
		
		var dialog, button_id, form, name = $("#name");
		lastName = $("#lastName");
		phoneNumber = $("#phoneNumber");
		phoneNumber.mask('(999) 999-9999');

		ident = $("#ident");
		allFields = $([]).add(name).add(lastName).add(phoneNumber);
		tips = $(".validateText");

		// 	update tips
		function updateTips(t) {
			tips.text(t).addClass("ui-state-highlight");
			setTimeout(function() {
				tips.removeClass("ui-state-highlight", 1500);
			}, 500);
		}

		/*
		 * Check min and max length of input
		 */
		function checkLength(o, n, min, max) {
			if (o.val().length > max || o.val().length < min) {
				Recaptcha.reload();
				o.addClass("ui-state-error");
				updateTips("Length of " + n + " must be between " + min
						+ " and " + max + ".");
				return false;
			} else {
				return true;
			}
		}

		/*
		 * Chech regex for validation
		 */
		function checkRegexp(o, regexp, n) {
			if (!(regexp.test(o.val()))) {
				Recaptcha.reload();
				o.addClass("ui-state-error");
				updateTips(n);
				return false;
			} else {
				return true;
			}
		}

		function addUser() {
			var valid = true;
			allFields.removeClass("ui-state-error");
			valid = valid && checkLength(name, "Name", 3, 80);
			valid = valid && checkLength(lastName, "Last Name", 3, 80);
			valid = valid && checkLength(phoneNumber, "Phone Number", 5, 16);

			valid = valid
					&& checkRegexp(name, /^[a-zA-Z ]{2,30}$/,
							"Name may consist of a-z, underscores, spaces and must begin with a letter and minimum 2 characters.");
			valid = valid
					&& checkRegexp(
							lastName,
							/^[a-zA-Z]([a-zA-Z_\s])+$/i,
							"Last Name may consist of a-z,  underscores, spaces and must begin with a letter and minimum 2 characters.");
			if (valid) {
				$.ajax({
							url : "user/save",
							type : "POST",
							data : {
								"name" : document.getElementById("name").value,
								"lastName" : document
										.getElementById("lastName").value,
								"phoneNumber" : document
										.getElementById("phoneNumber").value,
								"id" : document.getElementById("ident").value
							},
							success : function(msg) {
								window.location.reload();
							},

							error : function(xhr, ajaxOptions, thrownError) {
								alert(xhr.status);
								alert(thrownError);
							}
						});
			}
			return valid;
		}
		/*
		 * Validate form
		 */
		dialog = $("#dialog-form")
				.dialog(
						{
							autoOpen : false,
							height : 500,
							width : 550,
							modal : true,
							resizable : false,
							buttons : {
								"Create an account" : function() {
									$('#loading').show();
									var challengeField = $(
											'input#recaptcha_challenge_field')
											.val(), responseField = $(
											'input#recaptcha_response_field')
											.val();
									$
											.ajax({
												type : "post",
												async : false,
												url : "user/validate",
												data : {
													"recaptcha_challenge_field" : challengeField,
													"recaptcha_response_field" : responseField
												},
												success : function(resp) {
													if (resp === "true") {
														addUser()
													} else {
														updateTips("Invalid Captcha")
														Recaptcha.reload();
													}
												},
												error : function(xhr,
														ajaxOptions,
														thrownError) {
													alert(xhr.status);
													alert(thrownError);
												}
											});
								},
								Cancel : function() {
									dialog.dialog("close");
								}
							},
							close : function() {
								$('#loading').hide();
								form[0].reset();
								allFields.removeClass("ui-state-error");
							}
						});
 
		$("#create-user").button().on("click", function() {
			dialog.dialog("open");

		});

		delete_dialog = $("#delete").dialog({
			autoOpen : false,
			height : 200,
			width : 350,
			modal : true,
			resizable : false,
			buttons : {
				Delete : function() {
					$('#loading').show();
					$.ajax({
						type : "post",
						url : "user/delete",
						data : {
							"id" : button_id
						},
						success : function(msg) {
							window.location.reload();
						},
						error : function(xhr, ajaxOptions, thrownError) {
							alert(xhr.status);
							alert(thrownError);
						}
					});
					delete_dialog.dialog("close");

				},
				Cancel : function() {
					delete_dialog.dialog("close");
				}
			},
			close : function() {
				$('#loading').hide();
				form[0].reset();
				allFields.removeClass("ui-state-error");
			}
		});

		$(".delete_button").button().on("click", function() {
			button_id = $(this).attr('id');
			delete_dialog.dialog("open");

		});

		$(".update_button").button().on("click", function() {
			document.getElementById('ident').value = $(this).attr('data-id');
			dialog.dialog("open");

		});
		

	});