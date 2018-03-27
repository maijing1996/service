//index.js
//获取应用实例
const app = getApp()
import { get } from './api/api.js'
import { createQrCodeImg} from './qrcode/wxqrcode.js'
Page({
  data: {
    motto: 'Hello World',
    test:'222',
    qrcode:'',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: './publishGoods/publishGoods'
    })
  },
  onLoad: function () {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
      console.log(app.globalData.userInfo);
    } else if (this.data.canIUse){
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },
  getUserInfo: function(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },
  changeName: function(e){
    var that = this//不要漏了这句，很重要
    // get('/goods', null).then((response) => {
    //   if (response.statusCode == '200'){
    //     that.setData({
    //       test: response.data.totalElements
    //     });
    //   } else {
    //     that.setData({
    //       test: response.data.message
    //     });
    //   }
        
    // });
    // 只允许从相机扫码
    wx.scanCode({
      // onlyFromCamera: true,
      success: (res) => {
        console.log("12121")
        console.log(res)
        that.setData({
          test: res.result
        });
      }
    })
  },
  createQrCodeImg : function(e) {
    var that = this
    let img = createQrCodeImg('1234545', { 'size': 300 })
    that.setData({
      qrcode: img
    });

  }
  
})
