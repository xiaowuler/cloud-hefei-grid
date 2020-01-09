var App = function () {
    this.table = $('#configure-table');

    this.Startup = function () {
        this.ReLayout();
        this.InitConfigureGrid();
        $('#edit').on('click', this.OnEditButtonClick.bind(this));
        window.onresize = this.ReLayout.bind(this);
    };

    this.ReLayout = function () {
        var width = $('.content').width();
        var windowHeight = $(window).height();
        $('.aside').height(windowHeight - 70);
        $('.datagrid').width(width - 20);
        $('.configure-table').width(width - 20);
        $('.configure-table,.datagrid-wrap').height(windowHeight - 130);
    };

    this.InitConfigureGrid = function () {
        var width = $(window).width() - 214;
        this.table.datagrid({
            method: 'post',
            url: '/systemSetting/findAllByPage',
            columns: [[
                { field: 'name', title: '名称', align: 'center', width: width * 0.2},
                { field: 'value', title: '值', align: 'center', width: width * 0.2},
                { field: 'description', title: '描述', align: 'center', width: width * 0.2}
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

    this.OnEditButtonClick = function () {
        this.SettingEditDialog();
        this.SettingEditEvent();
    };

    this.SettingEditDialog = function () {
        $('.dialog-edit').show();
        $('.dialog-bg').show();

        var selected = this.table.datagrid('getSelected');
        $("#setting-id").attr("value", selected.id);
        $('#edit-name').attr("value",selected.name);
        $('#edit-value').attr("value",selected.value);
        $('#edit-describe').attr("value",selected.description);

        var index = this.table.datagrid('getRowIndex',selected.id);
        this.table.datagrid('beginEdit', index);
    };

    this.SettingEditEvent = function () {
        $('#edit-sure').on('click', this.EditConfigure.bind(this));
        $('#edit-cancel').on('click', this.EditDialogHide.bind(this));
        $('#edit-close').on('click', this.EditDialogHide.bind(this));
    };

    this.EditDialogHide = function () {
        $('.dialog-edit').hide();
        $('.dialog-bg').hide();
    };

    this.EditConfigure = function () {
        this.EditDialogHide();
        $.ajax({
            type: 'post',
            url: '/systemSetting/updateValueById',
            data: {
                id: $("#setting-id").attr('value'),
                value: $('#edit-value').val()
            },
            success: function (result) {
                this.InitConfigureGrid();
            }.bind(this)
        });
    };
};

$(document).ready(function () {
    var app = new App();
    app.Startup();
});