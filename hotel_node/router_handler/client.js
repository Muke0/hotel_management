// 导入数据库操作模块
const db = require('../db/index')
    // 导入全局的配置文件
const config = require('../config')

// 上传需求信息的处理函数
exports.upload_demand = (req, res) => {
    const info = req.body
    const sqlStr = 'insert into demand(Dno,Uno,Dinitiator,Dcontent,Ddescription) values(?,?,?,?,?)'
    db.query(sqlStr, [info.Dno, info.Uno, info.Dinitiator, info.Dcontent, info.Ddescription], (err, results) => {
        // 执行 SQL 语句失败
        if (err) {
            return res.cc(err)
        }
        res.cc('需求添加成功！', 0)
    })
}

//获取需求信息的处理函数
exports.get_demand = (req, res) => {
    page = req.query.page || 0;
    size = req.query.size || 30;
    db.getConnection((err, conn) => {
        if (err) {
            console.log(err)
        } else {
            const sql = 'select * from demand limit ' + page + ',' + size;
            conn.query(sql, (err, result) => {
                if (err) {
                    console.log(err);
                } else {
                    res.json(result)
                    conn.release();
                }
            })
        }
    })
}

//获取房间状态
exports.get_room_info = (req, res) => {
    page = req.query.page || 0;
    size = req.query.size || 30;
    db.getConnection((err, conn) => {
        if (err) {
            console.log(err)
        } else {
            const sql = 'select * from room_message where KFRZnumber is null limit ' + page + ',' + size;
            conn.query(sql, (err, result) => {
                if (err) {
                    console.log(err);
                } else {
                    res.json(result)
                    conn.release();
                }
            })
        }
    })
}

//上传预约请求
exports.upload_appointment = (req, res) => {
    const info = req.body
    console.log(info)
    const sqlStr = 'insert into reservation(Rno,Uno,Rtype,Rmoney,passed) values(?,?,?,?,0)'
    db.query(sqlStr, [info.Rno, info.Uno, info.Rtype, info.Rmoney], (err, results) => {
        // 执行 SQL 语句失败
        if (err) {
            return res.cc(err)
        }
        res.cc('预约请求添加成功！', 0)
    })
}

//获取账单
exports.get_bill = (req, res) => {
    page = req.query.page || 0;
    size = req.query.size || 30;
    db.getConnection((err, conn) => {
        if (err) {
            console.log(err)
        } else {
            const sql = 'select * from bill limit ' + page + ',' + size;
            conn.query(sql, (err, result) => {
                if (err) {
                    console.log(err);
                } else {
                    res.json(result)
                    conn.release();
                }
            })
        }
    })
}

//账单付款的处理函数
exports.update_bill = (req, res) => {
    const info = req.body
    const sqlStr = 'update bill set paid=? where Bno=?'
    db.query(sqlStr, [info.paid, info.Bno], (err, results) => {
        // 执行 SQL 语句失败
        if (err) {
            return res.cc(err)
        }
        res.cc('账单付款成功！', 0)
    })
}

//上传用户评价
exports.upload_evaluation = (req, res) => {
    const info = req.body
    console.log(info)
    const sqlStr = 'insert into evaluate(Eno,Dno,Uno,Estar,Einformation) values(?,?,?,?,?)'
    db.query(sqlStr, [info.Eno, info.Dno, info.Uno, info.Estar, info.Einformation], (err, results) => {
        // 执行 SQL 语句失败
        if (err) {
            return res.cc(err)
        }
        res.cc('用户评价添加成功！', 0)
    })
}