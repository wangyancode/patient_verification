<template>
  <div class="app-container">
    <el-card>
      <div class="jcmh">
        <el-form ref="IPForm" :model="IPFormData" label-width="150px" :rules="IPrules">
          <el-form-item label="系统登录初始密码" prop="initPassword">
            <el-input
              show-password
              v-model="IPFormData.initPassword"
              placeholder="请输入系统登录初始密码"
              style="width:300px"
            ></el-input>
          </el-form-item>
          <el-form-item label="首次登录修改密码" prop="initUpdatePasswordFlag">
            <el-switch
              v-model="IPFormData.initUpdatePasswordFlag"
              active-value="1"
              inactive-value="0"
              @change="handleStatusChange"
            ></el-switch>
            <!-- <el-switch v-model="IPFormData.initUpdatePasswordFlag"></el-switch> -->
          </el-form-item>
          <el-form-item label="登录开启验证码" prop="authCodeFlag">
            <el-switch
              v-model="IPFormData.authCodeFlag"
              active-value="1"
              inactive-value="0"
              @change="handleStatusChange"
            ></el-switch>
            <!-- <el-switch v-model="IPFormData.authCodeFlag"></el-switch> -->
          </el-form-item>
          <el-form-item label="系统Logo配置" prop="logoUrl">
            <userAvatar @saveImgUrl="saveImgUrl" :logoUrl="IPFormData.logoUrl" :key="timer"/>
          </el-form-item>
          <!--              <el-form-item label="医院名称" prop="hospitalName">-->
          <!--                <el-input-->
          <!--                  v-model="IPFormData.hospitalName"-->
          <!--                  placeholder="请输入医院名称"-->
          <!--                  style="width:300px"-->
          <!--                ></el-input>-->
          <!--              </el-form-item>-->
<!--          <el-form-item label="机构名称设置" prop="hospitalName">-->
          <el-form-item label="机构名称" prop="hospitalName">
            <el-input
              v-model="IPFormData.hospitalName"
              placeholder="请输入机构名称"
              maxlength="32"
              style="width:300px"
            ></el-input>
          </el-form-item>
          <el-form-item label="机构Logo配置" prop="orgLogoUrl">
            <userAvatar @saveImgUrl="saveOrgImgUrl" :logoUrl="IPFormData.orgLogoUrl" :key="timer"/>
          </el-form-item>
        </el-form>
      </div>
      <div class="Integration">
        <el-button type="primary" @click="IPsave" v-prevent-click icon="el-icon-document">保 存
        </el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
  import userAvatar from "./userAvatar.vue";
  import {detailData, editDetailData} from "@/api/system/parameter";

  export default {
    components: {userAvatar},
    data() {
      var similarScoreMini = (rule, value, callback) => {
        if (!value) {
          return callback(new Error("请输入数值,可输入范围0 - 100!"));
        } else if (!Number.isInteger(value * 1)) {
          return callback(new Error("请输入数值,可输入范围0 - 100!"));
        } else if (value > 100 || value < 0) {
          return callback(new Error("请输入数值,可输入范围0 - 100!"));
        } else {
          callback();
        }
        // !Number.isInteger(value)
      };
      var maxCleanNumber = (rule, value, callback) => {
        if (!value) {
          return callback(new Error("请输入数值,可输入范围1 - 10000!"));
        } else if (!Number.isInteger(value * 1)) {
          return callback(new Error("请输入数值,可输入范围1 - 10000!"));
        } else if (value > 10000 || value < 1) {
          return callback(new Error("请输入数值,可输入范围1 - 10000!"));
        } else {
          callback();
        }
        // !Number.isInteger(value)
      };
      var tryCleanImportDataSize = (rule, value, callback) => {
        if (!value) {
          return callback(new Error("请输入数值,可输入范围1 - 10000!"));
        } else if (!Number.isInteger(value * 1)) {
          return callback(new Error("请输入数值,可输入范围1 - 10000!"));
        } else if (value > 10000 || value < 1) {
          return callback(new Error("请输入数值,可输入范围1 - 10000!"));
        } else {
          callback();
        }
        // !Number.isInteger(value)
      };
      var cleanThreadNumber = (rule, value, callback) => {
        if (!value) {
          return callback(new Error("可输入范围1 - 30!"));
        } else if (!Number.isInteger(value * 1)) {
          return callback(new Error("可输入范围1 - 30!"));
        } else if (value > 30 || value < 1) {
          return callback(new Error("可输入范围1 - 30!"));
        } else {
          callback();
        }
        // !Number.isInteger(value)
      };
      var cleanThreadIndexSize = (rule, value, callback) => {
        if (!value) {
          return callback(new Error("可输入范围1 - 1000!"));
        } else if (!Number.isInteger(value * 1)) {
          return callback(new Error("可输入范围1 - 1000!"));
        } else if (value > 1000 || value < 1) {
          return callback(new Error("可输入范围1 - 1000!"));
        } else {
          callback();
        }
        // !Number.isInteger(value)
      };
      return {
        loading: false,
        checkBoxData: [],
        activeName: "1",
        isShow: false,
        IPShow: false,
        IPFormData: {
          informIds: [],
          initPassword: "",
          initUpdatePasswordFlag: "",
          authCodeFlag: "",
          logoUrl: "",
          hospitalName: '',
          orgLogoUrl: '',
        },
        timer: "",
        patientFormData: {
          similarScoreMini: "",
          maxCleanNumber: "",
          tryCleanImportDataSize: "",
          cleanThreadNumber: "",
          cleanThreadIndexSize: ""
        },
        selectData: [],
        formData: {},
        IPrules: {
          informIds: [
            {
              required: true,
              message: "桌面通知不能为空，请选择",
              trigger: "change"
            }
          ],
          initPassword: [
            {
              pattern: /^[a-zA-Z0-9]{6,20}$/,
              required: true,
              message: "请设置初始密码:6-20位字母、数字!",
              trigger: ["blur", 'change']
            }
          ],

          initUpdatePasswordFlag: [
            {required: true, message: "首次登录修改密码", trigger: "change"}
          ],
          authCodeFlag: [
            {required: true, message: "登录开启验证码", trigger: "change"}
          ],
          logoUrl: [
            {required: true, message: "logo配置不能为空", trigger: "change"}
          ],
          orgLogoUrl: [
            {required: true, message: "机构logo配置不能为空", trigger: "change"}
          ]
        },
        PatientRules: {
          similarScoreMini: [{required: true, validator: similarScoreMini, trigger: "blur"}],
          maxCleanNumber: [{required: true, validator: maxCleanNumber, trigger: "blur"}],
          tryCleanImportDataSize: [
            {required: true, validator: tryCleanImportDataSize, trigger: "blur"}
          ],
          cleanThreadNumber: [{required: true, validator: cleanThreadNumber, trigger: "blur"}],
          cleanThreadIndexSize: [
            {required: true, validator: cleanThreadIndexSize, trigger: "blur"}
          ]
        },
        listData: []
      };
    },
    created() {
      this.getDetailData();
    },
    methods: {
      getDetailData() {
        detailData().then(res => {
          console.log(res);
          if (res.code == 200) {
            this.IPFormData = res.data.configServerInfoVO;
          }
        });
      },
      handleStatusChange(val) {
        console.log(val);
      },
      saveImgUrl(val) {
        this.timer = new Date().getTime();
        this.IPFormData.logoUrl = val.url;
      },
      saveOrgImgUrl(val) {
        this.timer = new Date().getTime();
        this.IPFormData.orgLogoUrl = val.url;
      },
      handleClick(val) {
        console.log(val.index);
        this.getDetailData(val.index);
      },
      IPsave() {
        let obj = {
          configServerInfoVO: this.IPFormData
        };
        console.log(obj);
        this.$refs["IPForm"].validate(valid => {
          if (valid) {
            editDetailData(obj).then(res => {
              if (res.code == 200) {
                this.getDetailData(0);
                this.$message({
                  message: "保存成功",
                  type: "success"
                });
              }
            });
          }
        });
      },

    }
  };
</script>

<style lang="scss" scoped>
  .jcmh {
    width: 100%;
    min-height: 78vh;
  }

  .Integration {
    text-align: center;
    position: relative;
    bottom: 10px;
  }

  .searchTitle {
    width: 80px;
    display: inline-block;
    height: 20px;
    line-height: 20px;
    margin: 12px 12px;
    text-align: center;
    border-left: 3px solid #2c9ef7;
  }

  .word {
    font-size: 12px;
    color: #989898;
  }

  ::v-deep .el-table td.el-table__cell {
    border: 0px solid !important;
  }

  .el-table::before {
    height: 0;
  }

  .color {
    color: #e83131;
    margin-left: 5px;
  }

  .color1 {
    color: #2c9ef7;
    margin-left: 5px;
  }
</style>
