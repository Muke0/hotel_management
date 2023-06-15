// 导入数据库操作模块
const db = require('../db/index')
    // 导入全局的配置文件
const config = require('../config')

//获取服务需求的处理函数
exports.get_service_need = (req, res) => {
    page = req.query.page || 0;
    size = req.query.size || 30;
    db.getConnection((err, conn) => {
        if (err) {
            console.log(err)
        } else {
            const sql = 'select * from demand where Dperformer is null limit ' + page + ',' + size;
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

//更新服务需求的处理函数
exports.update_service_need = (req, res) => {
    const info = req.body
    console.log(info)
    const date = new Date;
    const sqlStr = 'update demand set Dperformer=?,Dtime=?,Dmoney=? where Dno=?'
    db.query(sqlStr, [info.Dperformer, Date, info.Dmoney, info.Dno], (err, results) => {
        // 执行 SQL 语句失败
        if (err) {
            return res.cc(err)
        }
        res.cc('服务需求信息更新成功！', 0)
    })
}