 $(document).bind('mobileinit', function() {
 
 // Disable Error Message
   $.extend(  $.mobile , {
      pageLoadErrorMessage: ""
  });
 
 // $.mobile.page.prototype.options.headerTheme  = "b"; 
 // $.mobile.page.prototype.options.footerTheme  = "b";
 // $.mobile.page.prototype.options.navbarTheme  = "b";  
 
 $.mobile.defaultTransition = 'slide';
 
 $.mobile.listview.prototype.options.dividerTheme = "a";
 
 
 //Erlaubt externe Seiten zu laden
 // $.support.cors = true;
 // $.mobile.allowCrossDomainPages = true;
 // // Problem Pagetransitions
 // $.mobile.defaultPageTransition = 'none'; 
 // $.mobile.pushStateEnabled = false; 
 
 /*enable touchOverflow on all browsers
 $.support.touchOverflow = true;
 $.mobile.touchOverflowEnabled = true;*/
 //$.mobile.phonegapNavigationEnabled = true
//$.mobile.touchOverflowEnabled = true;


 });   
