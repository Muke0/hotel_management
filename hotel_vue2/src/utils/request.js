// utils/request.js
import axios from 'axios'

const api = {
    // 疫情每日新增数据
    getStaff(params) {
        return axios.get('/api/staff', {
            params,
        })
    },
    addStaff(Sid, Sname, Sposition, Sbasepay, Sbonus, Sworktime) {
        return axios.post('/api/staff', {
            Sid: Sid,
            Sname: Sname,
            Sposition: Sposition,
            Sbasepay: Sbasepay,
            Sbonus: Sbonus,
            Sworktime: Sworktime,
        })
    },
    deleteStaff(Sid) {
        return axios.delete('/api/staff', {
            Sid: Sid,
        })
    },
    putStaff(Sid, Sname, Sposition, Sbasepay, Sbonus, Sworktime) {
        return axios.put('/api/staff', {
            Sid: Sid,
            Sname: Sname,
            Sposition: Sposition,
            Sbasepay: Sbasepay,
            Sbonus: Sbonus,
            Sworktime: Sworktime,
        })
    },
}

export default api