jQuery(function($) {
var names = ['Test case pass', 'Test case fail', 'Test case in progress', 
'Test case blocked', 'Test case NA', 'Total Execution', 'Issues Found'],
values = [8, 4, 9, 12, 11, 10, 20],
chart,
width = 400,
bar_height = 20,
 height = bar_height * names.length;
 
var x, y;
 
x = d3.scale.linear()
.domain([0, d3.max(values)])
.range([0, width]);
 
y = d3.scale.ordinal()
.domain(values)
.rangeBands([0, height]);

var color = d3.scale.category10();
 
var left_width = 100;
 
var gap = 2;
y = d3.scale.ordinal()
.domain(values)
.rangeBands([0, (bar_height + 2 * gap) * names.length]);
 
 
chart = d3.select($("#horizontal_bar_graph")[0])
.append('svg')
.attr('class', 'chart')
.attr('width', left_width + width + 40)
.attr('height', (bar_height + gap * 2) * names.length + 80)
.append("g")
.attr("transform", "translate(10, 20)");

 
chart.selectAll(".rule")
.data(x.ticks(d3.max(values)))
.enter().append("text")
.attr("class", "rule")
.attr("x", function(d) { return x(d) + left_width; })
.attr("y", 0)
.attr("dy", 180)
.attr("text-anchor", "middle")
.attr("font-size", 10)
.text(String);
 
chart.selectAll("rect")
.data(values)
.enter().append("rect")
.attr("x", left_width)
.attr("y", function(d) { return y(d) + gap; })
.attr("width", x)
.attr("height", bar_height)
.style('fill', function(d) { 
          return color(d);
        });

 
}(jQuery));
 