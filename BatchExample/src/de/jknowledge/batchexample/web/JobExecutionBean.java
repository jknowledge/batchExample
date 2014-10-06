package de.jknowledge.batchexample.web;

import java.util.Properties;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="jobExecutionBean")
@SessionScoped
public class JobExecutionBean {
	
	private long jobId;
	
	public void actionStartJob() {
		JobOperator jobOperator = BatchRuntime.getJobOperator();
		Properties props = new Properties();
		jobId = jobOperator.start("TaxCalculatorJob", props);
	}
	
	public void actionRestartJob() {
		JobOperator jobOperator = BatchRuntime.getJobOperator();
		Properties props = new Properties();
		jobId = jobOperator.restart(jobId, props);
	}
	
	public long getJobId() {
		return jobId;
	}

}
 