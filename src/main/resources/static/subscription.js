$(function(){
    $("#monthly").click(function(){
        $(this).attr("disabled", "disabled");
        $("#yearly").removeAttr("disabled");
    });

    $("#yearly").click(function () {
        $(this).attr("disabled", "disabled");
        $("#monthly").removeAttr("disabled");
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