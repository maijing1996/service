// pages/index/message/message.js
import { serviceUrl, get, put, post, del } from '../api/api.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    uid:'',
    winWidth: 0,
    winHeight: 0,
    // tab切换  
    currentTab: 0,
    goodsComments:[],
    goodsAllComments:[],
    content:'',
    goodsId: '',
    replyId: '',
    commentId: '',
    releaseName: '',
    releaseFocus: false
  
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    /** 
   * 获取系统信息 
   */
    var that = this;
    wx.getSystemInfo({

      success: function (res) {
        that.setData({
          winWidth: res.windowWidth,
          winHeight: res.windowHeight
        });
      }

    }); 
    wx.getStorage({
      key: 'uid',
      success: function (res) {
        that.setData({
          uid: res.data
        })
        that.getUnreadComment()
        that.getAllComments()
      }
    })
  
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
    * 滑动切换tab 
    */
  bindChange: function (e) {

    var that = this;
    that.setData({ currentTab: e.detail.current });

  },
  /** 
   * 点击tab切换 
   */
  swichNav: function (e) {

    var that = this;

    if (this.data.currentTab === e.target.dataset.current) {
      return false;
    } else {
      that.setData({
        currentTab: e.target.dataset.current
      })
    }
  },
  /**
   * 获取未读评论
   */
  getUnreadComment: function() {
    var that = this
    get('/comments/unread/' + this.data.uid, null).then((res) => {
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
   * 获取所有评论
   */
  getAllComments: function() {
    var that = this
    get('/comments/user/' + this.data.uid, null).then((res) => {
      if (res.statusCode == '200') {
        that.setData({
          goodsAllComments: res.data
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


  replyCommentHandler: function (event) {
    // console.log(event.detail.value.content);
    // console.log(event.currentTarget.dataset);
    var that = this;
    console.log(event.detail.value)
    if (event.detail.value.content == "") {
      wx.showToast({
        title: "评论不能为空",
        icon: "none",
        // image: '/pages/images/warning.png',
        duration: 2000
      })
      return false;
    }
    var params = {
      content: event.detail.value.content,
      goodsId: this.data.goodsId,
      replyCommentId: this.data.commentId
    }
    post('/comment/add/' + this.data.uid + '/reply/' + this.data.replyId, params).then((res) => {
      if (res.statusCode == '200') {
        wx.showToast({
          title: '评论成功',
          icon: 'success',
          duration: 2000,
          mask: true,
          success: function () {
            that.setData({
              content: ''
            });
            // that.getGoodsComment(that.data.goodsId);
          }
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
* 点击回复
*/
  bindReply: function (e) {
    console.log(e.currentTarget.dataset)
    this.setData({
      releaseFocus: true,
      replyId: e.currentTarget.dataset.replyid,
      commentId: e.currentTarget.dataset.commentid,
      releaseName: e.currentTarget.dataset.nickname,
      goodsId: e.currentTarget.dataset.goodsid
    })
  },
  backGoods: function(e) {
    wx.navigateTo({
      url: '../goodsDetails/goodsDetails?id=' + e.currentTarget.dataset.id
    })
  }
})