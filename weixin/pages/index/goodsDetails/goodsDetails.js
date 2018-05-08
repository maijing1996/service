// pages/index/goodsDetails/goodsDetails.js
import { serviceUrl, get, put, post, del } from '../api/api.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    goodsDetails: '',
    goodsComments: ''

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // console.log(options.id)
    this.getGoodsDetails(options.id)
    this.getGoodsComment(options.id)
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

  getGoodsDetails: function (id) {
    var that = this
    get('/goods/' + id, null).then((res) => {
      if (res.statusCode == '200') {
        console.log(res.data)

        that.setData({
          goodsDetails: res.data
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
  },

  getGoodsComment : function(id) {
    var that = this
    get('/comments/' + id, null).then((res) => {
      if (res.statusCode == '200') {
        that.setData({
          goodsComments: res.data
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


  },
  /**
   * 操作
   */
  moreOperation: function () {
    var that = this;
    wx.showActionSheet({
      itemList: ['生成二维码', '修改', '删除'],
      success: function (res) {
        if (res.tapIndex == 0) {
          console.log("res.tapIndex")
          wx.navigateTo({
            url: '../qr/qr?id=' + that.data.goodsDetails.id + '&goodsName=' + that.data.goodsDetails.goodsName 
          })
        } else if (res.tapIndex == 1) {

        } else if (res.tapIndex == 2) {

        }
        

      },
      fail: function (res) {
        console.log(res.errMsg)
      }
    })

  }
})