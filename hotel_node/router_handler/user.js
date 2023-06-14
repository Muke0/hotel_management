// 导入数据库操作模块
const db = require('../db/index')
    // 导入 bcryptjs 这个包
const bcrypt = require('bcryptjs')
    // 导入生成 Token 的包
const jwt = require('jsonwebtoken')
    // 导入全局的配置文件
const config = require('../config')
const expressJWT = require('express-jwt')

// 注册新用户的处理函数
exports.register = (req, res) => {
    // 获取客户端提交到服务器的用户信息
    const userinfo = req.body
        // 对表单中的数据，进行合法性的校验
        // if (!userinfo.username || !userinfo.password) {
        //   return res.send({ status: 1, message: '用户名或密码不合法！' })
        // }

    // 定义 SQL 语句，查询用户名是否被占用
    const sqlStr = 'select * from account where user_name=?'
    db.query(sqlStr, userinfo.user_name, (err, results) => {
        // 执行 SQL 语句失败
        if (err) {
            // return res.send({ status: 1, message: err.message })
            return res.cc(err)
        }
        // 判断用户名是否被占用
        if (results.length > 0) {
            // return res.send({ status: 1, message: '用户名被占用，请更换其他用户名！' })
            return res.cc('用户名被占用，请更换其他用户名！')
        }
        // 调用 bcrypt.hashSync() 对密码进行加密
        userinfo.user_password = bcrypt.hashSync(userinfo.user_password, 10)
            // 定义插入新用户的 SQL 语句
        const sql = 'insert into account(user_name,user_password,user_identity) values(?,?,?)'
            // 调用 db.query() 执行 SQL 语句
        db.query(sql, [userinfo.user_name, userinfo.user_password, userinfo.user_identity], (err, results) => {
            // 判断 SQL 语句是否执行成功
            // if (err) return res.send({ status: 1, message: err.message })
            if (err) return res.cc(err)
                // 判断影响行数是否为 1
                // if (results.affectedRows !== 1) return res.send({ status: 1, message: '注册用户失败，请稍后再试！' })
            if (results.affectedRows !== 1) return res.cc('注册用户失败，请稍后再试！')
                // 注册用户成功
                // res.send({ status: 0, message: '注册成功！' })
            res.cc('注册成功！', 0)
        })
    })
}

// 登录的处理函数
exports.login = (req, res) => {
    // 接收表单的数据
    const userinfo = req.body
    var user_password = userinfo.user_password
    var user_name = userinfo.user_name
        // 定义 SQL 语句
    const sql = `select * from account where user_name=?`
        // 执行 SQL 语句，根据用户名查询用户的信息
    db.query(sql, user_name, (err, results) => {
        // 执行 SQL 语句失败
        if (err) return res.cc(err)
            // 执行 SQL 语句成功，但是获取到的数据条数不等于 1
        if (results.length !== 1) return res.cc('登录失败！')

        // TODO：判断密码是否正确
        const compareResult = bcrypt.compareSync(user_password, results[0].user_password)
        if (!compareResult) return res.cc('登录失败！')

        // TODO：在服务器端生成 Token 的字符串
        const user = {...results[0], user_password: '', user_pic: '' }
            // 对用户的信息进行加密，生成 Token 字符串
        const tokenStr = jwt.sign(user, config.jwtSecretKey, { expiresIn: config.expiresIn })
            // 调用 res.send() 将 Token 响应给客户端
        res.send({
            status: 0,
            message: '登录成功！',
            token: 'Bearer ' + tokenStr,
        })
    })
}

//获取用户信息的处理函数
exports.get_info = (req, res) => {
    res.send(req.user)
}

//修改用户信息的处理函数
exports.put_info = (req, res) => {
    let sql = 'update account set ? where user_id=?';
    let user_password = bcrypt.hashSync(req.body.user_password, 10)
    let id = req.user.user_id
        // console.log(req.user)
    let args = {
        user_name: req.body.user_name,
        user_password: user_password,
        user_email: req.body.user_email,
        user_nickname: req.body.user_nickname
    };
    // console.log(args);
    db.query(sql, [args, id], (err, result) => {
        if (err) {
            return res.status(500).json({ msg: err })
        }

        if (result.affectedRows === 0) {
            return res.status(500).end()
        }
        res.status(200).json({ msg: "用户信息更新成功" })
    })
}