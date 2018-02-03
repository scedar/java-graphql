
(function ($) {
    "use strict";


    /*==================================================================
    [ Focus input ]*/
    $('.input100').each(function(){
        $(this).on('blur', function(){
            if($(this).val().trim() != "") {
                $(this).addClass('has-val');
            }
            else {
                $(this).removeClass('has-val');
            }
        })    
    })
  
  
    /*==================================================================
    [ Validate ]*/
    var input = $('.validate-input .input100');

    $('.validate-form').on('submit',function(){
        var check = true;

        for(var i=0; i<input.length; i++) {
            if(validate(input[i]) == false){
                showValidate(input[i]);
                return false;
            }
        }

        document.getElementById('submit').innerHTML = "" +
            "<svg class='spinner' width='27px' height='27px' viewBox='0 0 66 66' xmlns='http://www.w3.org/2000/svg'>" +
            "   <circle class='path' fill='none' stroke-width='6' stroke-linecap='round' cx='33' cy='33' r='30'></circle>" +
            "</svg>";

        var ajaxConfig = {
            loader: 'submit',
            async: true,
            url: 'http://localhost:8080/auth',
            method: 'POST',
            dataType: 'json',
            data: {
                'email': document.getElementById('email').value,
                'password': document.getElementById('password').value
            },
            contentType: 'application/json',
            headers: 'Content-Type application/json',
            callback: function (response, ajaxConfig) {
                if (response.message === 'S100X: Authentication successful'){
                    localStorage.setItem('accessToken',response.accessToken);
                    window.location.href = 'graphiql.html';
                }else{
                    document.getElementById(ajaxConfig.loader).innerHTML = response.message;
                }
            }
        };

        ajaxSend(ajaxConfig);


        return false;
    });


    $('.validate-form .input100').each(function(){
        $(this).focus(function(){
           hideValidate(this);
        });
    });

    function validate (input) {
        if($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
            if($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
                return false;
            }
        }
        else {
            if($(input).val().trim() == ''){
                return false;
            }
        }
    }

    function showValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).addClass('alert-validate');
    }

    function hideValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).removeClass('alert-validate');
    }

    function ajaxSend(ajaxConfig){

        $.ajax({
            async: ajaxConfig.async,
            url: ajaxConfig.url,
            type: ajaxConfig.method,
            dataType: ajaxConfig.dataType,
            data: JSON.stringify(ajaxConfig.data),
            contentType: ajaxConfig.contentType,
            headers: ajaxConfig.headers,
            success:function (response) {
                console.log("post successful");
                ajaxConfig.callback(response, ajaxConfig);
            },
            error:function (xhr, errMsg, err) {
                console.log("Error XHR @ post: "+xhr.status + ": " + xhr.responseText);
                console.log("Error (error) @ post: "+errMsg + ": " + err);
            }
        });
    }
    
    
})(jQuery);