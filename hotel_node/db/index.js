const mysql = require('mysql')

const db = mysql.createPool({
    connectionLimit: 20, //数据库最大连接数量
    host: '127.0.0.1', //数据库地址，本机为127.0.0.1
    port: 3309, //数据库端口，默认为3306
    user: 'root', //数据库用户名
    password: 'zhaokedi', //数据库密码
    database: 'hotel_management', //数据库名
})

module.exports = db