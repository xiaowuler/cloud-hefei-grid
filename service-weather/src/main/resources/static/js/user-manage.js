var App = function () {
    this.table = $('#user-table');

    this.Startup = function () {
        this.ReLayout();
        this.InitUserGrid();
        this.ReloadData();

        $('#add').on('click', this.OnAddButtonClick.bind(this));
        $('#add-close').on('click', this.AddDialogHide.bind(this));
        $('#add-sure').on('click', this.AddUser.bind(this));
        $('#add-quit').on('click', this.AddDialogHide.bind(this));
        $('#add-switch a').on('click', this.OnSwitchButtonClick.bind(this));

        $('#edit').on('click', this.OnEditButtonClick.bind(this));
        $('#edit-close').on('click', this.EditDialogHide.bind(this));
        $('#edit-sure').on('click', this.EditUser.bind(this));
        $('#edit-quit').on('click', this.EditDialogHide.bind(this));
        $('#edit-switch a').on('click', this.OnSwitchButtonClick.bind(this));

        $('#delete').on('click', this.OnDeleteButtonClick.bind(this));
        $('#delete-close').on('click', this.DeleteDialogHide.bind(this));
        $('#delete-sure').on('click', this.DeleteUser.bind(this));
        $('#delete-quit').on('click', this.DeleteDialogHide.bind(this));
        window.onresize = this.ReLayout.bind(this);
    };

    this.ReLayout = function () {
        var height = $(window).height();
        $('.aside').height(height - 70);
        $('.user-table').height(height - 130);
    };

    this.ReloadData = function () {
        this.table.datagrid({
            method: "POST",
            url: 'caller/findAllByPage',
            queryParams: {
                page: 1,
                rows: 10
            }
        });
    };

    this.InitUserGrid = function () {
        this.table.datagrid({
            columns: [[
                { field: 'code', title: '编号', align: 'center', width: 80},
                { field: 'department', title: '部门', align: 'center', width: 50},
                { field: 'loginName', title: '登录名', align: 'center', width: 50},
                { field: 'realName', title: '真实姓名', align: 'center', width: 50},
                { field: 'loginPassword', title: '登录密码', align: 'center', width: 50},
                { field: 'role', title: '角色', align: 'center', width: 40},
                { field: 'enabled', title: '是否启用', align: 'center', width: 50, formatter: this.StateFormatter.bind(this)}
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

    this.OnTableGridLoaded = function (data) {
        console.log(data);
        this.table.datagrid('selectRow', 0);
    };

    this.StateFormatter = function (value, row) {
        if(value === 1){
            return '<span class="enable">已启用</span>';
        } else {
            return '<span class="disable">已禁用</span>';
        }
    };

    this.OnTableGridBeforeLoad = function () {
        this.table.datagrid('getPager').pagination({
            beforePageText: '第',
            afterPageText: '页&nbsp;&nbsp;&nbsp;共{pages}页',
            displayMsg: '当前显示{from}-{to}条记录&nbsp;&nbsp;&nbsp;共{total}条记录',
            layout: ['list', 'sep', 'first', 'prev', 'sep', 'manual', 'sep', 'next', 'last', 'sep', 'refresh', 'info']
        });
    };

    this.OnAddButtonClick = function () {
        $('.dialog-add').show();
        $('.dialog-bg').show();
        $('.option i').hide();
        $('.option input').val("");
        $('.option input').css('borderColor','#53556c');
        $('#add-switch').addClass('switch-on')
    };

    this.AddDialogHide = function () {
        $('.dialog-add').hide();
        $('.dialog-bg').hide();
    };

    this.OnEditButtonClick = function () {
        $('.dialog-edit').show();
        $('.dialog-bg').show();

        var selected = this.table.datagrid('getSelected');
        $('#code').val(selected.code);
        $('#edit-department').val(selected.department);
        $('#edit-login-name').val(selected.loginName);
        $('#edit-real-name').val(selected.realName);
        $('#edit-password').val(selected.loginPassword);
        $('#edit-role').val(selected.role);
        if (selected.enabled === 1)
            $('#edit-switch').addClass('switch-on');
        else
            $('#edit-switch').removeClass('switch-on');
    };

    this.EditDialogHide = function () {
        $('.dialog-edit').hide();
        $('.dialog-bg').hide();
    };

    this.OnSwitchButtonClick = function (event) {
        $(event.target).parent().toggleClass('switch-on');
    };

    this.AddUser = function () {
        var department = $('#department').val().trim();
        var loginName =  $('#login-name').val().trim();
        var realName =  $('#real-name').val().trim();
        var loginPassword =  $('#login-password').val().trim();
        var role =  $('#role').val().trim();
        if (this.CheckInput(department, loginName, realName, loginPassword, role)){
            this.AddCaller(department, loginName, realName, loginPassword, role);
        }
    };

    this.CheckInput = function (department, loginName, realName, loginPassword, role) {
        var flag = true;
        if (department.length === 0){
            $('.check-department i').show();
            $('.check-department input').css({ 'borderColor': '#ff2828' });
            flag = false;
        } else {
            $('.check-department i').hide();
            $('.check-department input').css({ 'borderColor': '#53556c' });
        }

        if (loginName.length === 0){
            $('.check-login-name i').text('请输入登录名').show();
            $('.check-login-name input').css({ 'borderColor': '#ff2828' });
            flag = false;
        } else {
            if (this.IsExistLoginName(loginName) >= 1){
                $('.check-login-name i').text('登录名重复').show();
                $('.check-login-name input').css({ 'borderColor': '#ff2828' });
                flag = false;
            } else {
                $('.check-login-name i').hide();
                $('.check-login-name input').css({ 'borderColor': '#53556c' });
            }
        }

        if (realName.length === 0){
            $('.check-real-name i').show();
            $('.check-real-name input').css({ 'borderColor': '#ff2828' });
            flag = false;
        } else {
            $('.check-real-name i').hide();
            $('.check-real-name input').css({ 'borderColor': '#53556c' });
        }

        if (loginPassword.length === 0){
            $('.check-password i').show();
            $('.check-password input').css({ 'borderColor': '#ff2828' });
            flag = false;
        } else {
            $('.check-password i').hide();
            $('.check-password input').css({ 'borderColor': '#53556c' });
        }

        if (role.length === 0){
            $('.check-role i').show();
            $('.check-role input').css({ 'borderColor': '#ff2828' });
            flag = false;
        } else {
            $('.check-role i').hide();
            $('.check-role input').css({ 'borderColor': '#53556c' });
        }

        return flag
    };

    this.IsExistLoginName = function (name) {
        var value = null;
        $.ajax({
            type: 'post',
            url: 'caller/isExistLoginName',
            data: {loginName: name},
            async: false,
            success: function (result) {
                value = result;
            }
        });
        return value;
    };

    this.AddCaller = function (department, loginName, realName, loginPassword, role) {
        $.ajax({
            type: "POST",
            dataType: 'json',
            data: {
                department: department,
                loginName: loginName,
                realName: realName,
                loginPassword: loginPassword,
                role: role,
                enabled: $('#add-switch').hasClass('switch-on') ? 1 : 0
            },
            url: 'caller/addCaller',
            success: function () {
                this.AddDialogHide();
                this.ReloadData();
            }.bind(this)
        });
    };

    this.EditUser = function () {
        this.EditDialogHide();
        $.ajax({
            type: "POST",
            dataType: 'json',
            data: {
                code: this.table.datagrid('getSelected').code,
                department: $('#edit-department').val(),
                loginName: $('#edit-login-name').val(),
                realName: $('#edit-real-name').val(),
                loginPassword: $('#edit-password').val(),
                role: $('#edit-role').val(),
                enabled: $('#edit-switch').hasClass('switch-on') ? 1 : 0
            },
            url: 'caller/updateCaller',
            success: function () {
                this.ReloadData();
            }.bind(this)
        });
    };

    this.OnDeleteButtonClick = function () {
        $('.dialog-delete').show();
        $('.dialog-bg').show();
        $('#hint-txt').text(this.table.datagrid('getSelected').loginName)
    };

    this.DeleteDialogHide = function () {
        $('.dialog-delete').hide();
        $('.dialog-bg').hide();
    };

    this.DeleteUser = function () {
        this.DeleteDialogHide();
        $.ajax({
            type: "POST",
            dataType: 'json',
            data: {
                code: this.table.datagrid('getSelected').code,
            },
            url: 'caller/deleteCaller',
            success: function () {
                this.ReloadData();
            }.bind(this)
        });
    }
};

$(document).ready(function () {
    var app = new App();
    app.Startup();
});