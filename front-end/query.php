<?php

function query()
{
      $cluster   = Cassandra::cluster()
                     //->withContactPoints('127.0.0.1')
                     ->build();
      $session   = $cluster->connect("storm");
      $statement = new Cassandra\SimpleStatement("SELECT * FROM website_data");
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

}
query();
    ?>
