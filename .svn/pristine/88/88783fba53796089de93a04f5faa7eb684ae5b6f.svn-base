<template>
    <div>
        <el-card>
            <div slot="header">
                <span class="overview-header-title">查询</span>
            </div>
            <div>
                <el-form :inline="true" :model="searchFormData" class="demo-form-inline" label-width="90px">
                    <el-form-item label="姓名">
                        <el-input v-model="searchFormData.name" placeholder="请输入姓名" clearable></el-input>
                    </el-form-item>
                    <el-form-item label="标签">
                        <el-cascader v-model="searchFormData.subjectIds" :options="labelSelectArr" :props="optionProps" style="width:300px" placeholder="请选择标签"
                            clearable width="1400px" filterable collapse-tags></el-cascader>
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
                    <el-table-column label="专家类型" prop="expertTypeNames" align="center"
                        show-overflow-tooltip></el-table-column>
                    <el-table-column label="账号" prop="account" align="center" show-overflow-tooltip></el-table-column>
                    <el-table-column label="姓名" prop="expertName" align="center" show-overflow-tooltip></el-table-column>
                    <el-table-column label="性别" prop="sex" align="center" show-overflow-tooltip></el-table-column>
                    <el-table-column label="职称" prop="positionalTitle" align="center"
                        show-overflow-tooltip></el-table-column>
                    <el-table-column label="标签" prop="subjectNames" align="center" show-overflow-tooltip></el-table-column>
                    <el-table-column label="角色" prop="roleNames" align="center" show-overflow-tooltip></el-table-column>
                    <el-table-column label="单位名称" prop="companyName" align="center" show-overflow-tooltip></el-table-column>
                    <el-table-column label="应用状态" prop="status" align="center" show-overflow-tooltip>
                        <template slot-scope="scope">
                            <el-switch v-model="scope.row.status" active-value="0" inactive-value="1"
                                @change="switchChange(scope.row)">
                            </el-switch>
                        </template>
                    </el-table-column>
                    <el-table-column label="创建时间" prop="createDatetime" align="center"
                        show-overflow-tooltip></el-table-column>
                    <el-table-column label="操作" align="center" width="240">
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
                    label-width="120px" :rules="rules">
                    <el-form-item label="专家类型" prop="expertType">
                        <el-select v-model="addOrEditData.expertType" placeholder="请选择专家类型" style="width:300px" clearable>
                            <el-option v-for="item in expertTypeArr" :key="item.id" :label="item.dictLabel"
                                :value="item.dictValue"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="账号" prop="account">
                        <el-input v-model="addOrEditData.account" placeholder="请输入账号" style="width:300px"
                            clearable></el-input>
                    </el-form-item>
                    <el-form-item label="姓名" prop="expertName">
                        <el-input v-model="addOrEditData.expertName" placeholder="请输入姓名" style="width:300px"
                            clearable></el-input>
                    </el-form-item>
                    <el-form-item label="性别" prop="sex">
                        <el-radio-group v-model="addOrEditData.sex" style="width:300px">
                            <el-radio label="1">男</el-radio>
                            <el-radio label="2">女</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="职称" prop="positionalTitleId">
                        <el-select v-model="addOrEditData.positionalTitleId" placeholder="职称选择" style="width:300px"
                            clearable>
                            <el-option v-for="item in positionalObjectData" :key="item.id" :label="item.positionalTitleName"
                                :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="标签" prop="subjectIds">
                        <el-cascader v-model="addOrEditData.subjectIds" :options="labelSelectArr" :props="optionProps" style="width:300px" placeholder="请选择标签"
                            clearable width="1400px" filterable collapse-tags></el-cascader>
                        <!-- <el-select v-model="addOrEditData.subjectIds" placeholder="请选择标签" clearable filterable multiple
                            style="width:300px" collapse-tags>
                            <el-option v-for="item in labelSelectArr" :key="item.id" :label="item.subjectName"
                                :value="item.subjectId"></el-option>
                        </el-select> -->
                    </el-form-item>
                    <el-form-item label="角色" prop="roleIds">
                        <el-select v-model="addOrEditData.roleIds" placeholder="专家" style="width:300px" clearable>
                            <el-option v-for="item in rolesObjectData" :key="item.id" :label="item.roleName"
                                :value="item.roleId"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="单位名称" prop="companyId">
                        <el-select v-model="addOrEditData.companyId" placeholder="请选择单位名称" style="width:300px" clearable>
                            <el-option v-for="item in unitNameArr" :key="item.id" :label="item.companyName"
                                :value="item.id"></el-option>
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
import {
    unitNameData, rolesSelectData, addHealData, getHealData,
    updateHealData, resetStatus, resetting, deleteHealData,
    getExpertSelect, positionalSelectData, labelSelectData
} from '@/api/accountManagement/expert.js'
export default {
    data() {
        var validateAccount = (rule, value, callback) => {
            if (value) {
                // var usernameRegex = /^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$/;
                var usernameRegex = /^[a-zA-Z0-9]{1,10}$/;
                var isUsernameValid = usernameRegex.test(value);
                if (!isUsernameValid) {
                    return callback(new Error('账号只能由数字或字母组成'));
                } else {
                    callback()
                }
            } else {
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
                status: '',
                subjectIds: []
            },
            labelSelectArr: [],
            optionProps: { value: 'id', multiple: true,emitPath:false },
            expertTypeArr: [],
            addOrEditData: {
                expertType: '',
                account: '',
                expertName: '',
                sex: '1',
                positionalTitleId: '',
                subjectIds: [],
                roleIds: '',
                companyId: '',
                password: '123qwe'
            },
            isFlag: '1',
            unitNameArr: [],
            positionalObjectData: [],
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
            labelText: '',
            valueText: '',
            listData: [],
            rules: {
                expertType: [
                    { required: true, message: '请选择专家类型', trigger: 'change' },
                ],
                account: [
                    { required: true, validator: validateAccount, trigger: 'blur' }
                    // { required: true, message: '账号不能为空', trigger: 'blur' },
                ],
                expertName: [
                    { required: true, message: '姓名不能为空', trigger: 'blur' },
                ],
                sex: [
                    { required: true, message: '请选择性别', trigger: 'change' },
                ],
                positionalTitleId: [
                    { required: true, message: '请选择职称', trigger: 'change' },
                ],
                subjectIds: [
                    { required: true, message: '请选择标签', trigger: 'change' },
                ],
                roleIds: [
                    { required: true, message: '请选择角色', trigger: 'change' },
                ],
                companyId: [
                    { required: true, message: '请选择单位名称', trigger: 'change' },
                ],
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
            getExpertSelect().then(res => {
                this.expertTypeArr = res.data
            })
            positionalSelectData({}).then(res => {
                this.positionalObjectData = res.data
            })
            rolesSelectData().then(res => {
                this.rolesObjectData = res.data
            })
            unitNameData({}).then(res => {
                this.unitNameArr = res.data
            })
            labelSelectData({}).then(res => {
                this.labelSelectArr = res.data
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
                    status: this.searchFormData.status,
                    subjectIds: this.searchFormData.subjectIds
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
                status: '',
                subjectIds: null
            },
                this.queryData()
        },
        labelChange(value) {
            // if (value) {
            //     this.labelText = value.join(',')
            // } else {
            //     this.labelTex = ''
            // }
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
            this.addOrEditName = '新增专家账户'
            this.addOrEditVisible = true
            this.addOrEditData = {
                expertType: '',
                account: '',
                expertName: '',
                sex: '1',
                positionalTitleId: '',
                subjectIds: [],
                roleIds: 8,
                companyId: '',
                password: '123qwe'
            }
        },
        // 编辑
        editMethods(row) {
            let arr = []
            this.isFlag = 2
            this.addOrEditName = '编辑专家账户'
            this.addOrEditVisible = true
            if (row.subjectIds) {
                arr = row.subjectIds.split(',')
                console.log(arr);
            }
            let rows = arr.map(v => {
                return v * 1
            })
            this.addOrEditData = {
                id: row.id,
                userId: row.userId,
                expertType: row.expertType,
                account: row.account,
                expertName: row.expertName,
                sex: row.sex == '男' ? '1' : '2',
                positionalTitleId: row.positionalTitleId,
                subjectIds: rows,
                roleIds: row.roleIds * 1,
                companyId: row.companyId,
                password: row.password
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
                            expertType: this.addOrEditData.expertType,
                            account: this.addOrEditData.account,
                            expertName: this.addOrEditData.expertName,
                            sex: this.addOrEditData.sex,
                            positionalTitleId: this.addOrEditData.positionalTitleId,
                            subjectIds: this.addOrEditData.subjectIds,
                            roleIds: [this.addOrEditData.roleIds * 1],
                            companyId: this.addOrEditData.companyId,
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
                            userId: this.addOrEditData.userId,
                            expertType: this.addOrEditData.expertType,
                            account: this.addOrEditData.account,
                            expertName: this.addOrEditData.expertName,
                            sex: this.addOrEditData.sex,
                            positionalTitleId: this.addOrEditData.positionalTitleId,
                            subjectIds: this.addOrEditData.subjectIds,
                            roleIds: [this.addOrEditData.roleIds * 1],
                            companyId: this.addOrEditData.companyId,
                            password: '123qwe'
                        }
                        console.log(obj);
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
        // 分页
        handleSizeChange(value) {
            this.page.pageNum = 1;
            this.page.pageSize = value;
            let obj = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                param: {
                    name: this.searchFormData.name,
                    status: this.searchFormData.status,
                    subjectIds: this.searchFormData.subjectIds
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
                    status: this.searchFormData.status,
                    subjectIds: this.searchFormData.subjectIds
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