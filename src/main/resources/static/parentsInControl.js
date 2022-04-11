$( document ).ready(function() {

  $("#animatebutton").click(function(){
    const element = document.querySelector('.animatebutton');
    element.classList.add('animated', 'rotateIn');
    setTimeout(function() {
      element.classList.remove('rotateIn');
    }, 1000);
    $('#begin').fadeOut(1500);
    setTimeout(function() {
      $('#menu').fadeIn(2000);
    }, 1500);
    });
});
