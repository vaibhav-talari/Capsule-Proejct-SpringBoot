package spring.core.boot;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import spring.core.boot.model.ChildTask;
import spring.core.boot.model.ParentTask;
import spring.core.boot.repository.IChildTaskRepo;
import spring.core.boot.service.ChildTaskServiceImpl;
import spring.core.boot.service.IChildTaskService;

@RunWith(SpringRunner.class)
public class IChildTaskServiceTest {
	
	@Autowired
	IChildTaskService childTaskService;
	
	@MockBean
	private IChildTaskRepo childTaskRepo;
	
	@TestConfiguration
	static class ChildTaskServiceUnitTest{
		@Bean
		public IChildTaskService ChildTaskService(){
			return new ChildTaskServiceImpl();
		}
	}	
	private ChildTask[] childTasks;
	private ParentTask[] parentTasks;
	
	@Before
	public void setUp()
	{
		parentTasks=new ParentTask[] {
				new ParentTask("Parent Task-Create"),
				new ParentTask("Parent Task-Read"),
				new ParentTask("Parent Task-Update")
		};		
		childTasks=new ChildTask[] { 
				new ChildTask("Child Task 1_1",  LocalDate.now().minusDays(7),  
						LocalDate.now().plusDays(7), 7,
						parentTasks[0]),
				new ChildTask("Child Task 1_2",  LocalDate.now().minusDays(7),  
						LocalDate.now().plusDays(9), 7,
						parentTasks[0]),
				new ChildTask("Child Task 2_1",  LocalDate.now().minusDays(10),  
						LocalDate.now().plusDays(9), 30,
						parentTasks[1]),
				new ChildTask("Child Task 2_2",  LocalDate.now().minusDays(1),  
						LocalDate.now().plusDays(1), 1),
				new ChildTask("Child Task 2_3",  LocalDate.now().minusDays(1),  
						LocalDate.now().plusDays(1), 1,
						parentTasks[2])};
		Mockito.when(childTaskRepo.findByChildTask("Child Task 1_1"))
			.thenReturn(childTasks[0]);
		Mockito.when(childTaskRepo.findByParentID(parentTasks[1]))
			.thenReturn(childTasks[2]);
		Mockito.when(childTaskRepo.findAllByStartDate(LocalDate.now().minusDays(7)))
			.thenReturn(Arrays.asList(childTasks[0],childTasks[1]));
		Mockito.when(childTaskRepo.findAllByEndDate(LocalDate.now().plusDays(1)))
		.thenReturn(Arrays.asList(childTasks[3],childTasks[4]));
		Mockito.when(childTaskRepo.findAllBySeekbar(7))
		.thenReturn(Arrays.asList(childTasks[0],childTasks[1]));
		
		Mockito.when(childTaskRepo.findByChildTask("Child Task 2_1"))
		.thenReturn(childTasks[2]);
		}
	
	@Test
	public void whenFindChildTaskByName() {
		ChildTask actual=childTaskService.findChildTask("Child Task 1_1");
		ChildTask expected=childTasks[0];
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	public void whenFindChildTaskByParentTaskName() {
		ChildTask child=childTaskService.findChildTask("Child Task 2_1");
		ParentTask parentTaskfromChildTask=child.getParentID();
		ChildTask actual=childTaskService.findParentTaskID(parentTaskfromChildTask);
		ChildTask expected=childTasks[2];
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	public void whenFindChildTaskBySartDate()
	{
		List<ChildTask> actual=childTaskService.findAllChildTaskByStartDate(LocalDate.now().minusDays(7));
		List<ChildTask> expected=Arrays.asList(childTasks[0],childTasks[1]);
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	public void whenFindChildTaskByEndDate()
	{
		List<ChildTask> actual=childTaskService.findAllChildTaskByEndDate((LocalDate.now().plusDays(1)));
		List<ChildTask> expected=Arrays.asList(childTasks[3],childTasks[4]);
		assertThat(actual).isEqualTo(expected);
	}
	

	@Test
	public void whenFindChildTaskByPriority() {
		List<ChildTask> actual=childTaskService.findAllChildTaskBySeekbar(7);
		List<ChildTask> expected=Arrays.asList(childTasks[0],childTasks[1]);
		assertThat(actual).isEqualTo(expected);
	}
	
}


