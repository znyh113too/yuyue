package org.chao.yuyue;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static org.chao.yuyue.HttpRequest.login;

/**
 * @author xiezhengchao
 * @since 2019/4/15 12:15
 */
public class Yuyue {

	private static final MailServer MAIL_SERVER = new MailServer();

	// 114的登录账号密码
	private static final String USERNAME ="-";
	private static final String PASSWORD ="-";

    public static void main(String[] args) throws InterruptedException {
	    login("http://www.114yygh.com/quicklogin.htm", USERNAME, PASSWORD);
        String url = "http://www.114yygh.com/dpt/partduty.htm";

        while(true) {
	        LocalDate localDate = LocalDate.now();
	        LocalDate end = LocalDate.now().plusDays(30);

	        List<DoctorInfo> doctorInfoList = new ArrayList<>();
	        while (localDate.isBefore(end)) {
		        String dutyDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		        String param = "hospitalId=167&departmentId=200002612&dutyCode=1&isAjax=true&dutyDate=" + dutyDate;
		        String result = HttpRequest.sendPost(url, param);


		        JSONObject jsonObject = JSONObject.parseObject(result);

		        // {"data":[],"hasError":true,"code":2009,"msg":"用户未登录!"}
		        if ("2009".equals(jsonObject.getString("code"))) {
			        login("http://www.114yygh.com/quicklogin.htm", USERNAME, PASSWORD);
			        continue;
		        }

		        JSONArray jsonArray = jsonObject.getJSONArray("data");
		        if (jsonArray != null) {
			        for (int i = 0; i < jsonArray.size(); i++) {
				        JSONObject item = jsonArray.getJSONObject(i);
				        Integer remainAvailableNumber = item.getInteger("remainAvailableNumber");
				        String doctorName = item.getString("doctorName");
				        String doctorTitleName = item.getString("doctorTitleName");
				        String weekDay = localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CHINA);
				        // System.out.println(String.format("日期:%s %s 医师类别:%s-%s 剩余可预约数量:%s", dutyDate, weekDay, doctorName, doctorTitleName, remainAvailableNumber));
				        if (remainAvailableNumber > 0) {
					        doctorInfoList.add(new DoctorInfo(doctorName,doctorTitleName,dutyDate,weekDay,remainAvailableNumber));
				        }
			        }
		        }

		        localDate = localDate.plusDays(1);
		        TimeUnit.SECONDS.sleep(1);
	        }

	        if(!doctorInfoList.isEmpty()){
		        // System.out.println("doctorInfoList:{}"+doctorInfoList);
		        MAIL_SERVER.sendMail(doctorInfoList);
	        }
	        TimeUnit.SECONDS.sleep(10);
        }
    }
}
