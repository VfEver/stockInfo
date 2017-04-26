/**
 * gupiao ui-router
 */
angular.module("gupiao", ['ui.router', 'controllers'])
.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise('/index');
	$stateProvider
	.state('index', {
		url: '/index',
		views: {
			'navinfo': {
				templateUrl: 'tpls/index-nav-tpl.html'
			},
			'main': {
				templateUrl: 'tpls/index-main-tpl.html'
			},
			'footer': {
				templateUrl: ''
			}
		}
	})
	.state('stock', {
		url: '/stock',
		views: {
			'navinfo': {
				templateUrl: 'tpls/index-nav-tpl.html'
			},
			'main': {
				templateUrl: 'tpls/stock-main-tpl.html'
			},
			'footer': {
				templateUrl: ''
			}
		}
	});
}]);