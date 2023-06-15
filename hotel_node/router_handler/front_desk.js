// 导入数据库操作模块
const db = require('../db/index')
    // 导入全局的配置文件
const config = require('../config')

// 上传会员信息的处理函数
exports.upload_vip = (req, res) => {
    const info = req.body
        // 定义 SQL 语句，插入会员信息
    const sqlStr = 'insert into vip(VIPno,VIPlevel,VIPpower,VIPduration,VIPpoints,VIPinformation) values(?,?,?,?,?,?)'
    db.query(sqlStr, [info.VIPno, info.VIPlevel, info.VIPpower, info.VIPduration, info.VIPpoints, info.VIPinformation], (err, results) => {
        // 执行 SQL 语句失败
        if (err) {
            return res.cc(err)
        }
        res.cc('会员添加成功！', 0)
    })
}

//获取会员信息的处理函数
exports.get_vip = (req, res) => {
    page = req.query.page || 0;
    size = req.query.size || 30;
    db.getConnection((err, conn) => {
        if (err) {
            console.log(err)
        } else {
            const sql = 'select * from vip limit ' + page + ',' + size;
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

//删除会员信息的处理函数
exports.delete_vip = (req, res) => {
    VIPno = req.body.VIPno;
    db.getConnection((err, conn) => {
        if (err) {
            console.log(err)
        } else {
            const sql = 'delete from vip where VIPno=?';
            conn.query(sql, [VIPno], (err, result) => {
                if (err) {
                    console.log(err);
                } else {
                    if (result.affectedRows == 0) {
                        res.cc("不存在该会员", 1);
                    } else {
                        res.cc("删除成功", 0);
                    }
                    conn.release();
                }
            })
        }
    })
}

//更新会员信息的处理函数
exports.update_vip = (req, res) => {
    //const info = req.body
    console.log(req.body)
    const { VIPno, VIPlevel, VIPpower, VIPduration, VIPpoints, VIPinformation } = req.body

    const sqlStr = 'update vip set VIPlevel = ?,VIPpower=?,VIPduration=?,VIPpoints=?,VIPinformation=? where VIPno=?'
    db.query(sqlStr, [VIPlevel, VIPpower, VIPduration, VIPpoints, VIPinformation, VIPno], (err, results) => {
        // 执行 SQL 语句失败
        console.log(sqlStr)
        if (err) {
            return res.cc(err)
        }
        res.cc('会员信息修改成功！', 0)
    })
}

//获取房间信息
exports.get_room_info = (req, res) => {
    page = req.query.page || 0;
    size = req.query.size || 30;
    db.getConnection((err, conn) => {
        if (err) {
            console.log(err)
        } else {
            const sql = 'select * from room_message limit ' + page + ',' + size;
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

//修改房间信息
exports.update_room_info = (req, res) => {
    const info = req.body
    if (info.KFRZnumber != null) {
        var curdate = new Date();
        const sqlStr1 = 'insert into roomcheck(KFRZnumber,KFtime) values(?,?)'
        db.query(sqlStr1, [info.KFRZnumber, curdate], (err, results) => {
            // 执行 SQL 语句失败
            if (err) {
                return res.cc(err)
            }
            console.log('入住信息更新成功！')
        })
    }
    const sqlStr2 = 'update room_message set KFRZnumber=? where FJnumber=?'
    db.query(sqlStr2, [info.KFRZnumber, info.FJnumber], (err, results) => {
        // 执行 SQL 语句失败
        if (err) {
            return res.cc(err)
        }
        res.cc('房间信息更新成功！', 0)
    })
}

//获取预约信息
exports.get_appointment = (req, res) => {
    page = req.query.page || 0;
    size = req.query.size || 30;
    db.getConnection((err, conn) => {
        if (err) {
            console.log(err)
        } else {
            const sql = 'select * from reservation limit ' + page + ',' + size;
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

//修改预约信息状态
exports.update_appointment = (req, res) => {
    const info = req.body
    const sqlStr = 'update reservation set passed=? where Rno=?'
    db.query(sqlStr, [info.passed, info.Rno], (err, results) => {
        // 执行 SQL 语句失败
        if (err) {
            return res.cc(err)
        }
        res.cc('预约状态修改成功!当前为' + info.passed, 0)
    })
}

// 上传用户信息的处理函数
exports.upload_user = (req, res) => {
    const info = req.body
        // 定义 SQL 语句，插入会员信息
    const sqlStr = 'insert into user(Uno,Uname,Usex,Umail) values(?,?,?,?)'
    db.query(sqlStr, [info.Uno, info.Uname, info.Usex, info.Umail], (err, results) => {
        // 执行 SQL 语句失败
        if (err) {
            return res.cc(err)
        }
        res.cc('用户添加成功！', 0)
    })
}

//获取用户信息的处理函数
exports.get_user = (req, res) => {
    page = req.query.page || 0;
    size = req.query.size || 30;
    db.getConnection((err, conn) => {
        if (err) {
            console.log(err)
        } else {
            const sql = 'select * from user limit ' + page + ',' + size;
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

//删除用户信息的处理函数
exports.delete_user = (req, res) => {
    Uno = req.body.Uno;
    db.getConnection((err, conn) => {
        if (err) {
            console.log(err)
        } else {
            const sql = 'delete from user where Uno=?';
            conn.query(sql, [Uno], (err, result) => {
                if (err) {
                    console.log(err);
                } else {
                    if (result.affectedRows == 0) {
                        res.cc("不存在该用户", 1);
                    } else {
                        res.cc("删除成功", 0);
                    }
                    conn.release();
                }
            })
        }
    })
}

//更新用户信息的处理函数
exports.update_user = (req, res) => {
    //const info = req.body
    const { Uno, VIPno, Uname, Usex, Umail } = req.body

    const sqlStr = 'update user set VIPno = ?,Uname=?,Usex=?,Umail=? where Uno=?'
    db.query(sqlStr, [VIPno, Uname, Usex, Umail, Uno], (err, results) => {
        // 执行 SQL 语句失败
        console.log(sqlStr)
        if (err) {
            return res.cc(err)
        }
        res.cc('用户信息修改成功！', 0)
    })
}