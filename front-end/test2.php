<?php

function bb()
{
$cluster   = Cassandra::cluster()
               ->withContactPoints('127.0.0.1')
               ->build();
$session   = $cluster->connect("storm");
$statement = new Cassandra\SimpleStatement("SELECT * FROM storm_data");
$result    = $session->execute($statement);
$file = fopen('demo.csv', 'w');

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

bb();

?>
