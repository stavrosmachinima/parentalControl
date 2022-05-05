  $("#glow").click(function(){
      document.getElementById("idAudio").play();
    $('#begin').fadeOut(1500);
    setTimeout(function() {
      $('#menu').fadeIn(2000);
    }, 1500);
    });
