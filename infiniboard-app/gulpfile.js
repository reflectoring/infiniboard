var gulp = require('gulp'),
  rename = require('gulp-rename'),
  traceur = require('gulp-traceur'),
  webserver = require('gulp-webserver'),
  browserSync = require('browser-sync').create(),
  del = require('del'),
  usemin = require('gulp-usemin'),
  uglify = require('gulp-uglify'),
  minifyCss = require('gulp-minify-css'),
  replace = require('gulp-replace'),
  gulpTypings = require("gulp-typings"),
  tslint = require('gulp-tslint');


// run init tasks
gulp.task('default', ['dependencies', 'bower', 'ts', 'components_html', 'components_css', 'app_html']);

// run development task
gulp.task('dev', ['default', 'watch', 'serve']);

// ---------------------------------------------------------------------------------------------------------------------

// cleans the build dir
gulp.task('clean', function () {
  del([
    'build'
  ])
});

// ---------------------------------------------------------------------------------------------------------------------

// bower install
// copy bower dependencies into build dir
gulp.task('bower', function () {
  return gulp.src('bower_components/**')
    .pipe(gulp.dest('build/lib'))
});

// copy dependencies into build dir
gulp.task('dependencies', ['bower'], function () {
  return gulp.src([
    'node_modules/es6-shim/es6-shim.min.js',
    'node_modules/systemjs/dist/system-polyfills.js',
    'node_modules/angular2/bundles/angular2-polyfills.js',
    'node_modules/systemjs/dist/system.src.js',
    'node_modules/rxjs/bundles/Rx.js',
    'node_modules/angular2/bundles/angular2.dev.js'
  ]).pipe(gulp.dest('build/lib'));
});

// install typings
gulp.task("install_typings", ['dependencies'], function () {
    return gulp.src("./typings.json")
      .pipe(gulpTypings());
  }
)
;

// ---------------------------------------------------------------------------------------------------------------------

gulp.task('serve', ['bower', 'dependencies'], function () {
    browserSync.init({
      server: {
        baseDir: "build"
      }
    });
  }
)
;

// watch for changes and run the relevant task
gulp.task('watch', ['default'], function () {
  gulp.watch('index.html', ['app_html']).on('change', browserSync.reload);
  gulp.watch('app/**/*.ts', ['ts']).on('change', browserSync.reload);
  gulp.watch('app/**/*.html', ['components_html']).on('change', browserSync.reload);
  gulp.watch('app/**/*.css', ['components_css']).on('change', browserSync.reload);
});

gulp.task('app_html', function () {
  return gulp.src('index.html')
    .pipe(usemin({
      assetsDir: '',
      css: [minifyCss()
        .pipe(replace('fonts', 'lib/font-awesome/fonts')), 'concat'],
      js: [uglify(), 'concat']
    }))
    .pipe(replace(/(node_modules[^"]*)\//g, 'lib/'))
    .pipe(replace('bower_components', 'lib'))
    .pipe(gulp.dest('build'));
});

// transpile & move js
gulp.task('ts', ['tslint'], function () {
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

gulp.task('tslint', function () {
  return gulp.src('app/**/*.ts')
    .pipe(tslint())
    .pipe(tslint.report('verbose'));
});

// move component html
gulp.task('components_html', function () {
  return gulp.src('app/**/*.html')
    .pipe(gulp.dest('build/app'));
});

// move component css
gulp.task('components_css', function () {
  return gulp.src('app/**/*.css')
    .pipe(gulp.dest('build/app'));
});
