
var trendsMargin = {top: 20, right: 20, bottom: 40, left: 60},
    trendsWidth = 456 - trendsMargin.left - trendsMargin.right,
    trendsHeight = 300 - trendsMargin.top - trendsMargin.bottom;

var x = d3.scale.linear()
    .range([0, trendsWidth-100]);

var y = d3.scale.linear()
    .range([trendsHeight, 0]);
    
var trendsColor = d3.scale.category10();

var trendsxAxis = d3.svg.axis()
    .scale(x)
    .orient("bottom");

var trendsyAxis = d3.svg.axis()
    .scale(y)
    .orient("left");

var trendsline = d3.svg.line()	
	.interpolate("linear")
    .x(function(d) { return x(d.days); })
    .y(function(d) { return y(d.testRuns); });
    
var testRun_trends = d3.select("#trendsGraph").append("svg")
    .attr("width", trendsWidth + trendsMargin.left + trendsMargin.right+50)
    .attr("height", trendsHeight + trendsMargin.top + trendsMargin.bottom)
 	 .append("g")
    .attr("transform", "translate(" + trendsMargin.left + "," + trendsMargin.top + ")");

d3.json("js/trendsLineDatas.json", function(error, data) {
	trendsColor.domain(d3.keys(data[0]).filter(function(key) {return key !== "days"; }));
  data.forEach(function(d) {
    d.days = parseInt(d.days);
  });

var status = trendsColor.domain().map(function(name){
	return{
		name: name,
		values: data.map(function(d){
			return {days: d.days, testRuns: +d[name]};
		}) 
	}
});
  x.domain(d3.extent(data, function(d) { return d.days; }));

  y.domain([0, d3.max(status, function(c) { return d3.max(c.values, function(v) { return v.testRuns; }); })]);
  
  testRun_trends.append("g")
      .attr("class", "x axis")
      .attr("transform", "translate(0," + trendsHeight + ")")
      .call(trendsxAxis)
	  .append("text")
      .attr("y", 30)
	  .attr("x", 189)
      .attr("dy", ".71em")
      .style("text-anchor", "end")
      .text("Number of Test Runs");

  testRun_trends.append("g")
      .attr("class", "y axis")
      .call(trendsyAxis)
    .append("text")
      .attr("transform", "rotate(-90)")
      .attr("y", -41)
	  .attr("x",-53)
      .attr("dy", ".71em")
      .style("text-anchor", "end")
      .text("Number of");

var trendsLegend = testRun_trends.selectAll('.legend')
         .attr("transform", "translate(" + (trendsWidth - 30) + ",20)")
        .data(status)
        .enter()
      .append('g')
        .attr('class', 'legend');
        
    trendsLegend.append("path")
      .attr("class", "line")
      .attr("d", function(d) { return trendsline(d.values); })
      .style("stroke", function(d) { return trendsColor(d.name); });

    trendsLegend.append('rect')
        .attr('x', trendsWidth - 30)
        .attr('y', function(d, i){ return (i *  20) + 93;})
        .attr('width', 10)
        .attr('height', 10)
        .style('fill', function(d) { 
          return trendsColor(d.name);
        });

    trendsLegend.append('text')
        .attr('x', trendsWidth - 8)
        .attr('y', function(d, i){ return (i *  20) + 100;})
        .text(function(d){ return d.name; });
});