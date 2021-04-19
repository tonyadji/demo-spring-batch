package com.example.demobatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;

import com.example.demobatch.entites.BankTransaction;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private ItemReader<BankTransaction> bankTransactionItemReader;
	@Autowired
	private ItemWriter<BankTransaction> bankTransactionItemWriter;
	@Autowired
	private ItemProcessor<BankTransaction, BankTransaction> bankTransactionItemProcessor;
	
	@Bean
	public Job bankJob() {
		
		Step step = stepBuilderFactory.get("step-load-data")
				.<BankTransaction, BankTransaction>chunk(100)
				.reader(bankTransactionItemReader)
				.processor(bankTransactionItemProcessor)
				.writer(bankTransactionItemWriter)
				.build();
		
		return jobBuilderFactory.get("bank-data-loader-job")
				.start(step)
				.build();
	}
	
	@Bean
	public FlatFileItemReader<BankTransaction> flatFileItemReader(@Value("${inputFile}") String inputResource) throws Exception{
		System.out.println("INPUT RESOURCE "+inputResource);
		FlatFileItemReader<BankTransaction> fileItemReader = new FlatFileItemReader<>();
		fileItemReader.setName("TRANSACTIONS-READER");
		fileItemReader.setLinesToSkip(1);
		fileItemReader.setResource(new UrlResource(inputResource));
		fileItemReader.setLineMapper(lineMapper());
		return fileItemReader;
	}
	
	@Bean
	public LineMapper<BankTransaction> lineMapper(){
		DefaultLineMapper<BankTransaction> lineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("id","accountID","strDate","transactionType","amount");
		lineMapper.setLineTokenizer(lineTokenizer);
		BeanWrapperFieldSetMapper<BankTransaction> fiedSetWrapper = new BeanWrapperFieldSetMapper<>();
		fiedSetWrapper.setTargetType(BankTransaction.class);
		lineMapper.setFieldSetMapper(fiedSetWrapper);
		return lineMapper;
	}
	
}
