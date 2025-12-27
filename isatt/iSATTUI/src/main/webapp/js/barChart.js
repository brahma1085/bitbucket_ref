
var barMargin = {top: 60, right: 100, bottom: 60, left: 60},
    width = 300 - barMargin.left - barMargin.right,
    height = 286 - barMargin.top - barMargin.bottom;

var x0 = d3.scale.ordinal()
    .rangeRoundBands([0, width], .09);

var x1 = d3.scale.ordinal();

var y = d3.scale.linear()
    .range([height, 0]);

var barColor = d3.scale.ordinal()
    .range(["#98abc5", "#8a89a6", "#7b6888", "#6b486b"]);

var xAxis = d3.svg.axis()
    .scale(x0)
    .orient("bottom");

var yAxis = d3.svg.axis()
    .scale(y)
    .orient("left")
    .tickFormat(d3.format("20"));

var ondateTestcases = d3.select("#ondate_testcase").append("svg")
    .attr("width", width +200)
    .attr("height", height + barMargin.top + barMargin.bottom)
  .append("g")
    .attr("transform", "translate(" + barMargin.left + "," + barMargin.top + ")");

d3.json("js/data.json", function(error, data) {
  var ageNames = d3.keys(data[0]).filter(function(key) { return key !== "State"; });

  data.forEach(function(d) {
    d.ages = ageNames.map(function(name) { return {name: name, value: +d[name]}; });
  });

  x0.domain(data.map(function(d) { return d.State; }));
  x1.domain(ageNames).rangeRoundBands([0, x0.rangeBand()]);
  y.domain([0, d3.max(data, function(d) { return d3.max(d.ages, function(d) { return d.value; }); })]);

  ondateTestcases.append("g")
      .attr("class", "x axis")
      .attr("transform", "translate(0," + height + ")")
      .call(xAxis);

  ondateTestcases.append("g")
      .attr("class", "y axis")
      .call(yAxis)
    .append("text")
      .attr("transform", "rotate(-90)")
      .attr("y", -40)
	  .attr("x", -52)
      .attr("dy", ".71em")
      .style("text-anchor", "end")
      .text("Percentage");

  var state = ondateTestcases.selectAll(".state")
      .data(data)
    .enter().append("g")
      .attr("class", "g")
      .attr("transform", function(d) { return "translate(" + ((x0(d.State))) + ",0)"; });

  state.selectAll("rect")
      .data(function(d) { return d.ages; })
    .enter().append("rect")
      .attr("width", x1.rangeBand())
      .attr("x", function(d) { return x1(d.name); })
      .attr("y", function(d) { return y(d.value); })
      .attr("height", function(d) { return height - y(d.value); })
      .style("fill", function(d) { return barColor(d.name); })
	  .attr("width", 25);

  var ondateTestcasesLegend = ondateTestcases.selectAll(".legend")
      .data(ageNames.slice().reverse())
	  .enter().append("g")
      .attr("class", "legend")
      .attr("transform", function(d, i) { return "translate(0," + i * 20 + ")"; });

  ondateTestcasesLegend.append("rect")
      .attr("x", width +36)
      .attr("y", 88)
      .attr("width", 8)
      .attr("height", 8)
      .style("fill", barColor);

  ondateTestcasesLegend.append("text")
      .attr("x", width +104)
      .attr("y", 94)
      .attr("dy", ".15em")
      .style("text-anchor", "end")
      .text(function(d) { return d; });

});