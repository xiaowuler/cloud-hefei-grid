var App = function () {
    this.table = $('#user-table');

    this.Startup = function () {
        this.ReLayout();
        this.InitUserGrid();
        this.ReloadData();
        $('#add').on('click', this.OnAddButtonClick.bind(this));
        $('#edit').on('click', this.OnEditButtonClick.bind(this));
        $('#delete').on('click', this.OnDeleteButtonClick.bind(this));
        window.onresize = this.ReLayout.bind(this);
    };

    this.ReLayout = function () {
        var height = $(window).height();
        $('.aside').height(height - 70);
        $('.user-table').height(height - 130);
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

    this.StateFormatter = function (value, row) {
        if(value === 1){
            return '<span class="enable">已启用</span>';
        } else {
            return '<span class="disable">已禁用</span>';
        }
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

    this.OnAddButtonClick = function () {
        this.SettingAddDialog();
        this.SettingAddEvent();
    };

    this.SettingAddDialog = function () {
        $('.dialog-add').show();
        $('.dialog-bg').show();
        $('.option i').hide();
        $('.option input').val('');
        $('.option input').css('borderColor','#53556c');
        $('#add-switch').addClass('switch-on')
    };

    this.SettingAddEvent = function () {
        $('#add-sure').on('click', this.AddCaller.bind(this));
        $('#add-cancel').on('click', this.AddDialogHide.bind(this));
        $('#add-close').on('click', this.AddDialogHide.bind(this));
        $('#add-switch a').on('click', this.OnSwitchButtonClick.bind(this));
    };

    this.AddCaller = function () {
        var department = $('#department').val().trim();
        var loginName =  $('#login-name').val().trim();
        var realName =  $('#real-name').val().trim();
        var loginPassword =  $('#login-password').val().trim();
        var role =  $('#role').val().trim();

        if (this.CheckAddInput(department, loginName, realName, loginPassword, role)){
            this.Add(department, loginName, realName, loginPassword, role);
        }
    };

    this.CheckAddInput = function (department, loginName, realName, loginPassword, role) {
        var flag = true;
        var loginNameError = $('.check-login-name i');
        var loginNameInput = $('.check-login-name input');

        flag = this.CheckCommonInput(department, realName, loginPassword, role);

        if (loginName.length === 0){
            loginNameError.text('请输入登录名').show();
            this.SetWarnFontColor(loginNameInput);
            flag = false;
        } else {
            if (this.IsExistLoginName(loginName) >= 1){
                loginNameError.text('登录名重复').show();
                this.SetWarnFontColor(loginNameInput);
                flag = false;
            } else {
                loginNameError.hide();
                this.SetDefaultFontColor(loginNameInput);
            }
        }
        return flag
    };

    this.IsExistLoginName = function (name) {
        var value = null;
        $.ajax({
            type: 'post',
            url: '/caller/isExistLoginName',
            data: {loginName: name},
            async: false,
            success: function (result) {
                value = result;
            }
        });
        return value;
    };

    this.Add = function (department, loginName, realName, loginPassword, role) {
        $.ajax({
            type: 'post',
            url: '/caller/addCaller',
            data: {
                department: department,
                loginName: loginName,
                realName: realName,
                loginPassword: loginPassword,
                role: role,
                enabled: $('#add-switch').hasClass('switch-on') ? 1 : 0
            },
            dataType: 'json',
            success: function () {
                this.AddDialogHide();
                this.ReloadData();
            }.bind(this)
        });
    };

    this.AddDialogHide = function () {
        $('.dialog-add').hide();
        $('.dialog-bg').hide();
    };

    this.OnEditButtonClick = function () {
        this.SettingEditDialog();
        this.SettingEditEvent();
    };

    this.SettingEditDialog = function () {
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

    this.SettingEditEvent = function () {
        $('#edit-sure').on('click', this.EditCaller.bind(this));
        $('#edit-cancel').on('click', this.EditDialogHide.bind(this));
        $('#edit-close').on('click', this.EditDialogHide.bind(this));
        $('#edit-switch a').on('click', this.OnSwitchButtonClick.bind(this));
    };

    this.EditDialogHide = function () {
        $('.dialog-edit').hide();
        $('.dialog-bg').hide();
    };

    this.OnSwitchButtonClick = function (event) {
        $(event.target).parent().toggleClass('switch-on');
    };

    this.EditCaller = function () {
        var code = this.table.datagrid('getSelected').code
        var department = $('#edit-department').val();
        var loginName = $('#edit-login-name').val();
        var realName = $('#edit-real-name').val();
        var loginPassword = $('#edit-password').val();
        var role = $('#edit-role').val();
        if (this.CheckUpdateInput(code, department, loginName, realName, loginPassword, role))
            this.Edit(code, department, loginName, realName, loginPassword, role);

    };

    this.CheckUpdateInput = function (code, department, loginName, realName, loginPassword, role) {
        var flag = true;
        var loginNameError = $('.check-login-name i');
        var loginNameInput = $('.check-login-name input');

        flag = this.CheckCommonInput(department, realName, loginPassword, role);

        if (loginName.length === 0){
            loginNameError.text('请输入登录名').show();
            this.SetWarnFontColor(loginNameInput);
            flag = false;
        } else {
            if (this.isExistLoginNameByUpdate(code, loginName) >= 1){
                loginNameError.text('登录名重复').show();
                this.SetWarnFontColor(loginNameInput);
                flag = false;
            } else {
                loginNameError.hide();
                this.SetDefaultFontColor(loginNameInput);
            }
        }
        return flag
    };

    this.isExistLoginNameByUpdate = function (code, loginName) {
        var value = null;
        $.ajax({
            type: 'post',
            url: '/caller/isExistLoginNameByUpdate',
            data: {code: code, loginName: loginName},
            async: false,
            success: function (result) {
                value = result;
            }
        });
        return value;
    };

    this.Edit = function (code, department, loginName, realName, loginPassword, role) {
        $.ajax({
            type: 'post',
            url: '/caller/updateCaller',
            dataType: 'json',
            data: {
                code: code,
                department: department,
                loginName: loginName,
                realName: realName,
                loginPassword: loginPassword,
                role: role,
                enabled: $('#edit-switch').hasClass('switch-on') ? 1 : 0
            },
            success: function () {
                this.EditDialogHide();
                this.ReloadData();
            }.bind(this)
        });
    };

    this.CheckCommonInput = function (department, realName, loginPassword, role) {
        var flag = true;

        var departmentError = $('.check-department i');
        var realNameError = $('.check-real-name i');
        var passwordError = $('.check-password i');
        var roleError = $('.check-role i');

        var departmentInput = $('.check-department input');
        var realNameInput = $('.check-real-name input');
        var passwordInput = $('.check-password input');
        var roleInput = $('.check-role input');

        if (department.length === 0){
            departmentError.show();
            this.SetWarnFontColor(departmentInput);
            flag = false;
        } else {
            departmentError.hide();
            this.SetDefaultFontColor(departmentInput);
        }

        if (realName.length === 0){
            realNameError.show();
            this.SetWarnFontColor(realNameInput);
            flag = false;
        } else {
            realNameError.hide();
            this.SetDefaultFontColor(realNameInput);
        }

        if (loginPassword.length === 0){
            passwordError.show();
            this.SetWarnFontColor(passwordInput);
            flag = false;
        } else {
            passwordError.hide();
            this.SetDefaultFontColor(passwordInput);
        }

        if (role.length === 0){
            roleError.show();
            this.SetWarnFontColor(roleInput);
            flag = false;
        } else {
            roleError.hide();
            this.SetDefaultFontColor(roleInput);
        }

        return flag;
    };

    this.SetWarnFontColor = function (element) {
        element.css('borderColor','#ff2828');
    };

    this.SetDefaultFontColor = function (element) {
        element.css('borderColor','#53556c');
    };

    this.OnDeleteButtonClick = function () {
        this.SettingDeleteDialog();
        this.SettingDeleteEvent();
    };

    this.SettingDeleteDialog = function () {
        $('.dialog-delete').show();
        $('.dialog-bg').show();
        $('#hint-txt').text(this.table.datagrid('getSelected').loginName);
    };

    this.SettingDeleteEvent = function () {
        $('#delete-sure').on('click', this.DeleteCaller.bind(this));
        $('#delete-cancel').on('click', this.DeleteDialogHide.bind(this));
        $('#delete-close').on('click', this.DeleteDialogHide.bind(this));
    };

    this.DeleteCaller = function () {
        this.DeleteDialogHide();
        $.ajax({
            type: 'post',
            url: '/caller/deleteCaller',
            dataType: 'json',
            data: {code: this.table.datagrid('getSelected').code},
            success: function () {
                this.ReloadData();
            }.bind(this)
        });
    }

    this.DeleteDialogHide = function () {
        $('.dialog-delete').hide();
        $('.dialog-bg').hide();
    };
};

$(document).ready(function () {
    var app = new App();
    app.Startup();
});