// closes button with transition
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
  alertBlock.opacity="1";
}

$( document ).ready(function() {
  let msg='';
  let error=false;
  console.log(document.getElementById('checkIfWrongUserPasswordCombination'));
  var flag=document.getElementById('checkIfWrongUserPasswordCombination');
  if (flag)
  {
    if (flag.innerHTML=='true')
    {
        msg='<strong>Error!</strong> Wrong User-Password combination!<br/>';
        showMessage(msg);
    }
  }
  else{
    if (document.getElementById('checkIfUsernameAlreadyExists').innerHTML=='true')
    {
        error=true;
        msg='<strong>Error!</strong> An account with the same username is already defined!<br/>';
    }
    if (document.getElementById('checkIfEmailAlreadyExists').innerHTML=='true') {
      error=true;
      msg+='<strong>Error!</strong> An account with the same email is already defined!';
    }
    if (error)
      showMessage(msg);
  }

});

function validateForm() {
    var alertmsg1 = "You must fill the following required fields";
    var alertmsg2 = "";
    var passwordStrong=false;
    var d = document.getElementById('username').value;
    // to epomeno einai gia na metaferw to username tou sto url.
    if (d === "" || /^[a-z || A-Z || 0-9||.]+$/.test(d) === false) {
      alertmsg2 = alertmsg2 + "\nYour Username";
    }
    var b = document.getElementById('firstname').value;
    if (b === "" || /^[a-z || A-Z]+$/.test(b) === false) {
      alertmsg2 = alertmsg2 + "\nYour First Name";
    }
    var c = document.getElementById('lastname').value;
    if (c === "" || /^[a-z || A-Z]+$/.test(c) === false) {
      alertmsg2 = alertmsg2 + "\nYour Last Name";
    }
    var k = document.getElementById('email').value;
    if (k === "" || /[a-z || 0-9]+@[a-z]+.[a-z]{2,3}$/.test(k) === false) {
      alertmsg2 = alertmsg2 + "\nYour Email";
    }

var password1 = document.getElementById('password').value;
var password2 =document.getElementById('verifyPassword').value;

 // If password not entered
 if (password1 == '')
 {
    alertmsg2 = alertmsg2 + "\nYour Password";
}
 // If confirm password not entered
 if (password2 == '')
 {
    alertmsg2 = alertmsg2 + "\nYour Password verification";
}

 // Create an array and push all possible values that you want in password
 var matchedCase = new Array();
 matchedCase.push("[$@$!%*#?&]"); // Special Charector
 matchedCase.push("[A-Z]");      // Uppercase Alpabates
 matchedCase.push("[0-9]");      // Numbers
 matchedCase.push("[a-z]");     // Lowercase Alphabates

 // Check the conditions
 var ctr = 0;
 for (var i = 0; i < matchedCase.length; i++) {
     if (new RegExp(matchedCase[i]).test(password2)) {
         ctr++;
     }
 }
 // Display
 switch (ctr) {
     case 0:
     case 1:
     case 2:

        break;
     case 3:
     case 4:
         passwordStrong=true;
         break;
 }

    if (alertmsg2 != "") {
        alert(alertmsg1 + alertmsg2);
        alertmsg2 = "";
        return false;
      }else if (password1 != password2)
      {
        showMessage("<strong>Error!</strong> Your passwords don't much!");
        alertmsg2 = "";
        return false;
      }
       else if (!passwordStrong)
      {
        showMessage("<strong>Error!</strong> Your password is very weak!");
        alertmsg2 = "";
        return false;
      }else
        return true;
}
