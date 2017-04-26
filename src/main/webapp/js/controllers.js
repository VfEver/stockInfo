/**
 * gupiao controller module
 */
angular.module('controllers', ['ngCookies'])
.config(function ($httpProvider) {
	$httpProvider.defaults.transformRequest = function(data){
        if (data === undefined) {
            return data;
        }
        return $.param(data);
    }
    $httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
    $httpProvider.defaults.headers.post['Content-Type'] =  'application/x-www-form-urlencoded';
})
.controller('IndexController', ['$scope', '$location', '$cookies', '$cookieStore', '$http', '$window', function($scope, $location, $cookies, $cookieStore, $http, $window) {
	
	//点击股票列表按钮进入股票列表界面
	$scope.gotoStockList = function () {
		$location.path("stock");
	}
	
	//刷新页面
	$scope.reloadRoute = function () {
	    $window.location.reload();
	};
	
	//获得session stock的具体信息
	function getStockInfo () {
		$http({
		    method:'post', 
		    url:'http://localhost:8080/gupiao/stockInfoServlet',
		    data:{
	    	}
		}).success(function(data){
			
			$scope.stock = data.showapi_res_body.stockMarket;
			$scope.stock.nowPrice = Number($scope.stock.nowPrice).toFixed(2);
			$scope.diff = ($scope.stock.nowPrice-$scope.stock.closePrice).toFixed(2);
			
			if ($scope.diff < 0) {
				$("#stockPriceID").toggleClass("low-text");
				$("#stockPriceID1").toggleClass("low-text");
				$("#stockPriceID2").toggleClass("low-text");
				$("#stockIconID").toggleClass("down-icon");
			}
			
			$scope.diffPercent = ($scope.diff/$scope.stock.closePrice*100).toFixed(1);
			$scope.tradeAmount = ($scope.stock.tradeAmount/100000000).toFixed(4);
		})
	}
	
	getStockInfo();

	$scope.isSelfStock = false;
	
	//点击加入自选股
	$scope.addToSelfStock = function () {
		
		if($cookies.get("userID") == undefined || $cookies.get("userID") == null) {
			alert("您还未登录，请先登录");
			return ;
		}
		
		if ($scope.isSelfStock == true) {
			alert("已经是自选股，请勿重复添加");
			return ;
		}
		
		$("#selfStockID").text("已是自选股");
		var stockCode = $cookies.get("market") + $cookies.get("stockCode");
		$http({
		    method:'post', 
		    url:'http://localhost:8080/gupiao/addSelfStockServlet',
		    data:{
		    	userID: $cookies.get("userID"),
		    	stockCode: stockCode
		    }
		}).success(function(data){
			alert("添加自选股成功");
			$scope.isSelfStock = true;
		})
	}
	
	//判断表头的股票是否已经是自选股
	function isSelfStock() {
		
		if ($cookies.get("userID") == undefined || $cookies.get("userID") == null) {
			return ;
		}
		
		$http({
		    method:'post', 
		    url:'http://localhost:8080/gupiao/isSelfStockServlet',
		    data:{
		    	userID: $cookies.get("userID"),
		    	stockCode: $cookies.get("stockCode")
		    }
		}).success(function(data){

			if (data.status === 200) {
				if (data.isSelfStock) {
					$("#selfStockID").text("已是自选股");
					$scope.isSelfStock = true;
				}
			}
		})
	}
	
	isSelfStock();
	
//	//代码搜索
//	$scope.navSearch = function() {
//		
//		$cookies.put("stockCode", $scope.inputStockCode);
//		//点击之后先将股票代码加入session方便后台存取
//		$http({
//		    method:'post',  
//		    url:'http://localhost:8080/gupiao/stockSessiontServlet',
//		    data:{
//		    	stockCode: $scope.inputStockCode
//	    	}
//		}).success(function(data){  
//			$location.path("index");
//		})
//		
//		$http({
//		    method:'post', 
//		    url:'http://localhost:8080/gupiao/stockSearchServlet',
//		    data:{
//		    	stockCode: $scope.inputStockCode
//	    	}
//		}).success(function(data){
//			
//			$scope.stock = data.showapi_res_body.stockMarket;
//			$scope.stock.nowPrice = Number($scope.stock.nowPrice).toFixed(2);
//			$scope.diff = ($scope.stock.nowPrice-$scope.stock.closePrice).toFixed(2);
//			
//			if ($scope.diff < 0) {
//				$("#stockPriceID").toggleClass("low-text");
//				$("#stockPriceID1").toggleClass("low-text");
//				$("#stockPriceID2").toggleClass("low-text");
//				$("#stockIconID").toggleClass("down-icon");
//			}
//			
//			$scope.diffPercent = ($scope.diff/$scope.stock.closePrice*100).toFixed(1);
//			$scope.tradeAmount = ($scope.stock.tradeAmount/100000000).toFixed(4);
//		})
//		$scope.reloadRoute();
//	}
	
}])
.controller('UserOpeController', ['$scope', '$location', '$cookies', '$cookieStore', '$http', '$window', function($scope, $location, $cookies, $cookieStore, $http, $window) {
	
	//刷新页面
	$scope.reloadRoute = function () {
	    $window.location.reload();
	};
	
	//点击登录弹出登录模态框
	$scope.clickToLogin = function () {
		$("#loginModal").modal("toggle");
	}
	
	//点击注册弹出注册模态框
	$scope.clickToRegister = function () {
		$("#registerModal").modal("toggle");
	}
	
	//两次密码是否一致
	$scope.comfirmPasswordInvalidate = false;
	
	//密码和确认密码是否一致
	$('.confirmPasswordInput').on('change', function () {
		
		if ($scope.comfirmPassword == undefined) {
			$scope.$apply(function () {
				$scope.comfirmPasswordInvalidate = true;
			});
		}
		
		if ($scope.password === $scope.comfirmPassword) {
			$scope.$apply(function () {
				$scope.comfirmPasswordInvalidate = false;
			});
		} else {
			$scope.$apply(function () {
				$scope.comfirmPasswordInvalidate = true;
			});
		}
	});
	
	//登录状态
	$scope.username = $cookies.get("username");
	$scope.online = $cookies.get("online");
	
	//登录
	$scope.login = function () {
		$http({  
		    method:'post',  
		    url:'http://localhost:8080/gupiao/loginServlet',
		    data:{
		    	username: $scope.username,
		    	password: $scope.password
	    	}
		}).success(function(data){
			if (data.status === 200) {
				$cookies.put("username", data.username);
				$cookies.put("userID", data.userID);
				$cookies.put("online", true);
				$scope.online = true;
				
			} else {
				alert(data.reason);
				$("#loginModal").modal("toggle");
			}
		})
	}
	
	$scope.usernameInvalidate = false;
	$scope.usernameUsed = false;
	//验证账号是否合法
	$('.accountInput').on('blur', function() {
		if ($scope.username === undefined || $scope.username.trim() === '') {
			return ;
		}
		
		if ($scope.username.trim().length < 6 || $scope.username.trim().length > 20) {
			return ;
		} else {
			$scope.$apply(function () {
				$scope.usernameInvalidate  = false;
			});
		}
		
		$http({
			method:'post',  
			url:'http://localhost:8080/gupiao/usernameServlet',
			data:{
				username: $scope.username
			}
		}).success(function(data){  
			if (data.used) {
				$scope.usernameUsed = true;
				$scope.usernameInvalidate = true;
			}
		})
		
	});

	
	//注册
	$scope.register = function () {
		
		if ($scope.usernameInvalidate || $scope.usernameUsed) {
			return ;
		}
		$http({  
		    method:'post',  
		    url:'http://localhost:8080/gupiao/registerServlet',
		    data:{
		    	username: $scope.username,
		    	password: $scope.password
	    	}
		}).success(function(data){  
			if (data.status === 200) {
				$cookies.put("username", data.username);
				$cookies.put("userID", data.userID);
				$cookies.put("online", true);
				$scope.online = true;
			} else {
				alert(data.reason);
				$("#registerModal").modal("toggle");
			}
		})
	}
	
	//退出
	$scope.logout = function () {
		$cookies.remove("userID");
		$cookies.remove("username");
		$cookies.remove("online");
		$cookies.remove("stockCode");
		$cookies.remove("market");
		
		if ($location.path().indexOf("stock") >= 0) {
			
			$scope.reloadRoute();
		} else {
			
			$location.path("stock");
		}
	}
	
	//代码搜索
	$scope.navSearch = function() {
		
		$cookies.put("stockCode", $scope.inputStockCode);
		//点击之后先将股票代码加入session方便后台存取
		$http({
		    method:'post',  
		    url:'http://localhost:8080/gupiao/stockSessiontServlet',
		    data:{
		    	stockCode: $scope.inputStockCode
	    	}
		}).success(function(data){  
			if ($location.path().indexOf("index") >= 0) {
				$scope.reloadRoute();
			} else {
				$location.path("index");
			}
		})
	}
	
}])
.controller('StockController', ['$scope', '$location', '$cookies', '$cookieStore', '$http', '$window', function($scope, $location, $cookies, $cookieStore, $http, $window) {
	
	//如果用户登录，则返回他的所有自选股
	if ($cookies.get("userID") != undefined && $cookies.get("userID") != null) {
		
		getUserSelfStock();
		
	} else {	//否则则返回股票列表
		
		getStockList(1);
	}
	
	//获得股票列表
	function getStockList(pageNum) {
		$http({  
		    method:'post',  
		    url:'http://localhost:8080/gupiao/stockListServlet',
		    data:{
		    	page: pageNum
	    	}
		}).success(function(data){  
			var res = data;
			$scope.stockList = res.showapi_res_body.contentlist;
			
			$cookies.put("stockCode", $scope.stockList[0].code);
			
			$http({
			    method:'post',  
			    url:'http://localhost:8080/gupiao/stockSessiontServlet',
			    data:{
			    	stockCode: $scope.stockList[0].code
		    	}
			}).success(function(data){
				getStockInfo();
			})
			
		})
	}
	
	//根据股票代码查询股票详细信息
	function getStockInfo () {
		$http({
		    method:'post', 
		    url:'http://localhost:8080/gupiao/stockInfoServlet',
		    data:{
	    	}
		}).success(function(data){  
			
			$scope.stock = data.showapi_res_body.stockMarket;
			$scope.stock.nowPrice = Number($scope.stock.nowPrice).toFixed(2);
			$scope.diff = ($scope.stock.nowPrice-$scope.stock.closePrice).toFixed(2);
			
			if ($scope.diff < 0) {
				$("#stockPriceID").toggleClass("low-text");
				$("#stockPriceID1").toggleClass("low-text");
				$("#stockPriceID2").toggleClass("low-text");
				$("#stockIconID").toggleClass("down-icon");
			}
			
			$scope.diffPercent = ($scope.diff/$scope.stock.closePrice*100).toFixed(1);
			$scope.tradeAmount = ($scope.stock.tradeAmount/100000000).toFixed(4);
		})
	}
	
	//查询用户的所有自选股
	function getUserSelfStock() {
		
		$http({
			method:'post', 
			url:'http://localhost:8080/gupiao/userSelftStockServlet',
			data:{
				userID: $cookies.get("userID")
			}
		}).success(function(data){  
			
			$scope.stockList = data.showapi_res_body.list;
			if ($scope.stockList == undefined || $scope.stockList == null) {
				alert("您还没有自选股，请搜索或者从股票列表查看添加自选股");
				getStockList(1);
			} else {
				getStockInfo();
			}
		})
	}
	
	
	//点击查看股票具体信息
	$scope.checkStockInfo = function (code, market) {
		$cookies.put("market", market);
		$cookies.put("stockCode", code);
		//点击之后先将股票代码加入session方便后台存取
		$http({
		    method:'post',  
		    url:'http://localhost:8080/gupiao/stockSessiontServlet',
		    data:{
		    	stockCode: code,
	    	}
		}).success(function(data){  
			$location.path("index");
		})
	}
	
	//点击加入自选股
	$scope.addToSelfStock = function () {
		
		if($cookies.get("userID") == undefined || $cookies.get("userID") == null) {
			alert("您还未登录，请先登录");
			return ;
		}
		
		if ($scope.isSelfStock) {
			alert("已经是自选股，请勿重复添加");
		}
		
		$("#selfStockID").text("已是自选股");
		var stockCode = $cookies.get("market") + $cookies.get("stockCode");
		$http({
		    method:'post', 
		    url:'http://localhost:8080/gupiao/addSelfStockServlet',
		    data:{
		    	userID: $cookies.get("userID"),
		    	stockCode: stockCode
		    }
		}).success(function(data){
			if (data.status === 200) {
				alert("添加自选股成功");
				$scope.isSelfStock = true;
			}
		})
	}
	
	$scope.isSelfStock = false;
	
	//判断表头的股票是否已经是自选股
	function isSelfStock() {
		
		if ($cookies.get("userID") == undefined || $cookies.get("userID") == null) {
			return ;
		}
		
		$http({
		    method:'post', 
		    url:'http://localhost:8080/gupiao/isSelfStockServlet',
		    data:{
		    	userID: $cookies.get("userID"),
		    	stockCode: $cookies.get("stockCode")
		    }
		}).success(function(data){

			if (data.status === 200) {
				if (data.isSelfStock) {
					$("#selfStockID").text("已是自选股");
					$scope.isSelfStock = true;
				}
			}
		})
	}
	
	isSelfStock();
	
}]);