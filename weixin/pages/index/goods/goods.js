// pages/index/goods/goods.js
import { serviceUrl, get, put, post, del } from '../api/api.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    goodsList: [],
    pageNum: '',
    pageSize: '',
    goodsName:'',
    type:'',
    imagesList: ["http://120.79.143.90:8088/images/0e1cd7ae-335a-43f0-8d3e-4b3c3117c723.png", "https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ1h2oyGvUU6hibPVMvlrd7J9Y3fXElWnXGicz3TwKzeEDX8o0AicVYsJedPOtmaY01oibZibtg5erRZVw/0",    "https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ1h2oyGvUU6hibPVMvlrd7J9Y3fXElWnXGicz3TwKzeEDX8o0AicVYsJedPOtmaY01oibZibtg5erRZVw/0"],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.freshGoods();
  
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
    this.freshGoods();
    wx.stopPullDownRefresh()
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
   * 刷新商品列表
   */
  freshGoods: function (params) {
    var that = this
    if (params == undefined) {
      var params = {
        goodsName: this.data.goodsName,
        type: this.data.type,
        pageNum: 0
        //  pageSize: this.data.pageSize   
      }
    }  
    get('/goods', params).then((res) => {
      if (res.statusCode == '200') {
        console.log(res.data)
        that.setData({
          goodsList : res.data.content,
          pageNum : res.data.number,
          pageSize : res.data.size
        })
        
      } else {
        console.log(res.data.message)
      }
    });
  },

  serachGoods: function (e) {
    this.setData({
       goodsName: e.detail.value
    })
    this.freshGoods();
  },
  scanQr: function() {
    wx.scanCode({
      // onlyFromCamera: true,
      success: (res) => {
        console.log(res.result)
      }
    })
  }
})