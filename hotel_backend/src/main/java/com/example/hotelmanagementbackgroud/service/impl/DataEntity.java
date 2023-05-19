package com.example.hotelmanagementbackgroud.service.impl;

import com.example.hotelmanagementbackgroud.character.*;
import com.example.hotelmanagementbackgroud.dao.DataMapper;
import com.example.hotelmanagementbackgroud.dao.homestateMapper;
import com.example.hotelmanagementbackgroud.model.Data;
import com.example.hotelmanagementbackgroud.model.DataExample;
import com.example.hotelmanagementbackgroud.model.homestate;
import com.example.hotelmanagementbackgroud.model.homestateExample;
import com.example.hotelmanagementbackgroud.service.Dataservice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DataEntity implements Dataservice {
    @Resource
    private DataMapper dataMapper;
    @Resource
    private homestateMapper homestatemapper;
    @Override
    @Transactional
    public Data getmoredata(Data data) {
        DataExample dataExample = new DataExample();
        DataExample.Criteria criteria = dataExample.createCriteria();
        criteria.andNameEqualTo(data.getName());
        criteria.andSexEqualTo(data.getSex());
        criteria.andHomecodeEqualTo(data.getHomecode());
        criteria.andIdentityEqualTo(data.getIdentity());
        List<Data> my_data = dataMapper.selectByExample(dataExample);
        if(my_data.size() == 0){
            return null;
        }else{
            return my_data.get(0);
        }
    }

    @Override
    @Transactional
    public String updatedata(@RequestBody Data data ) {
        dataMapper.updateByPrimaryKeySelective(data);
        return "true";
    }

    @Override
    @Transactional
    public List<Data> getdata(int page) {
        DataExample dataExample = new DataExample();
        List<Data> my_data = dataMapper.selectByExample(dataExample);
        List<Data> x_data = new ArrayList<>();
        for(Data x:my_data){
            if(x.getOuttime()==null){
                x_data.add(x);
            }
        }
        return x_data;
    }

    @Override
    @Transactional
    public getdatashow searchdata(String identity) {
        DataExample dataExample = new DataExample();
        String[] s = identity.split("[+]");
        DataExample.Criteria criteria= dataExample.createCriteria();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        if(s.length >= 1 && !s[0].equals("")) {
            criteria.andIdentityEqualTo(s[0]);
        }
        if(s.length >= 2 && !s[1].equals("null")){
            Long my_date1;
            try {
                Date before = simpleDateFormat.parse(s[1]);
                my_date1 = Long.valueOf(simpleDateFormat.format(before));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            criteria.andIntimeGreaterThanOrEqualTo(my_date1);
        }
        if(s.length >= 3 && !s[2].equals("null")){
            Long my_date2;
            try {
                Date after = simpleDateFormat.parse(s[2]);
                my_date2 = Long.valueOf(simpleDateFormat.format(after));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            criteria.andOuttimeLessThanOrEqualTo(my_date2);
        }
        List<Data> my_data = dataMapper.selectByExample(dataExample);
        int q=0;
        for(Data x:my_data){
            q++;
        }
        getdatashow t = new getdatashow();
        t.dataList = my_data;
        t.a = q;
        return t;
    }

    @Override
    @Transactional
    public int deletdata(Data data) {
        DataExample dataExample = new DataExample();
        homestateExample homestateexample = new homestateExample();

        DataExample.Criteria criteria = dataExample.createCriteria();
        homestateExample.Criteria criteria1 = homestateexample.createCriteria();
        criteria.andIdEqualTo(data.getId());
        List<Data> my_data = dataMapper.selectByExample(dataExample);
        my_data.get(0).setOuttime(data.getOuttime());
        criteria1.andHomecodeEqualTo(my_data.get(0).getHomecode());
        List<homestate> state = homestatemapper.selectByExample(homestateexample);
        state.get(0).setState("3");
        homestatemapper.updateByPrimaryKeySelective(state.get(0));
        dataMapper.updateByPrimaryKey(my_data.get(0));
        return 1;
    }

    @Override
    @Transactional
    public int adddata(Data data) {
        dataMapper.insert(data);
        homestateExample homestateexample = new homestateExample();
        homestateExample.Criteria criteria = homestateexample.createCriteria();
        criteria.andHomecodeEqualTo(data.getHomecode());
        List<homestate> my_state = homestatemapper.selectByExample(homestateexample);
        my_state.get(0).setState("2");
        homestatemapper.updateByPrimaryKeySelective(my_state.get(0));
        return 1;
    }

    @Override
    @Transactional
    public int getallcount() {
        DataExample dataExample = new DataExample();
        List<Data> data = dataMapper.selectByExample(dataExample);
        int x = 0;

        for(Data a : data){
            x++;
        }
        return x;
    }

    @Override
    @Transactional
    public databoth datashow(int i) {
        DataExample dataExample = new DataExample();
        DataExample.Criteria criteria = dataExample.createCriteria();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        if(i == 1){
            String[] str = new String[7];
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            if(day == 1){
                calendar.add(Calendar.DAY_OF_YEAR, -6);
            }else {
                calendar.add(Calendar.DAY_OF_WEEK, -(day-2));
            }
            for(int j = 0;j<7;j++){
                Date date = calendar.getTime();
                str[j] = simpleDateFormat.format(date);
                calendar.add(Calendar.DAY_OF_WEEK, 1);
            }
            criteria.andIntimeBetween(Long.valueOf(str[0]),Long.valueOf(str[6]));
            List<Data> my_data = dataMapper.selectByExample(dataExample);
            int[] sum = new int[7];
            int[] count = new int[7];
            for (int j = 0; j < 7; j++) {
                sum[j] = 0;
            }

            //按周价格

            for (Data x : my_data) {
                Long now = x.getIntime();
                if (now.equals(Long.valueOf(str[0]))) {

                    sum[0] += Integer.parseInt(x.getYuan());
                    count[0]++;
                } else if (now.equals(Long.valueOf(str[1]))) {

                    sum[1] += Integer.parseInt(x.getYuan());
                    count[1]++;
                } else if (now.equals(Long.valueOf(str[2]))) {

                    sum[2] += Integer.parseInt(x.getYuan());
                    count[2]++;
                } else if (now.equals(Long.valueOf(str[3]))) {

                    sum[3] += Integer.parseInt(x.getYuan());
                    count[3]++;
                } else if (now.equals(Long.valueOf(str[4]))) {

                    sum[4] += Integer.parseInt(x.getYuan());
                    count[4]++;
                } else if (now.equals(Long.valueOf(str[5]))) {

                    sum[5] += Integer.parseInt(x.getYuan());
                    count[5]++;
                } else if (now.equals(Long.valueOf(str[6]))) {

                    sum[6] += Integer.parseInt(x.getYuan());
                    count[6]++;
                }
            }
            databoth q = new databoth();
            q.a = count;
            q.money = sum;
            q.b = str;
            return q;
        }else if(i == 2){
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            calendar.roll(Calendar.DAY_OF_MONTH, -(day-1));
            int month = calendar.get(Calendar.MONTH);
            int n = 0;
            int year = calendar.get(Calendar.YEAR);
            switch (month){
                case 0:
                case 2:
                case 4:
                case 6:
                case 7:
                case 9:
                case 11:
                    n=31;break;
                case 1: if(year%100 == 0){
                    if (year%400==0){
                        n=29;
                    }else{
                        n=28;
                    }
                }else{
                    if(year % 4 ==0){
                        n=29;
                    }else{
                        n=28;
                    }
                }
                break;
                case 3:
                case 5:
                case 8:
                case 10:
                    n = 30;break;
            }
            String[] str = new String[4];
            int[] sum = new int[4];
            int[] count = new int[4];

            for(int p = 0; p<3; p++){
                str[p] = simpleDateFormat.format(calendar.getTime());
                calendar.roll(Calendar.DAY_OF_MONTH,7);
            }
            str[3] = simpleDateFormat.format(calendar.getTime());
            for(int p =0;p<4;p++){
                sum[p] = 0;
                count[p] = 0;
            }
            calendar.roll(Calendar.DAY_OF_MONTH, n-22);
            Date date = calendar.getTime();
            Long last = Long.valueOf(simpleDateFormat.format(date));
            criteria.andIntimeBetween(Long.valueOf(str[0]), last);
            List<Data> my_data = dataMapper.selectByExample(dataExample);

            for(Data m: my_data) {
                Long x = m.getIntime();
                if (x >= Integer.parseInt(str[0]) && x < Integer.parseInt(str[1])) {
                    sum[0] += Integer.parseInt(m.getYuan());
                    count[0]++;
                } else if (x < Integer.parseInt(str[2])) {
                    sum[1] += Integer.parseInt(m.getYuan());
                    count[1]++;
                } else if(x < Integer.parseInt(str[3]) ) {
                    sum[2] += Integer.parseInt(m.getYuan());
                    count[2]++;
                }else{
                    sum[3] += Integer.parseInt(m.getYuan());
                    count[3]++;
                }
            }
            databoth p = new databoth();
            p.b = str;
            p.a = count;
            p.money = sum;
            return p;

        }else if(i == 3){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_YEAR, 1);
            String[] str = new String[12];
            str[0] = simpleDateFormat.format(calendar.getTime());
            for(int j=1;j<12;j++){
                calendar.roll(Calendar.MONTH,1);
                str[j] = simpleDateFormat.format(calendar.getTime());
            }
            int[] sum = new int[12];
            int[] count = new int[12];
            for(int j=0;j<12;j++){
                sum[j]=0;
                count[j]=0;
            }
            calendar.set(Calendar.DAY_OF_YEAR, calendar.getMaximum(Calendar.DAY_OF_YEAR));
            Long last = Long.valueOf(simpleDateFormat.format(calendar.getTime()));
            criteria.andIntimeBetween(Long.valueOf(str[0]), last);
            List<Data> my_data = dataMapper.selectByExample(dataExample);
            for(Data x:my_data){
                Long now = x.getIntime();
                if(now<Integer.parseInt(str[1])){
                    sum[0] += Integer.parseInt(x.getYuan());
                    count[0]++;
                }else if(now<Integer.parseInt(str[2])){
                    sum[1] += Integer.parseInt(x.getYuan());
                    count[1]++;
                }else if(now<Integer.parseInt(str[3])){
                    sum[2] += Integer.parseInt(x.getYuan());
                    count[2]++;
                }else if(now<Integer.parseInt(str[4])){
                    sum[3] += Integer.parseInt(x.getYuan());
                    count[3]++;
                }else if(now<Integer.parseInt(str[5])){
                    sum[4] += Integer.parseInt(x.getYuan());
                    count[4]++;
                }else if(now<Integer.parseInt(str[6])){
                    sum[5] += Integer.parseInt(x.getYuan());
                    count[5]++;
                }else if(now<Integer.parseInt(str[7])){
                    sum[6] += Integer.parseInt(x.getYuan());
                    count[6]++;
                }else if(now<Integer.parseInt(str[8])){
                    sum[7] += Integer.parseInt(x.getYuan());
                    count[7]++;
                }else if(now<Integer.parseInt(str[9])){
                    sum[8] += Integer.parseInt(x.getYuan());
                    count[8]++;
                }else if(now<Integer.parseInt(str[10])){
                    sum[9] += Integer.parseInt(x.getYuan());
                    count[9]++;
                }else if(now<Integer.parseInt(str[11])){
                    sum[10] += Integer.parseInt(x.getYuan());
                    count[10]++;
                }else{
                    sum[11] += Integer.parseInt(x.getYuan());
                    count[11]++;
                }
            }
            databoth q = new databoth();
            q.b= str;
            q.a = count;
            q.money = sum;
            return q;
        } else{return null;}
    }

    @Override
    @Transactional
    public List<todayshowcharacter> todayshow() {
        DataExample dataExample = new DataExample();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        Calendar calendar =Calendar.getInstance();
        Date date = calendar.getTime();
        DataExample.Criteria criteria = dataExample.createCriteria();
        criteria.andIntimeEqualTo(Long.valueOf(simpleDateFormat.format(date)));
        List<Data> dataList = dataMapper.selectByExample(dataExample);
        List<todayshowcharacter> todayshowcharacterList = new ArrayList<>();
        for(int i=0;i<4;i++)
        {
            todayshowcharacterList.add(new todayshowcharacter(0));
        }

        todayshowcharacterList.get(0).a = "A";
        todayshowcharacterList.get(1).a = "B";
        todayshowcharacterList.get(2).a = "C";
        todayshowcharacterList.get(3).a = "D";

        for(Data x:dataList){
            if(x.getHomecode().startsWith("A")){
                todayshowcharacterList.get(0).count++;
            } else if (x.getHomecode().startsWith("B")) {
                todayshowcharacterList.get(1).count++;
            } else if (x.getHomecode().startsWith("C")) {
                todayshowcharacterList.get(2).count++;
            } else if (x.getHomecode().startsWith("D")) {
                todayshowcharacterList.get(3).count++;
            }
        }
        

        return todayshowcharacterList;
    }
    @Override
    @Transactional
    public income todayincome() {
        DataExample dataExample = new DataExample();

        Calendar calendar =Calendar.getInstance();//获取现在的时间
        Date date = calendar.getTime();//getTime()方法用于获取带有时间的日期(以毫秒为单位)，将现在的时间以Date形式保存

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");//设置日期格式

        DataExample.Criteria criteria = dataExample.createCriteria();
        criteria.andIntimeEqualTo(Long.valueOf(simpleDateFormat.format(date)));//使用format()方法，将日期类型转换为自己自定义的字符串格式
        List<Data> dataList = dataMapper.selectByExample(dataExample);
        int count = 0;//顾客数量
        int  r =0 ;//收入
        for (Data x : dataList) {
            r += Integer.parseInt(x.getYuan());
            count++;
        }

        income i = new income();
        i.n = count;
        i.revenue = r;
        return i;
    }

    @Override
    public beforehomeshowcharacter beforehomeshow() {
        DataExample dataExample = new DataExample();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        calendar.add(Calendar.DAY_OF_YEAR, -6);
        int[][] x = new int[7][4];
        for(int i=0;i<7;i++){
            for(int j =0;j<4;j++){
                x[i][j]=0;
            }
        }
        Long[] date = new Long[7];
        for(int i =0;i<7;i++){
            date[i] = Long.valueOf(simpleDateFormat.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_YEAR,1);
        }
        DataExample.Criteria criteria = dataExample.createCriteria();
        criteria.andIntimeBetween(date[0], date[6]);
        List<Data> dataList = dataMapper.selectByExample(dataExample);
        for(Data d: dataList){
            if(d.getIntime().equals(date[0])){
                if(d.getHomecode().startsWith("A")){
                    x[0][0]++;
                } else if (d.getHomecode().startsWith("B")) {
                    x[0][1]++;
                } else if (d.getHomecode().startsWith("C")) {
                    x[0][2]++;
                } else if (d.getHomecode().startsWith("D")) {
                    x[0][3]++;
                }
            } else if (d.getIntime().equals(date[1])) {
                if(d.getHomecode().startsWith("A")){
                    x[1][0]++;
                } else if (d.getHomecode().startsWith("B")) {
                    x[1][1]++;
                } else if (d.getHomecode().startsWith("C")) {
                    x[1][2]++;
                } else if (d.getHomecode().startsWith("D")) {
                    x[1][3]++;
                }
            } else if (d.getIntime().equals(date[2])) {
                if(d.getHomecode().startsWith("A")){
                    x[2][0]++;
                } else if (d.getHomecode().startsWith("B")) {
                    x[2][1]++;
                } else if (d.getHomecode().startsWith("C")) {
                    x[2][2]++;
                } else if (d.getHomecode().startsWith("D")) {
                    x[2][3]++;
                }
            } else if (d.getIntime().equals(date[3])) {
                if(d.getHomecode().startsWith("A")){
                    x[3][0]++;
                } else if (d.getHomecode().startsWith("B")) {
                    x[3][1]++;
                } else if (d.getHomecode().startsWith("C")) {
                    x[3][2]++;
                } else if (d.getHomecode().startsWith("D")) {
                    x[3][3]++;
                }
            } else if (d.getIntime().equals(date[4])) {
                if(d.getHomecode().startsWith("A")){
                    x[4][0]++;
                } else if (d.getHomecode().startsWith("B")) {
                    x[4][1]++;
                } else if (d.getHomecode().startsWith("C")) {
                    x[4][2]++;
                } else if (d.getHomecode().startsWith("D")) {
                    x[4][3]++;
                }
            } else if (d.getIntime().equals(date[5])) {
                if(d.getHomecode().startsWith("A")){
                    x[5][0]++;
                } else if (d.getHomecode().startsWith("B")) {
                    x[5][1]++;
                } else if (d.getHomecode().startsWith("C")) {
                    x[5][2]++;
                } else if (d.getHomecode().startsWith("D")) {
                    x[5][3]++;
                }
            } else {
                if(d.getHomecode().startsWith("A")){
                    x[6][0]++;
                } else if (d.getHomecode().startsWith("B")) {
                    x[6][1]++;
                } else if (d.getHomecode().startsWith("C")) {
                    x[6][2]++;
                } else if (d.getHomecode().startsWith("D")) {
                    x[6][3]++;
                }
            }
        }
        beforehomeshowcharacter p = new beforehomeshowcharacter();
        p.x=x;
        return p;
    }


}
