const express = require('express')
const router = express.Router()

// 导入经理路由处理函数对应的模块
const client_handler = require('../router_handler/client')

// 上传服务需求
router.post('/demand', client_handler.upload_demand)

//获取服务需求
router.get('/demand', client_handler.get_demand)

//获取房间状态
router.get('/room_info', client_handler.get_room_info)

//发出预约请求
router.post('/appointment', client_handler.upload_appointment)

//获取账单
router.get('/bill', client_handler.get_bill)

//账单付款
router.put('/bill', client_handler.update_bill)

//发出用户请求
router.post('/service_evaluation', client_handler.upload_evaluation)




// //获取客户服务需求
// router.get('/room_info', client_handler.get_room_info)

// //删除教职工信息
// router.delete('/staff', client_handler.delete_staff)

// //修改教职工信息
// router.put('/staff', client_handler.update_staff)
module.exports = router