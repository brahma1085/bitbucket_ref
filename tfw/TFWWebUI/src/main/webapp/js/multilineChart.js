
var multilineMargin = {top: 40, right: 20, bottom: 50, left: 80},
    multilineWidth = 900 - multilineMargin.left - multilineMargin.right,
    multilineHeight = 320 - multilineMargin.top - multilineMargin.bottom;

var x = d3.scale.linear()
    .range([0, multilineWidth-250]);

var y = d3.scale.linear()
    .range([multilineHeight, 0]);
    
var multilineColor = d3.scale.category10();

var xAxis = d3.svg.axis()
    .scale(x)
    .orient("bottom");

var yAxis = d3.svg.axis()
    .scale(y)
    .orient("left");

var line = d3.svg.line()	
	.interpolate("linear")
    .x(function(d) { return x(d.days); })
    .y(function(d) { return y(d.testRuns); });
    
var applicationsGraph = d3.select("#application_status_graph").append("svg")
    .attr("width", multilineWidth +110)
    .attr("height", multilineHeight + multilineMargin.top + multilineMargin.bottom)
 	 .append("g")
    .attr("transform", "translate(" + multilineMargin.left + "," + multilineMargin.top + ")");

d3.json("js/lineChartData.json", function(error, data) {
	multilineColor.domain(d3.keys(data[0]).filter(function(key) {return key !== "days"; }));
  data.forEach(function(d) {
    d.days = parseInt(d.days);
  });

var status = multilineColor.domain().map(function(name){
	return{
		name: name,
		values: data.map(function(d){
			return {days: d.days, testRuns: +d[name]};
		}) 
	}
});
  x.domain(d3.extent(data, function(d) { return d.days; }));

	y.domain([0, d3.max(status, function(c) { return d3.max(c.values, function(v) { return v.testRuns; }); })]);
  applicationsGraph.append("g")
      .attr("class", "x axis")
      .attr("transform", "translate(0," + multilineHeight + ")")
      .call(xAxis)
	  .append("text")
	  .attr("y", multilineHeight-192)
	  .attr("x",multilineWidth-614)
	  .style("font","1em HelveticaNeue")
      .text("Number of Days");

  applicationsGraph.append("g")
      .attr("class", "y axis")
      .call(yAxis)
    .append("text")
      .attr("transform", "rotate(-90)")
      .attr("y", -56)
	  .attr("x",-54)
      .attr("dy", ".71em")
      .style("text-anchor", "end")
	  .style("font","1em HelveticaNeue")
      .text("Number of Test Runs");

	var applicationsGraphLegend = applicationsGraph.selectAll('.legend')
		.attr("transform", "translate(" + (multilineWidth - 30) + ",20)")
        .data(status)
        .enter().append('g')
        .attr('class', 'legend');
        
    applicationsGraphLegend.append("path")
      .attr("class", "line")
      .attr("d", function(d) { return line(d.values); })
      .style("stroke", function(d) { return multilineColor(d.name); });

    applicationsGraphLegend.append('rect')
        .attr('x', multilineWidth - 70)
        .attr('y', function(d, i){ return (i *  20) + 140;})
        .attr('width', 10)
        .attr('height', 10)
        .style('fill', function(d) { 
          return multilineColor(d.name);
        });

    applicationsGraphLegend.append('text')
        .attr('x', multilineWidth - 58)
        .attr('y', function(d, i){ return (i *  20) + 150;})
        .text(function(d){ return d.name; });

});