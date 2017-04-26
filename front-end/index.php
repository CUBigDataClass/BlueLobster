<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Twitter Sentiment</title>
<link rel=stylesheet href="style.css">
</head>
<body>
    <div class = "top">
        
    <div class="navlist">
        <a href="index.html">Home</a>
        <a href="about.html">About</a>
        <a>Tech Stack</a>
    </div>
    <h1>Twitter Sentiment Analysis</h1>
    <script src="https://d3js.org/d3.v3.min.js"></script>
    <script src ="map.js" type="text/javascript"> </script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script type = "text/javascript">
function myAjax () {
$.ajax( { type : 'POST',
          data : { },
          url  : 'test2.php',              // <=== CALL THE PHP FUNCTION HERE.
          success: function ( data ) {
            //alert( data );               // <=== VALUE RETURNED FROM FUNCTION.
          },
          error: function ( xhr ) {
            alert( "error" );
          }
        });
}
    </script>    

 
        
     <button onclick="myAjax()">Click here</button> <!-- BUTTON CALL PHP FUNCTION -->
    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas sollicitudin tincidunt ex, at elementum enim tristique ut. Proin tempor a diam accumsan varius. Duis semper efficitur leo non consectetur. Maecenas tempus eros eu iaculis mattis. Ut eu convallis sapien, vitae imperdiet neque. Suspendisse vitae augue rhoncus, egestas sapien accumsan, vestibulum ligula. Donec cursus porttitor sapien in sodales. Phasellus erat massa, fermentum id sagittis non, finibus at mauris. Aliquam vitae diam quis enim euismod posuere. Etiam nunc turpis, gravida vitae dapibus in, efficitur vel nibh. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nam scelerisque leo vitae placerat dignissim. Morbi et enim et nunc elementum sagittis id id sapien. Donec congue ex nec nulla congue iaculis. Quisque id luctus dui. Phasellus ultricies lectus arcu, in lobortis justo laoreet nec.

Phasellus vehicula laoreet porttitor. Nullam congue ligula non posuere feugiat. Curabitur tristique eget tellus maximus eleifend. Nunc sed vulputate enim. In dapibus lorem quis malesuada fringilla. Praesent a sagittis risus, vitae eleifend risus. Donec eget rutrum tortor. Quisque ligula est, ornare non ornare non, tempus vel odio. Fusce non dolor a arcu lacinia volutpat. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos.

Quisque eleifend elementum dolor, in mattis neque mollis sed. Aliquam porttitor massa non est hendrerit, vitae commodo quam placerat. Donec tincidunt mi est, id euismod tortor sollicitudin vel. Duis imperdiet, nisi in pellentesque rutrum, nisi elit lacinia arcu, sit amet porta mauris est sit amet ipsum. Curabitur eget rhoncus metus. Nulla facilisi. Vivamus sollicitudin urna velit. Pellentesque finibus vel magna nec euismod. Vivamus finibus eros in purus porttitor, id vestibulum purus vehicula. Fusce non tellus non mauris tempus dapibus. Quisque erat nibh, ultrices vitae urna ac, pharetra porttitor sem. Nunc justo elit, pulvinar nec maximus in, malesuada quis nunc. Nunc condimentum non nulla id blandit. Nam arcu lectus, varius vel ante fringilla, vehicula ultricies nunc. Fusce porta vulputate facilisis.

Sed ullamcorper tortor arcu, vel maximus ipsum aliquet vel. Proin lacinia quam eu sem semper scelerisque. Vestibulum porttitor facilisis mattis. Proin a diam at sapien vulputate eleifend non quis magna. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis elementum lobortis faucibus. Nulla egestas, ex eu faucibus blandit, tortor justo interdum nibh, ac pretium nunc mi nec metus. Maecenas a libero suscipit, suscipit erat id, rhoncus enim. Sed fermentum mauris dolor, eget blandit dolor rhoncus non. Proin suscipit rutrum quam.

Vestibulum euismod nec nunc et cursus. Praesent vitae suscipit enim, id sagittis lorem. Nunc eget fermentum leo. Nunc non magna bibendum, tincidunt leo vitae, aliquam ligula. Praesent fermentum viverra elit, mollis rutrum erat. Curabitur leo neque, rutrum non magna id, tincidunt aliquam elit. Quisque mi diam, malesuada a tortor at, ultricies venenatis felis. Proin et suscipit lectus. Nam justo turpis, condimentum id mollis ut, elementum vel dui. Vivamus id nibh sapien. Nam porta viverra dui sed porttitor. Nunc fermentum quam dolor, ut accumsan enim laoreet in. Fusce quis iaculis sapien. Suspendisse blandit vestibulum lacus, et cursus justo aliquet at. Fusce lobortis faucibus libero, vitae vulputate ligula condimentum in. Nulla tempor metus sit amet iaculis tempor.

</p>
    </div>
    
</body>
    <footer>copyright 2017</footer>
</html>

