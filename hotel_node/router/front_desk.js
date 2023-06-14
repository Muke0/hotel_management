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
module.exports = router