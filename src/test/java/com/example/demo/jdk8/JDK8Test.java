package com.example.demo.jdk8;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

public class JDK8Test {
	
	private void sortWithJdk7(List<String> names) {
		// 使用匿名函数
		Collections.sort(names, new Comparator<String>() {

			@Override
			public int compare(String s1, String s2) {
				
				return s1.compareTo(s2);
			}
		
		});
	}
	
	private void sortWithJdk8(List<String> names) {
		// 使用lambda表达式
		Collections.sort(names, (s1, s2) -> s1.compareTo(s2));
	}
	
	@Test
	public void lambda() {
		List<String> names1 = Arrays.asList("Google", "Baidu", "Alibaba", "Tencent");
		List<String> names2 = Arrays.asList("Google", "Baidu", "Alibaba", "Tencent");
		
		// 使用jdk7排序
		System.out.print("使用jdk7排序：");
		sortWithJdk7(names1);
		System.out.println(names1);
		
		// 使用jdk8排序
		System.out.print("使用jdk8排序：");
		sortWithJdk8(names2);
		System.out.println(names2);
		
		names1.forEach(str -> System.out.println(str));
		names1.forEach(System.out :: println);
		
		// lambda可看成实现了某方法的接口
		MathOperation addition = (int a, int b) -> a + b;
		MathOperation substraction = (a, b) -> a - b;
		MathOperation multiplication = (a, b) -> {return a + b;};
		MathOperation division = (a, b) -> a / b;
		
		System.out.println("10 + 5 = " + addition.run(10, 5));
		System.out.println("10 - 5 = " + substraction.run(10, 5));
		System.out.println("10 × 5 = " + multiplication.run(10, 5));
		System.out.println("10 ÷ 5 = " + division.run(10, 5));
		
		
	}
	
	interface MathOperation {
		int run(int a, int b);
	}
	
	@Test
	public void date() {
		
		LocalDateTime currentTime = LocalDateTime.now();
		LocalDate date1 = currentTime.toLocalDate();
		LocalTime time1 = currentTime.toLocalTime();
		System.out.println("date1: " + date1);
		System.out.println("time1: " + time1);
		
		LocalDate date2 = LocalDate.now();
		LocalTime time2 = LocalTime.now();
		System.out.println("date2: " + date2);
		System.out.println("time2: " + time2);
		
		int year = currentTime.getYear();
		int month = currentTime.getMonthValue();
		int day = currentTime.getDayOfMonth();
		int hour = currentTime.getHour();
		int minute = currentTime.getMinute();
		int second = currentTime.getSecond();
		int nano = currentTime.getNano();
		System.out.println(String.format("year:[%d] month:[%d] day:[%d] hour:[%d] minute:[%d] second:[%d] nano:[%d]", 
				year, month, day, hour, minute, second, nano));
		
		// 两个时间相隔的天数和小时数 
		LocalDateTime from = LocalDateTime.of(2008, 10, 1, 20, 0, 0);
		LocalDateTime to = LocalDateTime.of(2019, 10, 1, 20, 0, 0);
		Duration duration = Duration.between(from, to);
		System.out.println(duration.toDays() + ", " + duration.toHours());
		
		LocalDate date3 = LocalDate.parse("2019-04-15");
		LocalTime time3 = LocalTime.parse("20:15:30");
		System.out.println("date3: " + date3);
		System.out.println("time3: " + time3);
	}
	
}
