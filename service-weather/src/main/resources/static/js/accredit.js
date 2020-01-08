var App = function () {
    this.table = $('#accredit-table');

    this.Startup = function () {
        this.ReLayout();
        this.InitPortGrid();
        this.ReloadData();
        this.InitFiltrateGrid();
        this.ReloadFiltrateData();
        $('#add').on('click', this.OnAddButtonClick.bind(this));
        $('#add-close').on('click', this.AddDialogHide.bind(this));
        $('#add-sure').on('click', this.AddData.bind(this));
        $('#add-quit').on('click', this.AddDialogHide.bind(this));
        $('#add-switch a').on('click', this.OnSwitchButtonClick.bind(this));
        $('#add-create-key').on('click', this.AddDialogCreateKey.bind(this));

        $('#edit').on('click', this.OnEditButtonClick.bind(this));
        $('#edit-close').on('click', this.EditDialogHide.bind(this));
        $('#edit-sure').on('click', this.InsertData.bind(this));
        $('#edit-quit').on('click', this.EditDialogHide.bind(this));
        $('#edit-switch a').on('click', this.OnSwitchButtonClick.bind(this));
        window.onresize = this.ReLayout.bind(this);

    };

    this.ReLayout = function () {
        var height = $(window).height();
        $('.aside').height(height - 70);
        $('.accredit-table').height(height - 130);
    };

    this.ReloadData = function () {
        this.table.datagrid({
            method: "POST",
            url: 'caller/findCallerAuthorizationInfo',
            queryParams: {
                page: 1,
                rows: 10
            }
        });
    };

    this.InitPortGrid = function () {
        this.table.datagrid({
            columns: [[
                { field: 'loginName', title: '用户名', align: 'center', width: 30},
                { field: 'url', title: 'URL', align: 'center', width: 200}
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

    this.InitFiltrateGrid = function () {
        $('#filtrate-table').datagrid({
            columns: [[
                { field: 'id', checkbox: true, align: 'center', width: 30 },
                { field: 'name', title: '名称', align: 'center', width: 324 },
                { field: 'explain', title: '说明', align: 'center', width: 322 }
            ]],
            striped: true,
            singleSelect: false,
            fitColumns: true,
            fit: true,
            scrollbarSize: 0,
            onLoadSuccess: function () {

            }
        });
    };

    this.OnTableGridLoaded = function (data) {
        console.log(data);
        this.table.datagrid('selectRow', 0);
    };

    this.OnTableGridBeforeLoad = function () {
        this.table.datagrid('getPager').pagination({
            beforePageText: '第',
            afterPageText: '页&nbsp;&nbsp;&nbsp;共{pages}页',
            displayMsg: '当前显示{from}-{to}条记录&nbsp;&nbsp;&nbsp;共{total}条记录',
            layout: ['list', 'sep', 'first', 'prev', 'sep', 'manual', 'sep', 'next', 'last', 'sep', 'refresh', 'info']
        });
    };

    this.ReloadFiltrateData = function () {
        $.ajax({
            type: "POST",
            dataType: 'json',
            async: false,
            url: 'interface/getInterfaces',
            success: function (data) {
                var values = [];
                for (var i = 0; i < data.length; i++) {
                    values.push({
                        "id": data[i].id,
                        "name": data[i].name,
                        "explain": data[i].explain
                    });
                }
                $('#filtrate-table').datagrid('loadData', values);
            }.bind(this)
        });
    };

    this.OnAddButtonClick = function () {
        $('.dialog-add').show();
        $('.dialog-bg').show();
        $('#dialog-title').text('添加')
        $('#add-name').val(this.table.datagrid('getSelected').loginName);
        $("#filtrate-table").datagrid("resize");
        $("#filtrate-table").datagrid('clearSelections');
    };

    this.AddDialogHide = function () {
        $('.dialog-add').hide();
        $('.dialog-bg').hide();
    };

    this.AddData = function () {
        var ids = [];
        this.AddDialogHide();
        var row = this.table.datagrid('getSelected');
        var rows = $('#filtrate-table').datagrid('getSelections');
        for (var i = 0; i < rows.length; i++) {
            ids.push(rows[i].id);
        }
        $.ajax({
            type: "POST",
            data: {
                code: row.code,
                interfaceIds: ids
            },
            url: '/callerInterface/addCallerInterface',
            success: function () {
                this.ReloadData();
            }.bind(this)
        });
    };

    this.AddDialogCreateKey = function () {
        var uuId = this.CreateUUID();
        $('#add-key').attr('value', uuId);
    };

    this.CreateUUID = function () {
        return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
            return v.toString(16);
        });
    };

    this.OnEditButtonClick = function () {
        // $('.dialog-edit').show();
        // $('.dialog-bg').show();
        // var selected = this.table.datagrid('getSelected');
        // $('#edit-name').attr("value",selected.name);
        // $('#edit-number').attr("value",selected.code);
        // $('#edit-key').attr("value",selected.key);
        // if (selected.enabled === 1)
        //     $('#edit-switch').addClass('switch-on');
        // else
        //     $('#edit-switch').removeClass('switch-on');
        $('.dialog-add').show();
        $('.dialog-bg').show();
        $('#dialog-title').text('修改');
        var selected = this.table.datagrid('getSelected');
        var index = this.table.datagrid('getRowIndex',selected);
        $('#add-name').val(selected.loginName);
        var row = $("#accredit-table").datagrid('getRows')[index];

        $("#filtrate-table").datagrid("resize");
        var rows = $("#filtrate-table").datagrid("getRows");
        for (var i = 0; i < rows.length; i++) {
            var rowId = rows[i].Id;
            for (var j = 0; j < this.filtrateIds.length; j++) {
                if (rowId === this.filtrateIds[j]) {
                    var index = $("#filtrate-table").datagrid("getRowIndex", rows[i]);
                    $("#filtrate-table").datagrid("checkRow", index);
                }
            }
        }
    };

    this.EditDialogHide = function () {
        $('.dialog-edit').hide();
        $('.dialog-bg').hide();
    };

    this.InsertData = function () {
        this.EditDialogHide();
        $.ajax({
            type: "POST",
            dataType: 'json',
            data: {
                name: $('#edit-name').val(),
                code: $('#edit-number').val(),
                key: $('#edit-key').val(),
                enable: $('#edit-switch').hasClass('switch-on') ? 1 : 0
            },
            url: 'caller/update',
            success: function (result) {
                this.ReloadData();
            }.bind(this)
        });
    };

    this.OnSwitchButtonClick = function (event) {
        $(event.target).parent().toggleClass('switch-on');
    };
};

$(document).ready(function () {
    var app = new App();
    app.Startup();
});