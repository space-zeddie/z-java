/**
 * Created by citizenzer0 on 12/4/16.
 */
$(document).ready(function(){
    $("#adds").click(function(){
        var name = $("#name").val();
        var pass = $("#password").val();
        var role = $("#role").val();

        $("#link").attr("href", "/createuser?name=" + name + "&password=" + pass + "&role=" + role);
    });
})