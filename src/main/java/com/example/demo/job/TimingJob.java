package com.example.demo.job;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TimingJob {

	@Scheduled(cron = "${task.timing.cron}")
	public void handle() {
		
		LocalDateTime currentTime = LocalDateTime.now();
		LocalDate date = currentTime.toLocalDate();
		int hour = currentTime.getHour();
		int minute = currentTime.getMinute();
		
		if (minute == 0)
			log.info("日期：{} >> 北京时间 {} 点整", date, hour);
		else
			log.info("日期：{} >> 北京时间 {} 点 {} 分", date, hour, minute);
	}
	
}
