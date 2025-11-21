<template>
  <div class="app-container">
    <div class="dashboard-container">
      <el-card class="search-box">
        <div slot="header">
          <span class="overview-header-title">查询</span>
        </div>
        <div>
          <el-form
            :inline="true"
            :model="listQuery"
            class="demo-form-inline"
            label-width="80px"
          >
            <el-form-item label="科室名称">
              <el-input
                v-model="listQuery.param.distributeName"
                placeholder="请输入科室名称"
                clearable
                style="width: 280px"
              ></el-input>
            </el-form-item>
            <el-form-item label="科室代码">
              <el-input
                v-model="listQuery.param.distributeName"
                placeholder="请输入科室代码"
                clearable
                style="width: 280px"
              ></el-input>
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
      <el-card style="margin-top: 10px">
        <div slot="header">
          <span class="overview-header-title">科室管理</span>
          <div style="float: right">
            <div style="display: flex; align-items: center">
              <el-button
                size="mini"
                type="primary"
                plain
                @click="handleAdd($event)"
                icon="el-icon-plus"
                style="margin-right: 6px"
              >
                新增科室
              </el-button>
            </div>
          </div>
        </div>
        <div>
          <el-table
            ref="tableRefs"
            v-loading="loading"
            element-loading-text="拼命加载中"
            :data="listData"
            highlight-current-row
            stripe
            :header-cell-style="{ background: '#eff3f7', border: 'none' }"
            :row-style="{ height: '56px' }"
            style="width: 100%"
            height="52.2vh"
          >
            <el-table-column
              label="序号"
              type="index"
              fixed="left"
              align="center"
            >
              <template slot-scope="scope">
                <span>{{ (pageNum - 1) * pageSize + scope.$index + 1 }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="科室名称"
              prop="distributeCode"
              align="center"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              label="科室代码"
              prop="distributeName"
              align="center"
              show-overflow-tooltip
            ></el-table-column>

            <el-table-column
              label="操作"
              align="center"
              show-overflow-tooltip
              width="300"
            >
              <template slot-scope="scope">
                <el-button
                  type="text"
                  title="编辑"
                  v-hasPermi="['menuUpdate']"
                  @click="handleEdit(scope.row, $event)"
                  >编辑</el-button
                >
                <el-button
                  type="text"
                  title="删除"
                  v-hasPermi="['menuDeleteById']"
                  @click="handleDelete(scope.row, $event)"
                  style="color: #f56c6c"
                  >删除</el-button
                >
              </template>
            </el-table-column>
            <template slot="empty">
              <div>
                <!-- <el-empty :image-size="200"></el-empty> -->
                <div slot="empty">
                  <p class="table-empty" style>
                    <img
                      src="@/assets/images/empty.png"
                      class="table-empty-image"
                      style="width: 130px; height: 130px"
                    />
                  </p>
                  <p class="table-empty-text">
                    <span>暂无数据</span>
                  </p>
                </div>
              </div>
            </template>
          </el-table>
          <div style="text-align: right; padding-top: 15px">
            <pagination
              :total="total"
              :page.sync="listQuery.pageNum"
              :limit.sync="listQuery.pageSize"
              @pagination="getData"
            />
          </div>
        </div>
      </el-card>
    </div>

    <!-- 新增/编辑 人员 -->
    <el-dialog
      :title="title"
      :visible.sync="addOrEditVisible"
      append-to-body
      width="600px"
      v-if="addOrEditVisible"
    >
      <div>
        <el-form
          ref="personForm"
          :inline="false"
          :model="personForm"
          class="demo-form-inline"
          label-width="100px"
          :rules="personRules"
        >
          <el-form-item label="科室名称" prop="distributeCode">
            <el-input
              v-model="personForm.distributeCode"
              placeholder="请输入科室名称"
              style="width: 420px"
              clearable
              maxlength="32"
            ></el-input>
          </el-form-item>
          <el-form-item label="科室代码" prop="distributeName">
            <el-input
              v-model="personForm.distributeName"
              placeholder="请输入科室代码"
              style="width: 420px"
              maxlength="40"
              clearable
            ></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addOrEditVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { strTrim } from "@/utils";
import {
  getDataByPage,
  handleSave,
  handleUpdate,
  handleDelete,
  getDataById,
  checkToken,
  reflashToken,
} from "@/api/deliveryCompany";
import print from "vue-print-nb";

export default {
  name: "DeliveryCompany",
  dicts: ["delivery_person_type"],
  directives: {
    print,
  },
  data() {
    return {
      loading: false,
      dateRange: [],
      listData: [],
      listQuery: {
        pageNum: 1,
        pageSize: 10,
        param: {
          distributeName: undefined,
          distributeCode: undefined,
          status: undefined, //状态（0正常 1停用）
        },
      },
      total: 0,
      statusLists: [
        { id: 0, label: "正常" },
        { id: 1, label: "停用" },
      ],
      addOrEditVisible: false,
      selectedNode: [],
      title: "",
      personForm: {
        distributeCode: "",
        distributeName: "",
        status: 0, //状态（0正常 1停用）
      },
      // 表单校验
      personRules: {
        distributeCode: [
          { required: true, message: "请填写企业编码", trigger: "blur" },
        ],
        distributeName: [
          { required: true, message: "请填写企业名称", trigger: "blur" },
        ],
      },
      tokenRules: {
        password: [
          { required: true, message: "请填写登录密码", trigger: "blur" },
        ],
      },
      codeVisible: false,
      addTokenVisible: false,
      detailInfo: {},
      dialogShowType: "0",
      tokenText: "",
      printArr: [],
      tokenForm: {
        password: "",
      },
    };
  },
  created() {
    this.getData();
  },
  mounted() {},

  methods: {
    //获取标准列表信息
    getData() {
      this.loading = true;
      getDataByPage(this.listQuery).then((res) => {
        this.listData = res.rows;
        this.total = res.total;
        this.loading = false;
      });
    },

    // table多选
    handleSelection(val) {
      this.selectedNode = val;
    },
    /** 新增人员 */
    handleAdd(event) {
      this.clearBlur(event);
      this.title = "新增企业";
      this.addOrEditVisible = true;
      this.personForm = this.$options.data().personForm;
      this.$nextTick(() => {
        this.$refs.personForm.resetFields();
      });
    },
    /** 编辑人员 */
    handleEdit(row, event) {
      this.clearBlur(event);
      this.title = "编辑企业";
      this.personForm = JSON.parse(JSON.stringify(row));
      this.addOrEditVisible = true;
    },

    /** 新增和编辑提交按钮 */
    submitForm: function () {
      this.$refs["personForm"].validate((valid) => {
        if (valid) {
          if (this.personForm.companyId) {
            handleUpdate(this.personForm).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.addOrEditVisible = false;
              this.getData();
            });
          } else {
            handleSave(this.personForm).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.addOrEditVisible = false;
              this.getData();
            });
          }
        }
      });
    },

    //删除
    handleDelete(row, event) {
      this.clearBlur(event);
      this.$confirm("确定要删除该配送企业?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        handleDelete([row.companyId]).then((res) => {
          if (res.code == 200) {
            this.getData();
            this.$message({
              type: "success",
              message: "删除成功!",
            });
          }
        });
      });
    },

    // handlePrint(row) {
    // this.printArr.push(row)
    // this.showPrint = true
    // window.print()
    // },

    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "禁用";
      this.$confirm("确认要停用该配送企业吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return handleUpdate({ companyId: row.companyId, status: row.status });
        })
        .then(() => {
          this.$modal.msgSuccess(text + "成功");
        })
        .catch(function () {
          row.status = row.status === "0" ? "1" : "0";
        });
    },
    //查询
    queryData() {
      this.listQuery.pageNum = 1;
      this.listQuery.param = strTrim(this.listQuery.param);
      this.getData();
    },
    //清空
    resetEmpty() {
      this.dateRange = [];
      this.listQuery = this.$options.data().listQuery;
      this.getData();
    },

    //清楚选中按钮效果
    clearBlur(event) {
      event.target.blur();
      if (
        event.target.nodeName == "SPAN" ||
        event.target.nodeName == "BUTTON" ||
        event.target.nodeName == "I" ||
        event.target.nodeName == "use" ||
        event.target.nodeName == "svg"
      ) {
        event.target.parentNode.blur();
      }
    },

    dealDelChange() {
      // 删除成功后写下以下判断
      let totalPage = Math.ceil((this.total - 1) / this.listQuery.pageSize);
      let currentPage =
        this.listQuery.pageNum > totalPage ? totalPage : this.listQuery.pageNum;
      this.listQuery.pageNum = currentPage < 1 ? 1 : currentPage;
    },
    resetConfirm() {
      let obj = {
        companyId: this.detailInfo.companyId,
        password: this.tokenForm.password,
      };
      this.$refs["tokenForm"].validate((valid) => {
        if (valid) {
          checkToken(obj).then((res) => {
            if (res.code == 200) {
              this.tokenText = res.data.token;
              this.cancelCheckPWD();
              this.addTokenVisible = true;
              // reflashToken({ id: this.checkId }).then(res => {
              //   console.log(res, "resssssss");
              //   this.tokenText = res.data.token;
              //   this.checkTokenVisible = false;
              //   this.addTokenVisible = true;
              // });
            }
          });
        }
      });
    },
    cancelCheckPWD() {
      this.codeVisible = false;
      this.tokenForm = {
        password: "",
      };
    },
    resetToken() {
      reflashToken({ companyId: this.detailInfo.companyId }).then((res) => {
        console.log(res, "resssssss");
        this.tokenText = res.data.token;
        this.cancelCheckPWD();
        this.addTokenVisible = true;
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.container {
  margin-left: 10px;
  width: calc(100% - 20px);
}

.overview-header-title {
  border-left: 4px solid #2c9ef7;
  padding-left: 6px;
  line-height: 28px;
  font-size: 14px;
}

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

<style>
.el-message-box__status {
  color: #fe7e52;
}

.el-textarea__inner {
  font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB,
    Microsoft YaHei, Arial, sans-serif;
}
</style>
