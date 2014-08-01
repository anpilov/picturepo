var gulpfile = require("./gulpfile.js");
var gulp = gulpfile.gulp;
var gutil = require('gulp-util');

var tasks = process.argv.slice(2);
if (tasks.length == 0) {
	throw "no tasks specified"; 
}

initLog(gulp);
gulp.start(tasks);

function initLog(gulp) {
    gulp.on('err', function() {});

    gulp.on('task_start', function(e) {
        gutil.log('Starting', "'" + e.task + "'...");
    });

    gulp.on('task_stop', function(e) {
        var time = e.hrDuration;
        gutil.log('Finished', "'" + e.task + "'", 'after', time);
    });

    gulp.on('task_err', function(e) {
        var time = e.hrDuration;
        var error = e.err;
        gutil.log("'" + e.task + "'", 'errored after', time, error);
    });

    gulp.on('task_not_found', function(err) {
        gutil.log("Task '" + err.task + "' was not defined in your gulpfile but you tried to run it.");
        gutil.log('Please check the documentation for proper gulpfile formatting.');
        process.exit(1);
    });
}


