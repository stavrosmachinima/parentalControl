$( document ).ready(function() {
  showMessage('<strong>Success!</strong> You have been registered sucessfully!');
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
