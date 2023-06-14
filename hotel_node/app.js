// 导入 express
const express = require('express')
    // 创建服务器的实例对象
const app = express()
const joi = require('@hapi/joi')
const moment = require('moment')
const os = require('os');
const serverlink = { port: '9998', ip: '10.0.4.8' }; //服务器连接配置
const locallink = { port: '9000', ip: '127.0.0.1' }; //本地连接配置
// 导入并配置 cors 中间件
const cors = require('cors')
app.use(cors())
app.use(express.json());
// 配置解析表单数据的中间件，注意：这个中间件，只能解析 application/x-www-form-urlencoded 格式的表单数据
app.use(express.urlencoded({ extended: false }))
    // 一定要在路由之前，封装 res.cc 函数
app.use((req, res, next) => {
    // status 默认值为 1，表示失败的情况
    // err 的值，可能是一个错误对象，也可能是一个错误的描述字符串
    res.cc = function(err, status = 1) {
        res.send({
            status,
            message: err instanceof Error ? err.message : err,
        })
    }
    next()
})


// 一定要在路由之前配置解析 Token 的中间件
const expressJWT = require('express-jwt')
const config = require('./config')

app.use(expressJWT({ secret: config.jwtSecretKey })
    .unless({ path: ['/user/login', '/user/register'] }))

// 导入并使用用户路由模块
const userRouter = require('./router/user')
app.use('/user', userRouter)

const roomServiceRouter = require('./router/room_service.js')
app.use('/room_service', roomServiceRouter)

const frontDeskRouter = require('./router/front_desk.js')
app.use('/front_desk', frontDeskRouter)

const managerRouter = require('./router/manager')
app.use('/manager', managerRouter)
    // 定义错误级别的中间件
app.use((err, req, res, next) => {
    // 验证失败导致的错误
    if (err instanceof joi.ValidationError) return res.cc(err)
        // 身份认证失败后的错误
    if (err.name === 'UnauthorizedError') return res.cc('身份认证失败！')
        // 未知的错误
    res.cc(err)
})

// 启动服务器
if (os.networkInterfaces().eth0 == null) { //验证是否是否存在linux网卡
    var Server = app.listen(locallink.port, locallink.ip, () => {
        console.log('api server running at http://' + locallink.ip + ':' + locallink.port);
    })
} else { //如果在服务器端
    var Server = app.listen(serverlink.port, serverlink.ip, () => {
        console.log('api server running at http://' + serverlink.ip + ':' + serverlink.port);
    })
}