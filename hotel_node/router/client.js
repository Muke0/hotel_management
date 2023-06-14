const express = require('express')
const router = express.Router()

// 导入经理路由处理函数对应的模块
const client_handler = require('../router_handler/client')

// 上传服务评价
router.post('/service_evaluation', client_handler.upload_evaluation)

//获取房间状态
router.get('/room_info', client_handler.get_room_info)

//获取客户服务需求
router.get('/room_info', client_handler.get_room_info)

//删除教职工信息
router.delete('/staff', client_handler.delete_staff)

//修改教职工信息
router.put('/staff', client_handler.update_staff)
module.exports = router