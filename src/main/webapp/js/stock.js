var stock = (function () {
	//根url
	var baseUrl = 'http://localhost:8080/gupiao/';
    // 获取数据并渲染界面
	function render() {
		var kUrl = baseUrl + 'stockDetailServlet';
		var handicapUrl = baseUrl + 'stockHandicapServlet';
		_ajax(kUrl, function(data) {
            _renderK(data)
        })
        _ajax(handicapUrl, function(data) {
            _renderHandicap(data)
        })
	}
    // 渲染k线图
	function _renderK(data) {
		var kChart = echarts.init(document.getElementById('data-k'));
		var xData = [],
			kData = [],
			volumeData = [];
		data.forEach(function (value) {
			xData.push(value.date)
			kData.push(value.value)
			volumeData.push(value.volume)
		})
		function calculateMA(dayCount) {
	        var result = [];
	        for (var i = 0, len = kData.length; i < len; i++) {
	            if (i < dayCount) {
	                result.push('-');
	                continue;
	            }
	            var sum = 0;
	            for (var j = 0; j < dayCount; j++) {
	                sum += kData[i - j][1];
	            }
	            result.push(sum / dayCount);
	        }
	        return result;
	    }
		var option = {
		    title: {
		        text: 'K线图',
		        left: 0
		    },
		    toolbox: {
		        show: true,
		        right: "10%",
		        feature: {
		            dataZoom: {
		                yAxisIndex: 'none'
		            }
		        }
		    },
		    tooltip: {
		        trigger: 'axis'
		    },
		    legend: {
		        data: ['日K', 'MA5', 'MA10', 'MA20', 'MA30'],
		        bottom: 'bottom',
		        left: 'center'
		    },
		    grid: [{
		        x: '10%',
		        y: '10%',
		        width: '80%',
		        height: '50%'
		    }, {
		        x2: '10%',
		        y: '65%',
		        width: '80%',
		        height: '20%'
		    }],
		    xAxis: [{
		        type: 'category',
		        data: xData,
		        gridIndex: 0,
		        scale: true,
		        boundaryGap: false,
		        axisLine: {
		            onZero: false
		        },
		        splitLine: {
		            show: false
		        },
		        splitNumber: 20,
		        min: 'dataMin',
		        max: 'dataMax'
		    }, {
		        data: xData,
		        gridIndex: 1,
		        axisLine: {
		            lineStyle: {
		                color: 'rgba(0,0,0,1)'
		            }
		        },
		        axisTick: {
		            lineStyle: {
		                color: 'rgba(255,255,255,1)'
		            }
		        },
		        axisLabel: {
		            textStyle: {
		                color: 'rgba(255,255,255,0)'
		            }
		        }
		    }],
		    yAxis: [{
		        scale: true,
		        gridIndex: 0,
		        splitArea: {
		            show: true
		        }
		    }, {
		        gridIndex: 1,
		        splitLine: false,
		        axisLine: {
		            lineStyle: {
		                color: 'rgba(255,255,255,0)'
		            }
		        },
		        axisLabel: {
		            textStyle: {
		                color: 'rgba(255,255,255,0)'
		            }
		        }
		    }],
		    dataZoom: [{
		        type: 'inside',
		        start: 50,
		        xAxisIndex: [0, 1],
		        end: 100
		    }, {
		        xAxisIndex: [0, 1],
		        show: true,
		        type: 'slider',
		        y: '90%',
		        start: 50,
		        end: 100
		    }],
		    series: [{
		        name: '日K',
		        type: 'candlestick',
		        xAxisIndex: 0,
		        yAxisIndex: 0,
		        data: kData
		    }, {
		        name: 'MA5',
		        type: 'line',
		        data: calculateMA(5),
		        smooth: true,
		        xAxisIndex: 0,
		        yAxisIndex: 0,
		        lineStyle: {
		            normal: {
		                opacity: 0.5
		            }
		        }
		    }, {
		        name: 'MA10',
		        type: 'line',
		        data: calculateMA(10),
		        smooth: true,
		        xAxisIndex: 0,
		        yAxisIndex: 0,
		        lineStyle: {
		            normal: {
		                opacity: 0.5
		            }
		        }
		    }, {
		        name: 'MA20',
		        type: 'line',
		        xAxisIndex: 0,
		        yAxisIndex: 0,
		        data: calculateMA(20),
		        smooth: true,
		        lineStyle: {
		            normal: {
		                opacity: 0.5
		            }
		        }
		    }, {
		        name: 'MA30',
		        xAxisIndex: 0,
		        yAxisIndex: 0,
		        type: 'line',
		        data: calculateMA(30),
		        smooth: true,
		        lineStyle: {
		            normal: {
		                opacity: 0.5
		            }
		        }
		    }, {
		    	name : '成交量',
		        type: 'bar',
		        xAxisIndex: 1,
		        yAxisIndex: 1,
		        data: volumeData
		    }]
		};


		kChart.setOption(option);
	}
	
    // 渲染盘口
	function _renderHandicap(data) {
		var $sell = $(".js-data-handicap-sell");
		var $buy = $(".js-data-handicap-buy");
		var sellHtml = '';
		var buyHtml = '';
		sellHtml += '<table>'
		data.sell.forEach(function (sell) {
			 sellHtml += '<tr>'
			 sellHtml += '<td>' + sell.name + '</td>'
			 sellHtml += '<td class="up-text">' + sell.price + '</td>'
			 sellHtml += '<td>' + sell.count + '</td>'
			 sellHtml += '</tr>'
		})
		sellHtml += '</table>'

		buyHtml += '<table>'
		data.buy.forEach(function (buy) {
			 buyHtml += '<tr>'
			 buyHtml += '<td>' + buy.name + '</td>'
			 buyHtml += '<td class="up-text">' + buy.price + '</td>'
			 buyHtml += '<td>' + buy.count + '</td>'
			 buyHtml += '</tr>'
		})
		buyHtml += '</table>'
		$sell.empty()
		.append(sellHtml)
		$buy.empty()
		.append(buyHtml)
	}
	
	// 封装ajax方法
	function _ajax(url, cb) {
	    $.getJSON(url)
        .done(function(d) {
            cb && cb(d)
        })
        .fail(function(e) {
        	console.log(e);
            alert(url + '：获取不到数据');
        })
	}
    // 暴露render方法
	return {
		render : render
	}
})()
