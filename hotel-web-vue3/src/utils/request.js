// utils/request.js
import axios from 'axios'

const api = {
    // 员工管理相关
    getStaff(token, params) {
        return axios.get('/api/staff', {
            params,
        }, {
            Headers: {
                'token': token,
            }
        })
    },
    addStaff(token, Sid, Sname, Sposition, Sbasepay, Sbonus, Sworktime) {
        return axios.post('/api/staff', {
            Sid: Sid,
            Sname: Sname,
            Sposition: Sposition,
            Sbasepay: Sbasepay,
            Sbonus: Sbonus,
            Sworktime: Sworktime,
        }, {
            Headers: {
                'token': token,
            }
        })
    },
    deleteStaff(token, Sid) {
        return axios.delete('/api/staff', {
            Sid: Sid,
        }, {
            Headers: {
                'token': token,
            }
        })
    },
    putStaff(token, Sid, Sname, Sposition, Sbasepay, Sbonus, Sworktime) {
        return axios.put('/api/staff', {
            Sid: Sid,
            Sname: Sname,
            Sposition: Sposition,
            Sbasepay: Sbasepay,
            Sbonus: Sbonus,
            Sworktime: Sworktime,
        }, {
            Headers: {
                'token': token,
            }
        })
    },
}

export default api