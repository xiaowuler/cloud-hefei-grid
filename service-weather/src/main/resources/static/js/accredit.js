var App = function () {
    this.filtrateIds = [];
    this.table = $('#accredit-table');
    this.filtrateGrid = $("#filtrate-table");

    this.Startup = function () {
        this.ReLayout();
        this.InitPortGrid();
        this.ReloadData();
        this.InitFiltrateGrid();
        this.ReloadFiltrateData();
        $('#add').on('click', this.OnAddButtonClick.bind(this));
        $('#edit').on('click', this.OnEditButtonClick.bind(this));
        window.onresize = this.ReLayout.bind(this);
    };

    this.ReLayout = function () {
        var height = $(window).height();
        $('.aside').height(height - 70);
        $('.accredit-table').height(height - 130);
    };

    this.InitPortGrid = function () {
        this.table.datagrid({
            columns: [[
                { field: 'loginName', title: '用户名', align: 'center', width: 30},
                { field: 'url', title: 'URL', align: 'center', width: 200, formatter: function (value) {
                        return "<span title='" + value + "'>" + value + "</span>" }
                }
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
            onLoadSuccess: this.OnTableGridLoaded.bind(this)
        });
    };

    this.OnTableGridBeforeLoad = function () {
        this.table.datagrid('getPager').pagination({
            beforePageText: '第',
            afterPageText: '页&nbsp;&nbsp;&nbsp;共{pages}页',
            displayMsg: '当前显示{from}-{to}条记录&nbsp;&nbsp;&nbsp;共{total}条记录',
            layout: ['list', 'sep', 'first', 'prev', 'sep', 'manual', 'sep', 'next', 'last', 'sep', 'refresh', 'info']
        });
    };

    this.OnTableGridLoaded = function (data) {
        this.table.datagrid('selectRow', 0);
    };

    this.ReloadData = function () {
        this.table.datagrid({
            method: "POST",
            url: '/caller/findCallerAuthorizationInfo',
            queryParams: {
                page: 1,
                rows: 10
            }
        });
    };

    this.InitFiltrateGrid = function () {
        this.filtrateGrid.datagrid({
            columns: [[
                { field: 'id', checkbox: true, align: 'center', width: 30 },
                { field: 'name', title: '名称', align: 'center', width: 324 },
                { field: 'explain', title: '说明', align: 'center', width: 322 }
            ]],
            striped: true,
            singleSelect: false,
            fitColumns: true,
            fit: true,
            scrollbarSize: 0
        });
    };

    this.ReloadFiltrateData = function () {
        $.ajax({
            type: "POST",
            dataType: 'json',
            async: false,
            url: '/interface/getInterfaces',
            success: function (data) {
                var values = [];
                for (var i = 0; i < data.length; i++) {
                    values.push({
                        "id": data[i].id,
                        "name": data[i].name,
                        "explain": data[i].explain
                    });
                }
                this.filtrateGrid.datagrid('loadData', values);
            }.bind(this)
        });
    };

    this.OnAddButtonClick = function () {
        this.SettingAddDialog();
        this.SettingAddAttrAndEvent();
    };

    this.SettingAddDialog = function () {
        $('.dialog-common').show();
        $('.dialog-bg').show();
        $('#dialog-title').text('添加')
        this.InitLoginName();
        this.filtrateGrid.datagrid("resize");
        this.filtrateGrid.datagrid('clearSelections');
    };

    this.SettingAddAttrAndEvent = function () {
        $('.sure').attr('id', 'add-sure');
        $('.cancel').attr('id', 'add-cancel');
        $('.close').attr('id', 'add-close');

        $('#add-sure').off('click').on('click', this.AddCallerInterfaces.bind(this));
        $('#add-cancel').off('click').on('click', this.DialogCommonHide.bind(this));
        $('#add-close').off('click').on('click', this.DialogCommonHide.bind(this));
    };

    this.InitLoginName = function () {
        var $loginName = $('#login-name');
        $loginName.combobox({
            method: 'post',
            url: '/caller/findCodeAndLoginName',
            valueField: 'code',
            textField: 'loginName',
            editable:false,
            onLoadSuccess: function (result) {
                var item = $loginName.combobox('getData');
                if (item.length > 0) {
                    $loginName.combobox('select', result[0].loginName);
                    $loginName.combobox('setValue', result[0].code);
                }
            }
        }).combobox('enable');
    };

    this.AddCallerInterfaces = function () {
        var code = $('#login-name').combobox('getValue');
        if (this.CheckLoginNameCode(code))
            return;

        var interfaceIds = this.GetInterfaceIds();
        if (this.CheckFiltrateGridSelected(interfaceIds))
            return;

        $.ajax({
            type: 'post',
            url: '/callerInterface/addCallerInterface',
            data: {code: code, interfaceIds: interfaceIds},
            success: function () {
                this.ReloadData();
            }.bind(this)
        });
        this.DialogCommonHide();
    };

    this.CheckLoginNameCode = function (code) {
        if (code.length === 0){
            $('#login-name-hint').text('用户全部授权').css('color', '#ff2828');
            $('#login-name-hint').show();
            return true;
        }
        return false;
    };

    this.OnEditButtonClick = function () {
        this.SettingUpdateDialog();
        this.SettingEditAttrAndEvent();
    };

    this.SettingUpdateDialog = function () {
        var selected = this.table.datagrid('getSelected');
        this.filtrateGrid.datagrid('clearSelections');
        this.GetFiltrateData(selected);

        $('.dialog-common').show();
        $('.dialog-bg').show();
        $('#dialog-title').text('修改');
        this.filtrateGrid.datagrid("resize");
        $('#login-name').combobox('select', selected.loginName).combobox('disable');

        var rows = this.filtrateGrid.datagrid("getRows");
        for (var i = 0; i < rows.length; i++) {
            var rowId = rows[i].id;
            for (var j = 0; j < this.filtrateIds.length; j++) {
                if (rowId === this.filtrateIds[j]) {
                    var index = this.filtrateGrid.datagrid("getRowIndex", rows[i]);
                    this.filtrateGrid.datagrid("checkRow", index);
                }
            }
        }
    };

    this.GetFiltrateData = function (row) {
        $.ajax({
            type: "POST",
            dataType: 'json',
            async: false,
            data: {code: row.code},
            url: '/callerInterface/findInterfaceIdByCode',
            success: function (data) {
                this.filtrateIds = data;
            }.bind(this)
        });
    };

    this.SettingEditAttrAndEvent = function () {
        $('.sure').removeAttr('id').attr('id', 'edit-sure');
        $('.cancel').removeAttr('id').attr('id', 'edit-cancel');
        $('.close').removeAttr('id').attr('id', 'edit-close')

        $('#edit-sure').off('click').on('click', this.UpdateCallerInterface.bind(this));
        $('#edit-cancel').off('click').on('click', this.DialogCommonHide.bind(this));
        $('#edit-close').off('click').on('click', this.DialogCommonHide.bind(this));
    };

    this.UpdateCallerInterface = function () {
        var selected = this.table.datagrid('getSelected');
        var code = selected.code;
        var interfaceIds = this.GetInterfaceIds();
        if (this.CheckFiltrateGridSelected(interfaceIds))
            return;

        $.ajax({
            type: 'post',
            url: '/callerInterface/updateCallerInterface',
            data: {code: code, interfaceIds: interfaceIds},
            success: function () {
                this.ReloadData();
            }.bind(this)
        });
        this.DialogCommonHide();
    };

    this.GetInterfaceIds = function () {
        var interfaceIds = [];
        var rows = this.filtrateGrid.datagrid('getSelections');
        for (var i = 0; i < rows.length; i++) {
            interfaceIds.push(rows[i].id);
        }
        return interfaceIds;
    };

    this.CheckFiltrateGridSelected = function (interfaceIds) {
        if(interfaceIds.length === 0){
            $('#error').text('请选择接口').css('color', '#ff2828');
            $('#error').show();
            return true;
        }
        return false;
    };

    this.DialogCommonHide = function () {
        $('#login-name-hint').hide()
        $('#error').hide();
        $('.dialog-common').hide();
        $('.dialog-bg').hide();
    };
};

$(document).ready(function () {
    var app = new App();
    app.Startup();
});