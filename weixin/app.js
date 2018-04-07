//app.js
import { get } from './pages/index/api/api.js'
import wxValidate from './pages/index/utils/WxValidate.js'
App({
  onLaunch: function () {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        this.globalData.code = res.code;
      }
    })
    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              console.log(res)
              var that = this; 
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo
              let params = {
                code: this.globalData.code,
                nickName: this.globalData.userInfo.nickName,
                avatarUrl: this.globalData.userInfo.avatarUrl,
                gender: this.globalData.userInfo.gender
              } 
              get('/weChat/getUserInfo', params).then((res) => {
                if (res.statusCode == '200') {
                  console.log(res.data)
                  wx.setStorage({
                    key: "uid",
                    data: res.data.id
                  })
                  if (res.data.username == null) {
                    wx.navigateTo({
                      url: '../register/register?id=' + res.data.id
                    })

                  }
                  
                } else {
                  console.log(res.data.message)
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
  },
  globalData: {
    userInfo: null,
    code: ''
  },
  wxValidate: (rules, messages) => new wxValidate(rules, messages)
})