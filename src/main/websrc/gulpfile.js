var gulp = require('gulp');
var concat = require('gulp-concat');
var clean = require('gulp-clean');
var less = require('gulp-less');
var minifyCss = require('gulp-minify-css')
var watch = require('gulp-watch');
var uglify = require('gulp-uglify');
var gutil = require('gulp-util');
var ngTemplateCache = require('gulp-angular-templatecache');
var ngAnnotate = require('gulp-ng-annotate');

exports.gulp = gulp;

var moduleName = 'picturepoClient';

var targetDir = '../webapp/app';

var jsSources = ['app/module.js', 'app/**/*.js'];
var templateSources = ['app/**/*.html'];
var lessSources = ['app/**/*.less'];

var productionBuild = true;

gulp.task('clean', function () {
    return gulp.src(targetDir).pipe(clean({ force: true }));
});

gulp.task('build', ['clean', 'compile-less', 'compile-js', 'compile-templates']);

gulp.task('watch', ['clean'], function () {
    productionBuild = false;
    watch({glob: lessSources}, function () {
        gulp.start('compile-less');
    });
    watch({glob: jsSources}, function () {
        gulp.start('compile-js');
    });
    watch({glob: templateSources}, function () {
        gulp.start('compile-templates');
    });
});

gulp.task('compile-less', function () {
    return gulp.src(lessSources)
        .pipe(less())
        .pipe(concat('main.css'))
        .pipe(production(minifyCss()))
        .pipe(gulp.dest(targetDir));
});

gulp.task('compile-js', function () {
    return gulp.src(jsSources)
        .pipe(concat('app.js'))
        .pipe(production(ngAnnotate()))
        .pipe(production(uglify()))
        .pipe(gulp.dest(targetDir));
});

gulp.task('compile-templates', function () {
    return gulp.src(templateSources)
        .pipe(ngTemplateCache({ module: moduleName }))
        .pipe(production(ngAnnotate()))
        .pipe(production(uglify()))
        .pipe(gulp.dest(targetDir));
});

function production(pipe) {
    return productionBuild ? pipe : gutil.noop();
}

