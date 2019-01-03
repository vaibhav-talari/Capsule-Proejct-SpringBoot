/*package spring.core.boot;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import spring.core.boot.model.ParentTask;
import spring.core.boot.repository.IParentTaskRepo;
import spring.core.boot.service.IParentTaskService;
import spring.core.boot.service.ParentTaskServiceImpl;


@RunWith(SpringRunner.class)
public class IParentTaskServiceTest {
	
	@Autowired
	private IParentTaskService  parentTaskService;
	
	@Autowired
	private IParentTaskRepo parentTaskRepo;	
	
	@MockBean
	private IParentTaskRepo parentTaskRepo;
	
	@TestConfiguration
	static class ParentTaskServiceUnitTest{
		@Bean
		public IParentTaskService ParentTaskService(){
			return new ParentTaskServiceImpl();
		}
	}
		
	private ParentTask[] parentTasks;

	@Before
	public void setUp()
	{
		parentTasks=new ParentTask[] { 
				new ParentTask("Complete Task 1"),
				new ParentTask("Finish Project"),
				new ParentTask("Meet Manager"),
				new ParentTask("Approve Project")};
		
		for(int i=0;i<parentTasks.length;i++) {
			parentTaskRepo.save(parentTasks[i]);
			
		}
	}
	
	@Before
	public void setUp()
	{
		parentTasks=new ParentTask[] { 
				new ParentTask("Complete Task 1"),
				new ParentTask("Finish Project"),
				new ParentTask("Meet Manager"),
				new ParentTask("Approve Project")};
		Mockito.<Optional<ParentTask>when(parentTaskRepo.findByParentTask("Meet Manager"))
		.thenReturn(parentTasks[2]);
	}
	
	@Test
	public void whenFindParentTask_withName() {
		Optional<ParentTask> actual=parentTaskService.findByParentTaskName("Meet Manager");
		Optional<ParentTask> expected=parentTasks[2];
		assertThat(actual).isEqualTo(expected);


	}
	
	}


*/