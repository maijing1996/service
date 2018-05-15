//app.js
import { get } from './pages/index/api/api.js'
import wxValidate from './pages/index/utils/WxValidate.js'
App({
  onLaunch: function () {
    var count = this
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        var that = this;
        this.globalData.code = res.code;

        // 获取用户信息
        wx.getSetting({
          success: res => {
            if (res.authSetting['scope.userInfo']) {
              // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
              wx.getUserInfo({
                success: res => {
                  console.log(res)
                  
                  // 可以将 res 发送给后台解码出 unionId
                  that.globalData.userInfo = res.userInfo
                  let params = {
                    code: that.globalData.code,
                    nickName: that.globalData.userInfo.nickName,
                    avatarUrl: that.globalData.userInfo.avatarUrl,
                    gender: that.globalData.userInfo.gender
                  }
                  get('/weChat/getUserInfo', params).then((res) => {
                    if (res.statusCode == '200') {
                      // console.log(res.data)
                      wx.setStorage({
                        key: "uid",
                        data: res.data.id
                      })
                      // console.log("橘色" +res.data.role )
                      that.globalData.role = res.data.role 
                      if (res.data.state == 1) {
                        wx.redirectTo({
                          url: './error/error'
                        })
                      }
                      if (res.data.username == "" || res.data.username == null) {
                        wx.redirectTo({
                          url: './register/register?id=' + res.data.id,
                        })
                      }
                      count.getMessages(res.data.id)
                    } else {
                      wx.showToast({
                        title: res.data.message,
                        icon: "none",
                        // image: '/pages/images/warning.png',
                        duration: 2000
                      })
                    }
                  });
                  // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
                  // 所以此处加入 callback 以防止这种情况
                  if (this.userInfoReadyCallback) {
                    this.userInfoReadyCallback(res)
                  }
                }
              })
            }
          }
        })
      }
    })
   
  },
  getMessages: function(id) {
    var that = this
    setTimeout(function(){
      // console.log("1")
      get('/comments/unread/count/' + id, null).then((res) => {
        if (res.statusCode == '200') {
          wx.setTabBarBadge({
            index: 3,
            text:  res.data.toString()
          })
        } else {
          wx.showToast({
            title: res.data.message,
            icon: "none",
            // image: '/pages/images/warning.png',
            duration: 2000
          })
        }
      });
      that.getMessages(id);
    },2000)
  },
  globalData: {
    userInfo: null,
    code: '',
    role:'',
    state:''
  },
  wxValidate: (rules, messages) => new wxValidate(rules, messages)
})