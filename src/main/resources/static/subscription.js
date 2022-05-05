let data=true;
$( document ).ready(function() {
    showMessage('<strong>Success!</strong> You have been registered sucessfully!',data);

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

function validate(){
    // check if plan was picked
    let plan=$('#addPlan').val();
    if (plan===""){
        showMessage("<strong>Error!</strong> Please choose a plan.")
        return false;
    }
    // name validation
    let fullname=$('#fullname').val();
    if (fullname===""||/^[a-z||A-Z]+$/.test(fullname)===false){
        showMessage("<strong>Error!</strong> Your Fullname is of wrong type.");
        return false;
    }

    // credit card
    let ccn=$('#ccn').val();
    if (ccn===""||/[0-9\s]{19}/.test(ccn)===false){
        showMessage("<strong>Error!</strong> Your Credit Card Number is wrong.");
        return false;
    }

    // date validation
    let date=$('#expiration').val();
    if (date===""||/[0-9]{2}[/][0-9]{2}/.test(date)===false){
        showMessage("<strong>Error!</strong> Invalid Date");
        return false;
    }
    let dateArray=date.split('/');
    if (dateArray[0]>12)
    {
        showMessage("<strong>Error!</strong> Invalid Date");
        return false;
    }
    dateArray[1]="20"+dateArray[1];
    var dateGiven=new Date(dateArray[1],dateArray[0]);
    if (dateGiven<new Date())
    {
        showMessage("<strong>Error!</strong> Expired Credit card");
        return false;
    }

    // ccv check
    let cvv=$('#cvv').val();
    if (cvv===""||/[0-9]{3}/.test(cvv)===false){
        showMessage("<strong>Error!</strong> Your Card Verification Value is wrong.");
        return false;
    }
    return true;
};

function showMessage(msg){
  document.getElementById('alertMessage').innerHTML=msg;
  var alertBlock=document.getElementById('alert').style;
  alertBlock.display="block";
  alertBlock.opacity="1";
  if (data)
  {
      alertBlock.backgroundColor="#27DDE8";
      data=false;
  }
  else
      alertBlock.backgroundColor="#ff0000";
    setTimeout(function(){
        $('.closebtn').click();
    }, 5000);
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
    var expirDate=$('#expiration').val();

    if (expirDate.length==2)
        document.getElementById('expiration').value=expirDate+'/';

    if (keynum==="Backspace"){
        document.getElementById('expiration').value='';
        return;
    }
    else if (keynum=='/')
        document.getElementById('expiration').value=expirDate;
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