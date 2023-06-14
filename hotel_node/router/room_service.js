const express = require('express')
const router = express.Router()

// 导入经理路由处理函数对应的模块
const room_service_handler = require('../router_handler/room_service')

//获取服务需求
router.get('/service_need', room_service_handler.get_service_need)

//修改服务需求
router.put('/service_need', room_service_handler.update_service_need)
module.exports = router