<template>
    <div>
        <el-card>
            <div slot="header">
                <span class="overview-header-title">查询</span>
            </div>
            <div>
                <el-form :inline="true" :model="searchFormData" class="demo-form-inline" label-width="90px">
                    <el-form-item label="单位名称">
                        <el-input v-model="searchFormData.name" placeholder="请输入单位名称" clearable></el-input>
                    </el-form-item>
                    <el-form-item label="启用状态">
                        <el-select v-model="searchFormData.status" placeholder="全部" clearable>
                            <el-option v-for="item in projectStatus" :key="item.id" :label="item.label"
                                :value="item.value"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="queryData">
                            <svg-icon icon-class="search" class="marginRight" />查询
                        </el-button>
                        <el-button @click="resetEmpty">
                            <svg-icon icon-class="reset" class="marginRight" />重置
                        </el-button>
                    </el-form-item>
                </el-form>
            </div>
        </el-card>
        <el-card style="margin-top:10px">
            <div slot="header">
                <span class="overview-header-title">账号管理列表</span>
                <div class="btn">
                    <el-button type="primary" size="mini" icon="el-icon-plus" plain @click="addMethods">新 增</el-button>
                </div>
            </div>
            <div>
                <el-table ref="tableRefs" v-loading="loading" element-loading-text="拼命加载中" :data="listData"
                    highlight-current-row stripe :header-cell-style="{ background: '#eff3f7', border: 'none' }"
                    style="width:100%" height="48vh">
                    <el-table-column label="序号" width="100" align="center">
                        <template slot-scope="scope">
                            {{ (scope.$index + 1) + (page.pageNum - 1) * page.pageSize }}
                        </template>
                    </el-table-column>
                    <el-table-column label="登录账号" prop="account" align="center" show-overflow-tooltip></el-table-column>
                    <el-table-column label="单位名称" prop="companyName" align="center" show-overflow-tooltip></el-table-column>
                    <el-table-column label="区域" prop="regionName" align="center" show-overflow-tooltip></el-table-column>
                    <el-table-column label="角色" prop="roleNames" align="center" show-overflow-tooltip></el-table-column>
                    <el-table-column label="启用状态" prop="status" align="center" show-overflow-tooltip>
                        <template slot-scope="scope">
                            <el-switch v-model="scope.row.status" active-value="0" inactive-value="1"
                                @change="switchChange(scope.row)">
                            </el-switch>
                        </template>
                    </el-table-column>
                    <el-table-column label="创建时间" prop="createDatetime" align="center"
                        show-overflow-tooltip></el-table-column>
                    <el-table-column label="操作" align="center" width="240" fixed="right">
                        <template slot-scope="scope">
                            <el-button type="text" @click="editMethods(scope.row)">编辑</el-button>
                            <el-button type="text" @click="resetMethods(scope.row)" style="color:#fe9f21">重置</el-button>
                            <el-button type="text" @click="deleteMethods(scope.row)" style="color:#f67e7e">删除</el-button>
                        </template>
                    </el-table-column>
                    <template slot="empty">
                        <div>
                            <div slot="empty">
                                <p class="table-empty" style>
                                    <img src="@/assets/images/empty.png" class="table-empty-image"
                                        style="width: 130px; height: 130px" />
                                </p>
                                <p class="table-empty-text">
                                    <span>暂无数据</span>
                                </p>
                            </div>
                        </div>
                    </template>
                </el-table>
                <div style="text-align:right;padding-top:15px">
                    <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                        :current-page="page.pageNum" :page-sizes="[10, 20, 30, 40]" :page-size="page.pageSize"
                        layout="total, sizes, prev, pager, next, jumper" :total="total"></el-pagination>
                </div>
            </div>
        </el-card>

        <el-dialog :title="addOrEditName" :visible.sync="addOrEditVisible" append-to-body width="540px"
            v-if="addOrEditVisible">
            <div>
                <el-form ref="elDialogForm" :inline="false" :model="addOrEditData" class="demo-form-inline"
                    label-width="120px" v-if="addOrEditData" :rules="rules">
                    <el-form-item label="登录账号" prop="account">
                        <el-input v-model="addOrEditData.account" placeholder="请输入账号" style="width:300px"
                            clearable></el-input>
                    </el-form-item>
                    <el-form-item label="单位名称" prop="companyId">
                        <el-select v-model="addOrEditData.companyId" placeholder="请选择单位名称" style="width:300px" clearable
                            @change="unitChagne">
                            <el-option v-for="item in unitNameArr" :key="item.id" :label="item.companyName"
                                :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="区域" prop="regionName">
                        <el-input v-model="addOrEditData.regionName" placeholder="区域" style="width:300px"
                            disabled></el-input>
                    </el-form-item>
                    <el-form-item label="角色" prop="roleIds">
                        <el-select v-model="addOrEditData.roleIds" placeholder="单位" style="width:300px" clearable>
                            <el-option v-for="item in rolesObjectData" :key="item.id" :label="item.roleName"
                                :value="item.roleId"></el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addOrEditVisible = false">取 消</el-button>
                <el-button type="primary" @click="deteRmined">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { unitNameData, rolesSelectData, addHealData, getHealData, updateHealData, resetStatus, resetting, deleteHealData } from '@/api/accountManagement/unit.js'
export default {
    data() {
        var validateAccount = (rule, value, callback) => {
            if(value) {
                var usernameRegex = /^[a-zA-Z0-9]{1,100}$/;
                var isUsernameValid = usernameRegex.test(value);
                if(!isUsernameValid) {
                    return callback(new Error('账号只能由数字或字母组成'));
                }else {
                    callback()
                }
            }else {
                return callback(new Error('账号不能为空'));
            }
        };
        return {
            activeName: 'first',
            loading: false,
            addOrEditVisible: false,
            isStopUseVisible: false,
            addOrEditName: '新增单位账户',
            tipData: '是否确定停用该用户',
            searchFormData: {
                name: '',
                status: ''
            },
            addOrEditData: {
                account: '',
                companyId: '',
                roleIds: null,
                password: '123qwe'
            },
            isFlag: '1',
            unitNameArr: [],
            rolesObjectData: [],
            projectStatus: [
                {
                    label: '停用',
                    value: 1
                },
                {
                    label: '启用',
                    value: 0
                },
            ],
            listData: [],
            rules: {
                account: [
                    { required: true, validator: validateAccount, trigger: 'blur' }
                    // { required: true, message: '账号不能为空', trigger: 'blur' },
                ],
                companyId: [
                    { required: true, message: '请选择单位名称', trigger: 'change' },
                ],
                roleIds: [
                    { required: true, message: '角色不能为空', trigger: 'blur' },
                ],
                // regionName: [
                //     { required: true, message: '区域不能为空', trigger: 'change' },
                // ],
            },
            page: {
                pageNum: 1,
                pageSize: 10,
            },
            total: 0,
        }
    },
    mounted() {
        this.getSelectData()
        this.queryData()
    },
    methods: {
        getSelectData() {
            unitNameData({}).then(res => {
                this.unitNameArr = res.data
            })
            rolesSelectData().then(res => {
                this.rolesObjectData = res.data
            })
        },
        // 查询
        queryData() {
            this.page.pageNum = 1
            let obj = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                param: {
                    name: this.searchFormData.name,
                    status: this.searchFormData.status
                }
            }
            this.getHealData(obj)
        },
        getHealData(value) {
            getHealData(value).then(res => {
                if (res.code == 200) {
                    this.listData = res.rows
                    this.total = res.total
                }
            })
        },
        // 重置
        resetEmpty() {
            this.searchFormData = {
                name: '',
                status: ''
            },
                this.queryData()
        },
        // 启用状态
        switchChange(value) {
            if (value.status == '1') {
                this.$confirm("是否确定停用该用户", "操作确认", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    iconClass: 'el-icon-question',
                }).then(() => {
                    let obj = {
                        userId: value.userId,
                        status: value.status
                    }
                    console.log(obj);
                    resetStatus(obj).then(res => {
                        if (res.code == 200) {
                            this.$message({
                                message: '已停用',
                                type: 'success'
                            });
                        }
                        this.queryData()
                    })
                }).catch(() => {
                    this.queryData()
                });
            } else {
                let obj = {
                    userId: value.userId,
                    status: value.status
                }
                resetStatus(obj).then(res => {
                    if (res.code == 200) {
                        this.$message({
                            message: '已启用',
                            type: 'success'
                        });
                    }
                    this.queryData()
                })
            }
        },
        // 新增
        addMethods() {
            this.isFlag = '1'
            this.addOrEditName = '新增单位账户'
            this.addOrEditVisible = true
            this.addOrEditData = {
                account: '',
                companyId: '',
                roleIds: 7,
                password: '123qwe'
            }
        },
        // 编辑
        editMethods(row) {
            this.isFlag = 2
            this.addOrEditName = '编辑单位账户'
            this.addOrEditVisible = true
            console.log(row);
            this.addOrEditData = {
                id: row.id,
                account: row.account,
                companyId: row.companyId,
                regionName: row.regionName,
                roleIds: row.roleIds * 1,
                password: '123qwe',
                userId: row.userId
            }
        },
        // 重置密码
        resetMethods(value) {
            this.$confirm("是否确定重置该账户密码为 '123qwe'", "操作确认", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                iconClass: 'el-icon-question',
            }).then(() => {
                let obj = {
                    userId: value.userId,
                    password: '123qwe'
                }
                resetting(obj).then(res => {
                    if (res.code == 200) {
                        this.$message({
                            message: '重置成功',
                            type: 'success'
                        });
                    }
                })
            }).catch(() => {

            });
        },
        // 删除
        deleteMethods(value) {
            this.$confirm("是否确定删除该用户", "操作确认", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                iconClass: 'el-icon-question',
            }).then(() => {
                deleteHealData([value.id]).then(res => {
                    if (res.code == 200) {
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                    }
                    this.queryData()
                })
            }).catch(() => {

            });
        },
        // 新增/编辑 确认
        deteRmined() {
            this.$refs['elDialogForm'].validate((valid) => {
                if (valid) {
                    if (this.isFlag == 1) {
                        let obj = {
                            account: this.addOrEditData.account,
                            companyId: this.addOrEditData.companyId,
                            roleIds: [this.addOrEditData.roleIds * 1],
                            password: '123qwe'
                        }
                        console.log(obj);
                        addHealData(obj).then(res => {
                            if (res.code == 200) {
                                this.$message({
                                    message: '新增成功',
                                    type: 'success'
                                });
                                this.addOrEditVisible = false
                            } else {
                                this.$message({
                                    message: res.msg,
                                    type: 'warning'
                                });
                            }
                            this.queryData()
                        })
                    } else {
                        let obj = {
                            id: this.addOrEditData.id,
                            account: this.addOrEditData.account,
                            companyId: this.addOrEditData.companyId,
                            roleIds: [this.addOrEditData.roleIds * 1],
                            password: '123qwe',
                            userId: this.addOrEditData.userId,
                        }
                        updateHealData(obj).then(res => {
                            if (res.code == 200) {
                                this.$message({
                                    message: '编辑成功',
                                    type: 'success'
                                });
                                this.addOrEditVisible = false
                            } else {
                                this.$message({
                                    message: res.msg,
                                    type: 'warning'
                                });
                            }
                            this.queryData()
                        })
                    }
                }
            });
        },
        // dialog新增/编辑 单位名称
        unitChagne(value) {
            if (value) {
                var obj = {};
                //使用find()方法在下拉数据中根据value绑定的数据查找对象。
                obj = this.unitNameArr.find(function (item) {
                    return item.id === value;
                })
                this.addOrEditData.regionName = obj.regionName
            } else {
                this.addOrEditData.regionName = null
            }
        },
        // 分页
        handleSizeChange(value) {
            this.page.pageNum = 1;
            this.page.pageSize = value;
            let obj = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                param: {
                    name: this.searchFormData.name,
                    status: this.searchFormData.status
                }
            }
            this.getHealData(obj)
            this.$refs.tableRefs.bodyWrapper.scrollTop = 0;
        },
        handleCurrentChange(value) {
            this.page.pageNum = value;
            let obj = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                param: {
                    name: this.searchFormData.name,
                    status: this.searchFormData.status
                }
            }
            this.getHealData(obj)
            this.$refs.tableRefs.bodyWrapper.scrollTop = 0;
        }
    }
}
</script>

<style lang="scss" scoped>
.btn {
    float: right;
    position: relative;
    top: -6px;
}

.tip {
    position: relative;
    top: -10px;
    left: 10px;
}
</style>