/*  This visualization was made possible by modifying code provided by:

Scott Murray, Choropleth example from "Interactive Data Visualization for the Web"
https://github.com/alignedleft/d3-book/blob/master/chapter_12/05_choropleth.html

Malcolm Maclean, tooltips example tutorial
http://www.d3noob.org/2013/01/adding-tooltips-to-d3js-graph.html

Mike Bostock, Pie Chart Legend
http://bl.ocks.org/mbostock/3888852  */


//Width and height of map
var width = 960;
var height = 500;

// D3 Projection
var projection = d3.geo
           .albersUsa()
				   .translate([width/2, height/2])    // translate to center of screen
				   .scale([1000]);          // scale things down so see entire US

// Define path generator
var path = d3.geo.path()               // path generator that will convert GeoJSON to SVG paths
		  	 .projection(projection);  // tell path generator to use albersUsa projection


// Define linear scale for output
var color = d3.scale.linear()
			  .range(["rgb(0,0,255)","rgb(255,0,0)"]);

//var legendText = ["Cities Lived", "States Lived", "States Visited", "Nada"];
var w = 140, h = 400;

var key = d3.select("body")
        .append("svg")
        .attr("width", w)
        .attr("height", h);

var legend = key.append("defs")
          .append("svg:linearGradient")
          .attr("id", "gradient").attr("x1", "100%")
          .attr("y1", "0%").attr("x2", "100%")
          .attr("y2", "100%").attr("spreadMethod", "pad");

			legend.append("stop")
            .attr("offset", "0%")
            .attr("stop-color", "rgb(255,0,0)")
            .attr("stop-opacity", 1);

			legend.append("stop")
            .attr("offset", "100%")
            .attr("stop-color", "rgb(0,0,255)")
            .attr("stop-opacity", 1);

			key.append("rect")
         .attr("width", w - 100)
         .attr("height", h - 100)
         .style("fill", "url(#gradient)")
         .attr("transform", "translate(0,10)");

			var y = d3.scale.linear().range([300, 0]).domain([-5, 5]);

			var yAxis = d3.svg.axis().scale(y).orient("right");

			key.append("g")
      .attr("class", "y axis")
      .attr("transform", "translate(41,10)")
      .call(yAxis)
      .append("text")
      .attr("transform", "rotate(-90)")
      .attr("y", 30)
      .attr("dy", ".71em")
      .style("text-anchor", "end")
      .text("Sentiment Score");




//Create SVG element and append map to the SVG
var svg = d3.select("body")
			.append("svg")
			.attr("width", width)
			.attr("height", height);

// Append Div for tooltip to SVG
var div = d3.select("body")
		    .append("div")
    		.attr("class", "tooltip")
    		.style("opacity", 0);

// Load in my states data!
d3.csv("../query.csv", function(data) {
color.domain([-1,1]); // setting the range of the input data

// Load GeoJSON data and merge with states data
d3.json("../us-states.json", function(json) {

// Loop through each state data value in the .csv file
for (var i = 0; i < data.length; i++) {

	// Grab State Name
	var dataState = data[i].state;

	// Grab data value
	var dataValue = data[i].sentiment;

	// Find the corresponding state inside the GeoJSON
	for (var j = 0; j < json.features.length; j++)  {
		var jsonState = json.features[j].properties.name;

		if (dataState == jsonState) {

		// Copy the data value into the JSON
		json.features[j].properties.visited = dataValue;

		// Stop looking through the JSON
		break;
		}
	}
}

// Bind the data to the SVG and create one path per GeoJSON feature
svg.selectAll("path")
	.data(json.features)
	.enter()
	.append("path")
	.attr("d", path)
	.style("stroke", "#fff")
	.style("stroke-width", "1")
	.style("fill", function(d) {

	// Get data value
	var value = d.properties.visited;

	if (value) {
	//If value exists…
	return color(value);
	} else {
	//If value is undefined…
	return "rgb(213,222,217)";
	}
});


// Map the cities I have lived in!
/*d3.csv("cities-lived.csv", function(data) {

svg.selectAll("circle")
	.data(data)
	.enter()
	.append("circle")
	.attr("cx", function(d) {
		return projection([d.lon, d.lat])[0];
	})
	.attr("cy", function(d) {
		return projection([d.lon, d.lat])[1];
	})
	.attr("r", function(d) {
		return Math.sqrt(d.years) * 4;
	})
		.style("fill", "rgb(217,91,67)")
		.style("opacity", 0.85)

	// Modification of custom tooltip code provided by Malcolm Maclean, "D3 Tips and Tricks"
	// http://www.d3noob.org/2013/01/adding-tooltips-to-d3js-graph.html
	.on("mouseover", function(d) {
    	div.transition()
      	   .duration(200)
           .style("opacity", .9);
           div.text(d.place)
           .style("left", (d3.event.pageX) + "px")
           .style("top", (d3.event.pageY - 28) + "px");
	})

    // fade out tooltip on mouse out
    .on("mouseout", function(d) {
        div.transition()
           .duration(500)
           .style("opacity", 0);
    });
});*/

// Modified Legend Code from Mike Bostock: http://bl.ocks.org/mbostock/3888852

	});

});
