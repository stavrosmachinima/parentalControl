var popup=  document.getElementById('myModal').style;

$( document ).ready(function() {
  var flag=document.getElementById('gmail').value;
  console.log(flag);
  if (document.getElementById('plan').innerHTML==''&&!flag)
    showMessage('<strong>Success!</strong> You have signed in sucessfully!');
  else if (flag)
    showMessage('<strong>Info!</strong> We are currently keylogging your computer');

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
  alertBlock.opacity="1";
  alertBlock.backgroundColor="#27DDE8";
}

function shadowEverything(){
  popup.display='block';
  popup.opacity='0.9';
  popup.background='black';
}

function closeForm(){
  popup.display='none';
  popup.opacity='0';
  popup.background='inherit';
}
