//近一个星期告警排行榜
var alarmCharts = echarts.init(document.getElementById('alarmCharts'));

option = {
    color: ['#3398DB'],
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type : 'shadow',
            animation: false
        }
    },
    toolbox: {
        show: true,
        feature: {
            dataView: {readOnly: false},
            magicType: {type: ['line', 'bar']}
        }
    },
    grid: {
        top:45
    },
    legend: {
        data:[{
            name:"告警次数"
        }]
    },
    xAxis: {
        type:'category',
        name:'项目名称',
        data: [],
        axisTick: {
            alignWithLabel: true
        }
    },
    yAxis: {
        type: 'value',
        name:'告警次数'
    },
    series: [{
        name: '告警次数',
        type: 'bar',
        showSymbol: false,
        hoverAnimation: false,
        data: []
    }]
};

alarmCharts.setOption(option);

alarmCountInit();
$("#alarmSearch").click(function () {
    alarmCountInit();
});

function alarmCountInit(rank,day) {
    var rank = $("#alarmRank").val();
    var day = $("#alarmTime").val();
    $.get("/charts/alarmCount?alarmRank="+rank+"&alarmTime="+day).done(function (data) {
        alarmCharts.setOption({
            xAxis: {
                type:'category',
                data: data.key,
                splitLine: {
                    show: false
                }
            },
            series : [
                {
                    name: '告警次数',
                    type: 'bar',
                    data:data.data
                }]
        })
    });
}

//告警规则对比图
var ruleTypeCharts = echarts.init(document.getElementById('ruleTypeCharts'));

ruleTypeCharts.setOption({
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c}个 (比例：{d}%)"
    },
    series : [
        {
            name: '规则数量',
            type: 'pie',
            radius: '55%',
            center: ['50%', '50%'],
            data:[],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
})

$.get('/charts/ruleType').done(function (data) {
    ruleTypeCharts.setOption({
        legend: {
            data: data.key
        },
        series : [
            {
                name: '规则数量',
                type: 'pie',
                radius: '55%',
                center: ['50%', '50%'],
                data:data.data,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }]

    })
});

//单个项目APM告警折线图
var singletonApmCharts = echarts.init(document.getElementById('singletonApmCharts'));

option1 = {
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            animation: false
        }
    },
    toolbox: {
        show: true,
        feature: {
            dataView: {readOnly: false},
            magicType: {type: ['line', 'bar']}
        }
    },
    grid: {
        //left:'auto',
        //right:'60',
        top:45
        //bottom:20,
        //containLabel: true
    },
    legend: {
        data:[{
            name:"告警次数"
        }]
    },
    xAxis: {
        type:'category',
        boundaryGap: false,
        name:'日期',
        data: [],
        splitLine: {
            show: false
        }
    },
    yAxis: {
        type: 'value',
        boundaryGap: [0, '50%'],
        name:'数量(个)',
        splitLine: {
            show: true
        }
    },
    series: [{
        name: '告警次数',
        type: 'line',
        showSymbol: false,
        hoverAnimation: false,
        data: []
    }],
    dataZoom: [
        {   // 这个dataZoom组件，默认控制x轴。
            type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
            start: 50,      // 左边在 10% 的位置。
            end: 100         // 右边在 60% 的位置。
        }
    ]
};

singletonApmCharts.setOption(option1);

alarmApmSearch();
$("#alarmApmSearch").click(function () {
    alarmApmSearch();
});

function alarmApmSearch() {
    var appName = $("#appNameApm").val();
    var appTime = $("#appTimeApm").val();
    $.get('/charts/singletonApm?appName='+appName+"&appTime="+appTime).done(function (data) {
        singletonApmCharts.setOption({
            xAxis: {
                type:'category',
                data: data.key,
                splitLine: {
                    show: false
                }
            },
            series : [
                {
                    name: '告警次数',
                    type: 'line',
                    data:data.data
                }]
        })
    });
}

//单个项目ELK告警折线图
var singletonElkCharts = echarts.init(document.getElementById('singletonElkCharts'));

option2 = {
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            animation: false
        }
    },
    toolbox: {
        show: true,
        feature: {
            dataView: {readOnly: false},
            magicType: {type: ['line', 'bar']}
        }
    },
    grid: {
        //left:'auto',
        //right:'60',
        top:45
        //bottom:20,
        //containLabel: true
    },
    legend: {
        data:[{
            name:"告警次数"
        }]
    },
    xAxis: {
        type:'category',
        boundaryGap: false,
        name:'日期',
        data: [],
        splitLine: {
            show: false
        }
    },
    yAxis: {
        type: 'value',
        boundaryGap: [0, '50%'],
        name:'数量(个)',
        splitLine: {
            show: true
        }
    },
    series: [{
        name: '告警次数',
        type: 'line',
        showSymbol: false,
        hoverAnimation: false,
        data: []
    }],
    dataZoom: [
        {   // 这个dataZoom组件，默认控制x轴。
            type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
            start: 50,      // 左边在 10% 的位置。
            end: 100         // 右边在 60% 的位置。
        }
    ]
};

singletonElkCharts.setOption(option2);

alarmElkSearch();
$("#alarmElkSearch").click(function () {
    alarmApmSearch();
});

function alarmElkSearch() {
    var appName = $("#appNameElk").val();
    var appTime = $("#appTimeElk").val();
    $.get('/charts/singletonElk?appName='+appName+"&appTime="+appTime).done(function (data) {
        singletonElkCharts.setOption({
            xAxis: {
                type:'category',
                data: data.key,
                splitLine: {
                    show: false
                }
            },
            series : [
                {
                    name: '告警次数',
                    type: 'line',
                    data:data.data
                }]
        })
    });
}



//告警规则对比图
var sourceTypeCharts = echarts.init(document.getElementById('sourceTypeCharts'));

sourceTypeCharts.setOption({
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c}次 (比例：{d}%)"
    },
    series : [
        {
            name: '告警总数',
            type: 'pie',
            radius: '55%',
            center: ['50%', '50%'],
            data:[],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
})

alarmSourceSearch();

$("#alarmSourceSearch").click(function () {
    alarmSourceSearch();
});

function alarmSourceSearch() {
    var appTimeSource = $("#appTimeSource").val();
    $.get('/charts/sourceType?appTime='+appTimeSource).done(function (data) {
        sourceTypeCharts.setOption({
            legend: {
                data: data.key
            },
            series : [
                {
                    name: '告警总数',
                    type: 'pie',
                    radius: '55%',
                    center: ['50%', '50%'],
                    data:data.data,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }]

        })
    });
}