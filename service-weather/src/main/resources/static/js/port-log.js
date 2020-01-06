var App = function () {

    this.Startup = function () {
        this.ReLayout();
        this.ReLoadInterfaceComboBoxData();
        this.ReLoadCallerComboBoxData();
        this.InitCalendar();
        this.ReloadPortTable();

        $('#query-btn').on('click', this.OnQueryButtonClick.bind(this));
        $('#query-btn').trigger("click");
        window.onresize = this.ReLayout.bind(this);
    };

    this.ReLayout = function () {
        var width = $('.content').width();
        var windowHeight = $(window).height();
        $('.aside').height(windowHeight - 70);
        $('.datagrid').width(width - 20);
        $('.log-table').width(width - 20);
        $('.log-table,.datagrid-wrap').height(windowHeight - 475);
    };

    this.ReLoadInterfaceComboBoxData = function () {
        var interfaces = $('#name');
        $.ajax({
            type: 'post',
            url: '/interface/getInterfaces',
            dataType: 'json',
            async: false,
            success: function (data) {
                data.unshift({"name": "全部" });
                interfaces .combobox({
                    valueField: 'name',
                    textField: 'name',
                    data: data,
                    onLoadSuccess: function (result) {
                        var item = interfaces .combobox('getData');
                        if (item.length > 0) {
                            interfaces .combobox('select', result[0].name);
                            interfaces .combobox('setValue', result[0].name);
                        }
                    }
                });
            }.bind(this)
        });
    };

    this.ReLoadCallerComboBoxData = function () {
        var caller = $('#caller');
        $.ajax({
            type: 'post',
            dataType: 'json',
            async: false,
            url: '/caller/findDepartment',
            success: function (data) {
                data.unshift({"department": "全部"});
                caller.combobox({
                    valueField: 'department',
                    textField: 'department',
                    data: data,
                    onLoadSuccess: function (result) {
                        var item = $('#caller').combobox('getData');
                        if (item.length > 0) {
                            caller.combobox('select', result[0].department);
                            caller.combobox('setValue', result[0].department);
                        }
                    }
                });
            }.bind(this)
        });
    };

    this.InitCalendar = function () {
        var startDate = $("#start-date");
        var endDate = $("#end-date");

        startDate.datebox({
            panelWidth: 203,
            panelHeight: 260
        });

        endDate.datebox({
            panelWidth: 203,
            panelHeight: 260
        });

        startDate.datebox("setValue", moment().add(-1, 'months').format('YYYY/MM/DD'));
        endDate.datebox("setValue", moment().add(0, 'days').format('YYYY/MM/DD'));
    };

    this.ReloadPortTable = function () {
        var width = $(window).width();
        $('#log-table').datagrid({
            columns: [[
                { field: 'name', title: '名称', align: 'center', width: width * 0.2},
                { field: 'state', title: '状态', align: 'center', width: width * 0.12, formatter: this.StateFormatter.bind(this)},
                { field: 'department', title: '调用者', align: 'center', width: width * 0.2},
                { field: 'executeStartTime', title: '开发时间', align: 'center', width: width * 0.12, formatter: this.DateFormatter.bind(this)},
                { field: 'executeEndTime', title: '结束时间', align: 'center', width: width * 0.12, formatter: this.DateFormatter.bind(this)},
                { field: 'elapsedTime', title: '耗时（s）', align: 'center', width: width * 0.2, formatter: this.TimeFormatter.bind(this)},
                { field: 'requestType', title:'请求类型', width:150, hidden: 'false'},
                { field: 'parameters', title: '请求参数', width:150, hidden: 'false'},
                { field: 'errorMessage', title:'错误信息', width:150, hidden: 'false'},
             ]],
            striped: true,
            singleSelect: true,
            fitColumns: true,
            fit: true,
            scrollbarSize: 0,
            pagination: true,
            pageNumber: 1,
            pageSize: 10,
            pageList: [10, 30, 50],
            loadMsg: '正在加载数据，请稍后...',
            onBeforeLoad: this.OnTableGridBeforeLoad.bind(this),
            onLoadSuccess: this.OnTableGridLoaded.bind(this),
            onSelect: this.SetDetailText.bind(this)
        });
    };

    this.TimeFormatter = function (value) {
        var item = value === 0 ? value : (value * 0.001).toFixed(2);
        return item;
    };

    this.StateFormatter = function (value) {
        if(value === '成功'){
            return '<span class="success">成功</span>';
        } else {
            return '<span class="fail">失败</span>';
        }
    };

    this.DateFormatter = function (value, row) {
        return moment(value).format('YYYY-MM-DD HH:mm:ss');
    };

    this.OnTableGridBeforeLoad = function () {
        $('#log-table').datagrid('getPager').pagination({
            beforePageText: '第',
            afterPageText: '页&nbsp;&nbsp;&nbsp;共{pages}页',
            displayMsg: '当前显示{from}-{to}条记录&nbsp;&nbsp;&nbsp;共{total}条记录',
            layout: ['list', 'sep', 'first', 'prev', 'sep', 'manual', 'sep', 'next', 'last', 'sep', 'refresh', 'info']
        });
    };

    this.OnTableGridLoaded = function (data) {
        $('#log-table').datagrid('selectRow', 0);
    };

    this.SetDetailText = function (index, data) {
        $('#request-url').text(data.name);
        $('#request-type').text(data.requestType)
        $('#request-param').text(data.parameters);
        $('#error-info').text(data.errorMessage);
    };

    this.OnQueryButtonClick = function () {
        this.ReLoadTableData();
    };

    this.ReLoadTableData = function () {
        var params = this.GetParams();
        $('#log-table').datagrid({
            method: "post",
            url: '/interfaceLog/findAllByPage',
            queryParams: params
        });
    };

    this.GetParams = function () {
        var name = $('#name').combobox('getValue');
        var caller = $('#caller').combobox('getValue');
        var requestStartTime = $('#start-date').datebox('getValue');
        var requestEndTime = $('#end-date').datebox('getValue');
        var state = $('#state').combobox('getValue');
        return{
            interfaceName : name === '全部'? 'all'  : name,
            department: caller === '全部'? 'all' : caller,
            requestStartTime: requestStartTime,
            requestEndTime: requestEndTime,
            state: state === '-1'? 'all' : state
        }
    };
};

$(document).ready(function () {
    var app = new App();
    app.Startup();
});