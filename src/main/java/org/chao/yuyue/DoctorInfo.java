package org.chao.yuyue;

/**
 * @author xiezhengchao
 * @since 2019/4/16 11:18
 */
public class DoctorInfo {

	private String name;
	private String title;
	private String date;
	private String weekDay;
	private int remainAvailableNumber;

	public DoctorInfo(String name, String title, String date, String weekDay, int remainAvailableNumber) {
		this.name = name;
		this.title = title;
		this.date = date;
		this.weekDay = weekDay;
		this.remainAvailableNumber = remainAvailableNumber;
	}

	public String getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getRemainAvailableNumber() {
		return remainAvailableNumber;
	}

	public void setRemainAvailableNumber(int remainAvailableNumber) {
		this.remainAvailableNumber = remainAvailableNumber;
	}

	@Override
	public String toString() {
		return "DoctorInfo{" +
				"name='" + name + '\'' +
				", title='" + title + '\'' +
				", date='" + date + '\'' +
				", remainAvailableNumber=" + remainAvailableNumber +
				'}';
	}
}
