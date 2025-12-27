
var overviewMargin = {top: 40, right: 20, bottom: 50, left: 70},
    overviewWidth = 580 - overviewMargin.left - overviewMargin.right,
    overviewHeight = 276 - overviewMargin.top - overviewMargin.bottom;

var x = d3.scale.linear()
    .range([0, overviewWidth-100]);

var y = d3.scale.linear()
    .range([overviewHeight, 0]);
    
var overViewColor = d3.scale.category10();

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
    
var pastdateTestcases = d3.select("#pastdate_testcases").append("svg")
    .attr("width", overviewWidth+150)
    .attr("height", overviewHeight + overviewMargin.top + overviewMargin.bottom)
 	 .append("g")
    .attr("transform", "translate(" + overviewMargin.left + "," + overviewMargin.top + ")");

d3.json("js/overviewLineDatas.json", function(error, data) {
	overViewColor.domain(d3.keys(data[0]).filter(function(key) {return key !== "days"; }));
  data.forEach(function(d) {
    d.days = parseInt(d.days);
  });

var status = overViewColor.domain().map(function(name){
	return{
		name: name,
		values: data.map(function(d){
			return {days: d.days, testRuns: +d[name]};
		}) 
	}
});
  x.domain(d3.extent(data, function(d) { return d.days; }));

  y.domain([0, d3.max(status, function(c) { return d3.max(c.values, function(v) { return v.testRuns; }); })]);
  
  pastdateTestcases.append("g")
      .attr("class", "x axis")
      .attr("transform", "translate(0," + overviewHeight + ")")
      .call(xAxis)
	  .append("text")
	  .attr("y", 31)
	  .attr("x",112)
	  .style("font","1em HelveticaNeue")
      .text("Number of Days");

  pastdateTestcases.append("g")
      .attr("class", "y axis")
      .call(yAxis)
    .append("text")
      .attr("transform", "rotate(-90)")
      .attr("y", -54)
	  .attr("x",-37)
      .attr("dy", ".71em")
      .style("text-anchor", "end")
      .text("Number of Test Runs");

var pastdateTestcasesLegend = pastdateTestcases.selectAll('.legend1')
        .attr("transform", "translate(" + (overviewWidth - 30) + ",20)")
        .data(status)
        .enter()
        .append('g')
        .attr('class', 'legend1');
        
    pastdateTestcasesLegend.append("path")
      .attr("class", "line")
      .attr("d", function(d) { return line(d.values); })
      .style("stroke", function(d) { return overViewColor(d.name); });

    pastdateTestcasesLegend.append('rect')
        .attr('x', overviewWidth - 30)
        .attr('y', function(d, i){ return (i *  20) + 93;})
        .attr('width', 10)
        .attr('height', 10)
        .style('fill', function(d) { 
        return overViewColor(d.name);
        });

    pastdateTestcasesLegend.append('text')
        .attr('x', overviewWidth - 8)
        .attr('y', function(d, i){ return (i *  20) + 100;})
        .text(function(d){ return d.name; });
});