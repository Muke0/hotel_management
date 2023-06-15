const express = require('express')
const router = express.Router()

// 导入经理路由处理函数对应的模块
const front_desk_handler = require('../router_handler/front_desk')

// 上传会员信息
router.post('/vip', front_desk_handler.upload_vip)

//获取会员信息
router.get('/vip', front_desk_handler.get_vip)

//删除会员信息
router.delete('/vip', front_desk_handler.delete_vip)

//修改会员信息
router.put('/vip', front_desk_handler.update_vip)

//获取房间信息
router.get('/room_info', front_desk_handler.get_room_info)

//修改房间信息
router.put('/room_info', front_desk_handler.update_room_info)

//获取预约
router.get('/appointment', front_desk_handler.get_appointment)

//修改预约
router.put('/appointment', front_desk_handler.update_appointment)

// 上传用户信息
router.post('/user', front_desk_handler.upload_user)

//获取用户信息
router.get('/user', front_desk_handler.get_user)

//删除用户信息
router.delete('/user', front_desk_handler.delete_user)

//修改用户信息
router.put('/user', front_desk_handler.update_user)
module.exports = router