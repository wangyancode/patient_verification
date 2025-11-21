<template>
  <div class="app-container">
    <el-card style="overflow: auto">
      <div style="height: calc(100vh - 206px)">
        <div style="margin: 20px">当前版本：{{ filename }}</div>
        <div style="display: flex; margin: 20px">
          <div>
            <span>输入版本号：</span>
            <el-input
              v-model="remark"
              placeholder="请输入版本号"
              style="width: 350px"
            ></el-input>
          </div>
          <div style="margin-left: 10px">
            <el-upload
              class="uploadBtn"
              action="#"
              :http-request="requestUpload"
              accept=".apk"
              :show-file-list="false"
              :before-upload="upload"
            >
              <el-button type="primary" :loading="loadings"
                ><i class="el-icon-upload" style="margin-right: 6px"></i
                >点击上传</el-button
              >
            </el-upload>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import { getRersion, upLoadData, saveRersion } from "@/api/rersionUpDate.js";
export default {
  name: "versionUpDate",
  data() {
    return {
      filename: "",
      remark: "",
      loadings: false,
    };
  },
  created() {},
  mounted() {
    this.getRersionData();
  },
  methods: {
    requestUpload() {},
    compareVersion(v1, v2) {
      v1 = v1.split(".");
      v2 = v2.split(".");
      let len = Math.max(v1.length, v2.length);
      while (v1.length < len) {
        v1.push("0");
      }
      while (v2.length < len) {
        v2.push("0");
      }

      for (let i = 0; i < len; i++) {
        let num1 = parseInt(v1[i]),
          num2 = parseInt(v2[i]);

        if (num1 > num2) {
          return 1;
        } else if (num1 < num2) {
          return -1;
        }
      }

      return 0;
    },
    getRersionData() {
      getRersion().then((res) => {
        if (res.code == 200) {
          this.filename = res.data.versionsNo;
        }
      });
    },
    upload(file) {
      if (this.remark) {
        if (file) {
          let num = this.compareVersion(this.remark, this.filename);
          if (num == 1) {
            this.uploadFile(file);
          } else {
            this.$message.error("新增版本号需大于当前版本号！");
          }
        }
      } else {
        this.$message.error("版本号不能为空");
      }
    },
    uploadFile(file) {
      this.loadings = true;
      let formData = new FormData();
      formData.append("file", file);
      upLoadData(formData).then((res) => {
        if (res.code == 200) {
          this.saveUpData(res.data.documentId);
        }
      });
    },
    saveUpData(id) {
      let obj = {
        documentId: id,
        versionsNo: this.remark,
      };
      saveRersion(obj).then((res) => {
        if (res.code == 200) {
          this.$message({
            message: "上传成功",
            type: "success",
          });
          this.getRersionData();
          this.loadings = false;
        }
      });
    },
  },
};
</script>

<style scoped lang="scss">
.overview-header-title {
  border-left: 4px solid #2c9ef7;
  padding-left: 6px;
  line-height: 28px;
  font-size: 14px;
}

.searchTitle {
  width: 120px;
  height: 20px;
  line-height: 20px;
  margin: 12px 12px;
  text-align: center;
  border-left: 3px solid #2c9ef7;
}

.searchTitle1 {
  width: 50px;
  height: 20px;
  line-height: 20px;
  margin: 12px 12px;
  text-align: center;
  border-left: 3px solid #2c9ef7;
}

.searchList {
  display: flex;
  justify-content: space-between;
}

.searchList_btn {
  display: flex;
  align-items: center;
}

::v-deep .el-table th.el-table__cell.is-leaf {
  border-bottom: 0px solid #dfe6ec !important;
  background: rgb(239, 243, 247);
}

::v-deep .el-button--danger.is-plain:hover {
  color: #fff !important;
  background: #ff4949 !important;
}

::v-deep .el-button--primary.is-plain:hover {
  color: #fff !important;
}

// ::v-deep .el-button--primary.is-plain {
//   color: #fff !important;
//   background: #409EFF !important;
//   border-color: #fff !important;
// }
::v-deep .el-button--danger.is-plain {
  color: #ff4949 !important;
  background: #ffeded !important;
  border-color: #ff4949 !important;
}

::v-deep .el-table td.el-table__cell {
  border-bottom: 0px solid #dfe6ec !important;
}

::v-deep .el-table::before {
  height: 0 !important;
}
</style>
