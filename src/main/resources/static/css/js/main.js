/**
 * Created by citizenzer0 on 12/4/16.
 */
$(document).ready(function(){
    //$('.regist').visibility(false);
    $("#adds").click(function(){
        var name = $("#name").val();
        var pass = $("#password").val();
        var role = $("#role").val();

        $("#link").attr("href", "/createuser?name=" + name + "&password=" + pass + "&role=" + role);
        //$('.regist').visibility(true);
    });
    $("#adds1").click(function(){
        var name = $("#name").val();
        var credits = $("#credits").val();
        var prof = $("#prof").val();
        var annotation = $("#annotation").val();
        $("#link1").attr("href", "/create-full?name=" + name + "&credits=" + credits + "&prof=" + prof + "&annot=" + annotation);
        //$('.regist').visibility(true);
    });
})