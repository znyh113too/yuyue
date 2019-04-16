package org.chao.yuyue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xiezhengchao
 * @since 2019/4/16 11:07
 */
public class MailServer {

	private Map<String,Long> silentMap = new HashMap<>();
	private String receiveMail = "513016881@qq.com";

	public void sendMail(List<DoctorInfo> doctorInfoList){

		String silentKey = doctorInfoList.stream().map(DoctorInfo::toString).collect(Collectors.joining(","));

		Long expectEndTime = silentMap.get(silentKey);
		if(expectEndTime==null){
			expectEndTime = resetSilent(silentKey);
			doSend(doctorInfoList);
		}

		if(System.currentTimeMillis() < expectEndTime){
			return;
		}

		resetSilent(silentKey);
		doSend(doctorInfoList);
	}

	private void doSend(List<DoctorInfo> doctorInfoList){
		String content = doctorInfoList.stream()
				.map(doctorInfo ->
						String.format("日期:%s %s 医师类别:%s-%s 剩余可预约数量:%s <br/>", doctorInfo.getDate(), doctorInfo.getWeekDay(), doctorInfo.getName(), doctorInfo.getTitle(), doctorInfo.getRemainAvailableNumber()))
				.collect(Collectors.joining());
		MailUtil.send(content,receiveMail);
		System.out.println("doctorInfoList:"+doctorInfoList);
		System.out.println(receiveMail+"邮件发送成功!");
	}

	private long resetSilent(String silentKey){
		long expectEndTime = LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		silentMap.put(silentKey,expectEndTime);
		return expectEndTime;
	}
}
