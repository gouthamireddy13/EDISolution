// Karma configuration
// Generated on Tue Aug 29 2017 21:57:01 GMT-0400 (Eastern Daylight Time)

module.exports = function(config) {
  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: '',


    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine'],


    // list of files / patterns to load in the browser
    files: [
        'node_modules/angular/angular.js',
        'node_modules/angular-mocks/angular-mocks.js',

        'ui/Plugins/angular-strap.js',
        'ui/Plugins/angular-strap.tpl.js',

        'ui/Plugins/angular-route.js',
        'ui/Plugins/angular-ui.min.js',

        'ui/Plugins/jquery.min.js',
        'ui/Plugins/jquery-ui.js',
        'ui/Plugins/bootstrap.min.js',
        'ui/Plugins/bootstrap.min.css',
        'ui/Plugins/font-awesome.js',

        'ui/Plugins/mask.min.js',
        'ui/Plugins/scriptang.js',
        'ui/Plugins/ui-bootstrap-tpls-0.12.1.js',
        'ui/script.js',




        'app/**.js',
      'ui/**.js',
      'tests/**.js',
      'test/**.js',
      'ls',
      'uitest/**.js'
    ],


    // list of files to exclude
    exclude: [
    ],


    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
    },


    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['progress'],


    // web server port
    port: 9876,


    // enable / disable colors in the output (reporters and logs)
    colors: true,


    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,


    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,


    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    browsers: ['Chrome'],


    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false,

    // Concurrency level
    // how many browser should be started simultaneous
    concurrency: Infinity
  })
}
