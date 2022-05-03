$( document ).ready(function() {
    showMessage('<strong>Success!</strong> You have been registered sucessfully!');

    setTimeout(function(){
       $('.closebtn').click();
    }, 5000);
});

var close = document.getElementsByClassName("closebtn");
for (i = 0; i < close.length; i++) {
  close[i].onclick = function(){
    var div = this.parentElement;
    div.style.opacity = "0";
    setTimeout(function(){ div.style.display = "none"; }, 600);
  }
}

function showMessage(msg){
  document.getElementById('alertMessage').innerHTML=msg;
  var alertBlock=document.getElementById('alert').style;
  alertBlock.display="block";
  alertBlock.backgroundColor="#27DDE8";
}

$(function(){
    $("#monthly").click(function(){
        $(this).attr("disabled", "disabled");
        $("#yearly").removeAttr("disabled");
        $("#addPlan").val("10€/month");
    });

    $("#yearly").click(function () {
        $(this).attr("disabled", "disabled");
        $("#monthly").removeAttr("disabled");
        $("#addPlan").val("100€/year");
    });
});

$('#ccn').on("keydown",makeSpace);

$('#expiration').on("keydown",insertSlash);

function insertSlash(e){
    var keynum=e.key;

    if (keynum==="Backspace"){
        document.getElementById('expiration').value='';
        return;
    }

    var expirDate=$('#expiration').val();
    if (expirDate.length==2)
        document.getElementById('expiration').value=expirDate+'/';
}


//makes space every 4 chars
function makeSpace(e){
    var keynum=e.key;

    if (keynum==="Backspace"){
        document.getElementById('ccn').value='';
        return;
    }

    var ccn=document.getElementById('ccn').value.replaceAll(" ","");
    var ccnPrev=document.getElementById('ccn').value.length;
  if (ccn.length%4==0&&(ccnPrev>1&&ccnPrev<19)){
      document.getElementById('ccn').value=document.getElementById('ccn').value+' ';
  }

};