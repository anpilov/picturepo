var gulp = require('gulp');
var concat = require('gulp-concat');
var clean = require('gulp-clean');
var uglify = require('gulp-uglify');
var ngTemplateCache = require('gulp-angular-templatecache');
var ngAnnotate = require('gulp-ng-annotate');

exports.gulp = gulp;

var moduleName = 'picturepoClient';

var targetDir = 'target/compile';

var jsSources = ['src/module.js', 'src/**/*.js'];
var cssSources = ['src/**/*.css'];
var templateSources = ['src/**/*.html'];

gulp.task('clean', function() {
    return gulp.src(targetDir).pipe(clean());
});

gulp.task('build-prod', ['clean'], function() {
    gulp.src(cssSources)
        .pipe(concat('main.css'))
        .pipe(gulp.dest(targetDir));

    gulp.src(jsSources)
        .pipe(concat('app.js'))
        .pipe(ngAnnotate())
        .pipe(uglify())
        .pipe(gulp.dest(targetDir));

    gulp.src(templateSources)
        .pipe(ngTemplateCache({ module: moduleName }))
        .pipe(ngAnnotate())
        .pipe(uglify())
        .pipe(gulp.dest(targetDir));
});

gulp.task('build-dev', ['clean', 'compile-css', 'compile-js', 'compile-templates']);

gulp.task('compile-css', function () {
    return gulp.src(cssSources).pipe(concat('main.css')).pipe(gulp.dest(targetDir));
});

gulp.task('compile-js', function () {
    return gulp.src(jsSources).pipe(concat('app.js')).pipe(gulp.dest(targetDir))
});

gulp.task('compile-templates', function () {
    return gulp.src(templateSources).pipe(ngTemplateCache({ module: moduleName })).pipe(gulp.dest(targetDir));
});

gulp.task('watch', function () {
    gulp.watch(jsSources, ['compile-js']);
    gulp.watch(cssSources, ['compile-css']);
    gulp.watch(templateSources, ['compile-templates']);
});

