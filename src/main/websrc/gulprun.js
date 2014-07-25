var gulpfile = require("./gulpfile.js");
var glog = require('gulp-api-log');
var gulp = gulpfile.gulp;

var tasks = process.argv.slice(2);
if (tasks.length == 0) {
	throw "no tasks specified"; 
}

glog(gulp);
gulp.start(tasks);


