// pages/index/register/register.js
import WxValidate from '../utils/WxValidate.js'
import { serviceUrl, get, put, post, del } from '../api/api.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userForm: {
      username: '',
      password: '',
      rePassword: '',
      qq: '',
      mobile: ''
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this

    this.WxValidate = new WxValidate(
      {
        username: {
          required: true
        },
        password: {
          required: true
          // password: true,
          // maxlength: 200,
        }
      }
      , {
        username: {
          required: '请填写用户名'
        },
        password: {
          required: '请填写密码'
        }
      }
    )

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  /**
   * 提交注册表单
   */
  formSubmit: function (e) {
    var that = this;
    var formData = {
      username: e.detail.value.username,
      password: e.detail.value.password,
    }
    //
    if (!this.WxValidate.checkForm(e)) {
      const error = this.WxValidate.errorList[0]
      // `${error.param} : ${error.msg} `
      wx.showToast({
        title: error.msg,
        icon: "none",
        // image: '/pages/images/warning.png',
        duration: 2000
      })
      return false
    }
    // formData.type = this.data.typeList[formData.type];
    post('/user/auth', formData).then((res) => {
      wx.showLoading({
        mask: true
      })
      if (res.statusCode == '200') {
        wx.hideLoading()
        wx.showToast({
          title: '登陆成功',
          icon: 'success',
          duration: 2000,
          mask: true,
          success: function () {
            that.setData({
              userForm: {
                username: '',
                password: ''
              }
            });
            /**跳转 */
            wx.switchTab({
              url: '/pages/index/index'
            })
          }
        })
        // }
      } else {
        wx.hideLoading()
        console.log(res.data.message)
        wx.showToast({
          title: res.data.message,
          icon: "none",
          // image: '/pages/images/warning.png',
          duration: 2000
        })
      }
    });
  }
})