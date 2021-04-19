/**
 * 
 */
package com.example.demobatch.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tonys
 *
 */
@RestController
@Slf4j
public class BatchController {

	private JobLauncher jobLauncher;
	private Job job;
	
	public BatchController(JobLauncher jobLauncher, Job job) {
		this.job = job;
		this.jobLauncher = jobLauncher;
	}
	
	@GetMapping("jobs/load-data")
	public BatchStatus load() throws Exception {
		Map<String, JobParameter> parameters = new HashMap<>();
		parameters.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters jobParameters = new JobParameters(parameters);
		JobExecution jobExecution = jobLauncher.run(job, jobParameters);
		while(jobExecution.isRunning()) {
			log.info("...");
		}
		return jobExecution.getStatus();
	}
}
