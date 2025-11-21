<template>
  <div>

    <div class="header-message" v-if="MessageStatus == 1">
      <el-popover placement="bottom" width="400" trigger="click" popper-class="popperOptions" v-model="popshow">
        <div class="message-pop-up">
          <div class="message-pop-header">
            <div class="message-pop-header-title">
              <span>消息提醒</span>
            </div>
          </div>
          <el-scrollbar>
            <div class="message-pop-body" v-if="messageNoteadLists.length > 0">
              <div v-for="(item, index) in messageNoteadLists" :key="index" class="message-pop-info"
                @click="handelMessageDetail(item.id)">
                <div class="message-pop-title">
                  <div class="message-pop-signs-noread"></div>
                  <div class="message-pop-title-body">
                    {{ item.content }}
                  </div>
                </div>
                <div class="message-pop-time">{{ item.sendDatetime }}</div>
              </div>
            </div>
            <div class="message-pop-body" v-else>
              <div class="message-body-empty">
                <div class="message-body-empty-item">
                  <img src="@/assets/images/empty.png" class="table-empty-image" style="width: 80px; height: 80px" />
                  <p>暂无消息</p>
                </div>
              </div>
            </div>
          </el-scrollbar>
          <div class="message-pop-footer">
            <el-button type="text" class="message-button-item" @click="makeAllRead"
              v-hasPermi="['workbench:IgnoreAll']">忽略全部</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button type="text" class="message-button-item" @click="readAllMessage">查看全部</el-button>
          </div>
        </div>
        <el-badge :value="messageNoteadLists.length" :max="99" style="line-height:9px;" slot="reference"
          :hidden="messageNoteadLists.length == 0">
          <svg-icon icon-class="headermessage" class-name="message-icon" />
        </el-badge>
      </el-popover>
    </div>
    <!-- 消息详情弹框-->
    <el-dialog title="消息详情" :visible.sync="dialogMessageDetailVisible" width="24%" :close-on-click-modal="false"
      @close="closeMessageDetail" :modal-append-to-body='false'>
      <div class="message-detail">
        <div class="message-detail-content">
          {{ messageDetail.content }}
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import { getNoReadMessage, setAllSignIsRead, setSignIsRead } from '@/api/message'
import { mapGetters } from 'vuex'
// import Stomp from 'stompjs'
// import { MQTT_SERVICE, MQTT_USERNAME, MQTT_PASSWORD, MQTT_host, MQTT_topic } from '@/utils/mqtt'

export default {
  name: 'Message',
  computed: {
    ...mapGetters(['topbarRouters', 'messageNoteadLists', 'messageNoteadNum', 'userInfo']),
  },
  data() {
    return {
      client: Stomp.client(MQTT_SERVICE),
      dialogMessageDetailVisible: false,
      messageDetail: [],
      popshow: false,
      lineSubscribe: '',
      MessageStatus: 0,
    }
  },
  created() {
    // console.log('topbarRouters', this.topbarRouters);
    this.topbarRouters.forEach((item, index) => {
      if (item.children && item.children.length > 0) {
        item.children.forEach(element => {
          if (element.path == 'newValue') {
            this.MessageStatus = 1
          }
        });
      }
    });
    this.getAllReadMessageLists()
    // console.log('messageNoteadLists99', this.messageNoteadLists);
  },

  mounted() {
    if (this.MessageStatus == 1) {
      this.connect()// stomp连接mq
    }
  },

  methods: {

    //MQTT消息连接
    onConnected: function (frame) {
      // 订阅频道
      let topic = MQTT_topic + '-' + this.userInfo.account
      this.lineSubscribe = this.client.subscribe(topic, this.responseCallback, this.onFailed)
    },
    onFailed: function (frame) {
      // console.log('MQ Failed:' + frame)
    },
    responseCallback: function (frame) {
      // 接收消息处理
      //  console.log('MQ msg=>', frame.body)
      let frameBody = JSON.parse(frame.body)
      // console.log('frameBody', frameBody);
      if (frameBody && frameBody.code && frameBody.code == 'disconnectCode') {
        //判断客户端和接受消息同时存在token且token不同时 取消订阅
        this.client.disconnect()
        // if (this.lineSubscribe) {
        //   this.lineSubscribe.unsubscribe()
        // }
      }
      // console.log('frameBody',frameBody);
      if (frameBody && frameBody.length > 0) {

        let messageData = []
        frameBody.forEach((item, index) => {
          messageData.push(item)
        });
        // console.log('messageData',messageData);
        let messageNotRead = []
        if (messageData && messageData.length > 0) {
          messageNotRead = JSON.parse(JSON.stringify(this.messageNoteadLists))
          messageNotRead.unshift(...messageData)
          messageNotRead = this.unique(messageNotRead)
        }
        this.$store.dispatch("MessageNoteadLists", JSON.stringify(messageNotRead)).then(() => { }).catch(() => { }); //动态存储未读消息

        this.notifyMessage()

        // var link = document.querySelector('link[rel*="icon"]')
        // let linkStr = '@/assets/favicon/favicon.png'
        // if (parseInt(messageNotRead.length) && parseInt(messageNotRead.length) > 0 && parseInt(messageNotRead.length) < 10) {
        //   linkStr = require('@/assets/favicon/favicon_' + messageNotRead.length + '.png')
        // } else if (parseInt(messageNotRead.length) > 9) {
        //   linkStr = require('@/assets/favicon/favicon_11.png')
        // }
        // link.href = linkStr

        //   //系统弹框
        // this.windowDiloag(messageData)
      }
    },


    windowDiloag(messageData) {
      if (window.Notification) {
        var popNotice = function () {
          messageData.forEach(element => {
            var notification = new Notification("消息提醒：", {
              body: element.content,
            });
          });
        };
        if (Notification.permission == "granted") {
          popNotice();
        } else if (Notification.permission != "denied") {
          Notification.requestPermission(function (permission) {
            popNotice();
          });
        }
      } else {
        console.log('浏览器不支持Notification');
        // alert('浏览器不支持Notification');
      }
    },

    notifyMessage() {
      const h = this.$createElement;
      this.$notify({
        title: '消息通知',
        message: h('i', { style: 'color: #2c9ef7' }, '新发现值域！'),
        duration: 2000
      });
    },
    unique(arr) { // 根据唯一标识id来对数组进行过滤
      const res = new Map();//定义常量 res,值为一个Map对象实例
      //返回arr数组过滤后的结果，结果为一个数组   过滤条件是，如果res中没有某个键，就设置这个键的值为1
      return arr.filter((arr) => !res.has(arr.id) && res.set(arr.id, 1))
    },

    connect() {
      // var clientid = v4()
      // console.log('clientid', clientid);
      // 初始化mqtt客户端，并连接mqtt服务
      const headers = {
        'login': MQTT_USERNAME,
        'password': MQTT_PASSWORD,
        // 'client-id': clientid
      }
      this.client.connect(MQTT_USERNAME, MQTT_PASSWORD, this.onConnected, this.onFailed, MQTT_host)
    },


    //忽略全部未读消息
    makeAllRead() {
      setAllSignIsRead([]).then(res => {
        this.getAllReadMessageLists()
        this.$message.success(res.msg)
        return
      })
    },
    //记录最近访问记录
    handelMessageDetail(id) {
      this.popshow = false
      setSignIsRead({ messageId: id }).then(res => {
        this.getAllReadMessageLists()
      })
      var url = '/systematic/newValue'
      let visitedViews = this.$store.state.tagsView.visitedViews
      console.log('visitedViews', visitedViews);
      if (visitedViews.length > 0) {
        visitedViews.forEach(element => {
          if (element.fullPath === url) {
            this.$store.dispatch('tagsView/delCachedView', element).then(() => { })
          }
        });
      }
      this.$router.replace({
        path: '/redirect' + url
      })
      // this.$router.push({ path: '/systematic/newValue' })
    },

    //查看全部消息跳转到消息列表
    readAllMessage() {
      this.popshow = false
      var url = '/systematic/newValue'
      let visitedViews = this.$store.state.tagsView.visitedViews
      console.log('visitedViews', visitedViews);
      if (visitedViews.length > 0) {
        visitedViews.forEach(element => {
          if (element.fullPath === url) {
            this.$store.dispatch('tagsView/delCachedView', element).then(() => { })
          }
        });
      }
      this.$router.replace({
        path: '/redirect' + url
      })
      // this.$router.push({ path: '/systematic/newValue' })
    },

    //关闭公告详情
    closeMessageDetail() {
      this.getAllReadMessageLists()
    },

    //获取全部未读消息列表
    getAllReadMessageLists() {
      getNoReadMessage({}).then(res => {
        if (res.code == 200) {
          if (res.data) {
            this.$store.dispatch("MessageNoteadLists", JSON.stringify(res.data)).then(() => { }).catch(() => { }); //动态存储未读消息
          }
        }
      }).catch((r) => {

      })
    },

  }
}
</script>

<style lang="scss" scoped>
.header-message {
  font-size: 0 !important;
  margin-right: 20px;

  .message-icon {
    cursor: pointer;
    font-size: 22px;
    vertical-align: middle;
  }

}

.message-pop-up {

  .message-pop-header {
    background: #f3f4f8;
    font-size: 16px;
    padding: 15px;
    min-height: 40px;
  }

  .operation-button-item {
    background: transparent;
    border: none;
    outline: none;
    cursor: pointer;
    font-size: 16px;
  }

  .message-pop-body {
    max-height: 176px;
    padding: 10px 15px;

  }


  .message-pop-footer {
    height: 40px;
    background: rgb(243, 244, 248);
    font-size: 16px;
    padding: 15px;
    display: flex;
    align-items: center;
    justify-content: space-around;
    margin-top: 10px;
  }

  .message-pop-info {
    padding: 10px 6px;
    border-bottom: 1px solid #f2f2f2;
    font-size: 14px;
    cursor: pointer;

    .message-pop-title {
      color: #808080;
      display: flex;
      align-items: center;


      .message-pop-title-body {
        overflow: hidden;
        /*文本不会换行*/
        white-space: nowrap;
        /*当文本溢出包含元素时，以省略号表示超出的文本*/
        text-overflow: ellipsis;
        width: 98%;
      }

      .message-pop-signs-noread {
        width: 6px;
        height: 6px;
        border-radius: 50%;
        background: #E34D59;
        margin-right: 10px;
      }
    }

    .message-pop-time {
      margin-left: 16px;
      font-size: 13px;
      color: #B3B3B3;
    }

  }

  .message-body-empty {
    display: flex;
    align-items: center;
    justify-content: center;

    .message-body-empty-item {
      text-align: center;

      p {
        font-size: 14px;
        color: #808080;
        margin-top: 0px;
      }
    }
  }
}
</style>
<style>
.popperOptions {
  padding: 0px;
}
</style>
