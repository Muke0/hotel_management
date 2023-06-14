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
    const sqlStr = 'update vip set VIPlevel=?,VIPpower?,VIPduration=?,VIPpoints=?,VIPinformation=? where VIPno=?'
    db.query(sqlStr, [VIPlevel, VIPpower, VIPduration, VIPpoints, VIPinformation, VIPno], (err, results) => {
        // 执行 SQL 语句失败
        console.log(sqlStr)
        if (err) {
            return res.cc(err)
        }
        res.cc('会员信息修改成功！', 0)
    })
}