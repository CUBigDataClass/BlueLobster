<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Twitter Sentiment</title>
<meta http-equiv="refresh" content="60">
<link rel=stylesheet href="css/style.css">
</head>
<body>
  <?php


      $cluster   = Cassandra::cluster()
                     //->withContactPoints('127.0.0.1')
                     ->build();
      $session   = $cluster->connect("storm");
      $statement = new Cassandra\SimpleStatement("SELECT * FROM storm_data");
      $result    = $session->execute($statement);
      $session->close();
      $file = fopen('return.csv', 'w');

      	// save the column headers
      	fputcsv($file, array('id', 'state', 'sentiment'));


      	// save each row of the data
      	foreach ($result as $row)
      	{
      		fputcsv($file, $row);
      	}

      	// Close the file
      	fclose($file);


    ?>
    <script src="https://d3js.org/d3.v3.min.js"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <div class = "top">

    <div class="navlist">
        <a href = "index.php">Home</a>
        <a href = "about.html">About</a>
        <a href = "technologies.html">Technologies</a>
    </div>
      <h1>Twitter Sentiment Analysis</h1>
      <script src ="js/map.js" type="text/javascript"> </script>
    </div>

  </body>
    <footer>copyright 2017</footer>
</html>
