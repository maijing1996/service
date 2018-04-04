// pages/index/publishGoods/publishGoods.js
import { serviceUrl,get, put, post, del } from '../api/api.js'
var uploadimg = require('../utils/uploadImages.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    uid:'',
    imagesList: [],
    tempFiles:[],
    pictureNumber: 3,
    flag: false,
    typeList: ['闲置数码', '家具日用', '图书音像', '鞋服配饰', '美妆洗护', '文体户外','办公用品','其他'],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    wx.getStorage({
      key: 'uid',
      success: function (res) {
        that.setData({
          uid: res.data
        })
        console.log(res.data)
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
  
  chooseImage: function () {
    var that = this
    wx.chooseImage({
      count: that.data.pictureNumber, // 默认9
      sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        // console.log(res)
        var tempFilePaths = res.tempFilePaths
        var list = that.data.imagesList.concat(tempFilePaths)
        // console.log(list);
        that.setData({
          imagesList: that.data.imagesList.concat(tempFilePaths),
          pictureNumber: 3 - list.length,
          tempFiles: res.tempFiles
        })
      },
      complete: function(){
        if (that.data.pictureNumber < 1) {
          that.setData({
            flag: true
          })
        }
      }
    })
  },
  previewImage: function (e) {
    var current = e.currentTarget.dataset.src;
    // console.log(e)
    // var index = e.currentTarget.dataset.index;
    // console.log(current)
    wx.previewImage({
      current: current, // 当前显示图片的http链接  
      urls: this.data.imagesList // 需要预览的图片http链接列表  
    })
  },
  bindPickerChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      index: e.detail.value
    })
  },
  formSubmit: function (e) {
    var that = this;
    var formData = e.detail.value;
    formData.type = this.data.typeList[formData.type];
    // console.log(formData)
    // wx.request({
    //   url: 'http://test.com:8080/test/socket.php?msg=2',
    //   data: formData,
    //   header: {
    //     'Content-Type': 'application/json'
    //   },
    //   success: function (res) {
    //     console.log(res.data)
    //     that.modalTap();
    //   }
    // })
    post('/goods/publish/'+ this.data.uid, formData).then((res) => {
      wx.showLoading({
        mask: true
      })
      if (res.statusCode == '200'){
        console.log("12")
        console.log(res.data)
        console.log("34")
        let result = uploadimg.uploadimg({
          url: serviceUrl + '/goods/images/upload',//这里是你图片上传的接口
          path: that.data.imagesList,//这里是选取的图片的地址数组
          goods: res.data
        });
        wx.hideLoading()
        if(result == true) {
          wx.showToast({
            title: '成功',
            icon: 'success',
            duration: 2000,
            mask: true,
            success: function () {
              that.setData({
                name: '',
                phone: '',
                bs: ''
              });  
            }
          })
        }
      } else {
        wx.hideLoading()
        console.log(res.data.message)
      }
    });

    

    // wx.uploadFile({
    //   url: 'http://192.168.1.117:8081/goods/publish', 
    //   filePath: that.data.imagesList,
    //   name: 'file',
    //   formData: {
    //     'goodsName': 'test'
    //   },
    //   success: function (res) {
    //     var data = res.data
    //     //do something
    //   }
    // })
  }

})