var gulp = require('gulp');
var usemin = require('gulp-usemin');
var uglify = require('gulp-uglify');
var minifyCss = require('gulp-minify-css');

// run init tasks
gulp.task('default', ['replace_js_css']);


gulp.task('replace_js_css', function () {
  gulp.src('index.html')
    .pipe(usemin({
      assetsDir: '',
      css: [minifyCss(), 'concat'],
      js: [uglify(), 'concat']
    }))
    .pipe(gulp.dest('build'));
});
