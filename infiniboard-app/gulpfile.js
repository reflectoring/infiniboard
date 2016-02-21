var gulp = require('gulp'),
  rename = require('gulp-rename'),
  traceur = require('gulp-traceur'),
  webserver = require('gulp-webserver'),
  del = require('del'),
  bower = require('gulp-bower'),
  usemin = require('gulp-usemin'),
  uglify = require('gulp-uglify'),
  minifyCss = require('gulp-minify-css'),
  replace = require('gulp-replace');

gulp.task('bower', function () {
  return bower()
    .pipe(gulp.dest('build/lib/'))
});

// run init tasks
gulp.task('default', ['bower', 'dependencies', 'js', 'components_html', 'css', 'app_html']);

// run development task
gulp.task('dev', ['default', 'watch', 'serve']);

// cleans the output folder
gulp.task('clean', function () {
  del([
    'build'
  ])
});

// serve the build dir
gulp.task('serve', function () {
  gulp.src('build')
    .pipe(webserver({
      open: true
    }));
});

// watch for changes and run the relevant task
gulp.task('watch', function () {
  gulp.watch('app/**/*.ts', ['js']);
  gulp.watch('app/**/*.html', ['components_html']);
  gulp.watch('index.html', ['app_html']);
  gulp.watch('app/**/*.css', ['css']);
});

// move dependencies into build dir
gulp.task('dependencies', function () {
  return gulp.src([
      'node_modules/es6-shim/es6-shim.min.js',
      'node_modules/systemjs/dist/system-polyfills.js',
      'node_modules/angular2/bundles/angular2-polyfills.js',
      'node_modules/systemjs/dist/system.src.js',
      'node_modules/rxjs/bundles/Rx.js',
      'node_modules/angular2/bundles/angular2.dev.js'
    ])
    .pipe(gulp.dest('build/lib'));
});

gulp.task('app_html', function () {
  gulp.src('index.html')
    .pipe(usemin({
      assetsDir: '',
      css: [minifyCss(), 'concat'],
      js: [uglify(), 'concat']
    }))
    .pipe(replace(/(node_modules[^"]*)\//g, 'lib/'))
    .pipe(gulp.dest('build'));
});


// transpile & move js
gulp.task('js', function () {
  return gulp.src('app/**/*.ts')
    .pipe(rename({
      extname: ''
    }))
    .pipe(traceur({
      modules: 'instantiate',
      moduleName: true,
      annotations: true,
      types: true,
      memberVariables: true
    }))
    .pipe(rename({
      extname: '.js'
    }))
    .pipe(gulp.dest('build/app'));
});

gulp.task('replace_js_css', function () {
  gulp.src('index.html')
    .pipe(usemin({
      assetsDir: '',
      css: [minifyCss(), 'concat'],
      js: [uglify(), 'concat']
    }))
    .pipe(gulp.dest('build'));
});

// move component html
gulp.task('components_html', function () {
  return gulp.src('app/**/*.html')
    .pipe(gulp.dest('build/app'))
});

// move component css
gulp.task('css', function () {
  return gulp.src('app/**/*.css')
    .pipe(gulp.dest('build/app'))
});
