package spring.core.boot;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import spring.core.boot.model.ChildTask;
import spring.core.boot.model.ParentTask;
import spring.core.boot.repository.IChildTaskRepo;
import spring.core.boot.repository.IParentTaskRepo;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class IChildTaskRepoTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private IChildTaskRepo childTaskRepo;
	@Autowired
	private IParentTaskRepo parentTaskRepo;
	
	private ChildTask[] childTasks;
	
	private ParentTask parentTask1=new ParentTask("Parent Task 1");
	private ParentTask parentTask2=new ParentTask("Parent Task 2");
	private ParentTask parentTasks3;
	private ParentTask parentTasks4;


	@Before
	public void setUp()
	{
		parentTasks3=new ParentTask("In memory Parent Task");	
		parentTasks4=new ParentTask("In memory Parent Task Not Linked");	

		entityManager.persist(parentTasks3);
		entityManager.persist(parentTasks4);

		
		childTasks=new ChildTask[] { 
				new ChildTask("Child Task 1_1",  LocalDate.now().minusDays(7),  
						LocalDate.now().plusDays(7), 7,false,
						parentTask1),
				new ChildTask("Child Task 1_2",  LocalDate.now().minusDays(7),  
						LocalDate.now().plusDays(9), 7,false,
						parentTask1),
				new ChildTask("Child Task 2_1",  LocalDate.now().minusDays(10),  
						LocalDate.now().plusDays(9), 30,false,
						parentTask2),
				new ChildTask("Child Task 2_2",  LocalDate.now().minusDays(1),  
						LocalDate.now().plusDays(1), 1,false),
				new ChildTask("Child Task 2_3",  LocalDate.now().minusDays(1),  
						LocalDate.now().plusDays(1), 1,false,
						parentTasks3)};
		
		for(ChildTask child:childTasks)
		{
			entityManager.persist(child);
		}
		
		
	}
	
	@After
	public void tearDown() {
		for(ChildTask child:childTasks)
			entityManager.remove(child);
	}
	
	@Test
	public void whenFindChildTaskByName_returnTask()
	{
		ChildTask actual=childTaskRepo.findByChildTask("Child Task 2_1");
		ChildTask expected=childTasks[2];
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	public void whenFindChildTaskByStartDate_returnTask()
	{
		List<ChildTask> actual=childTaskRepo.findAllByStartDate(LocalDate.now().minusDays(7));
		List<ChildTask> expected=Arrays.asList(childTasks[0],childTasks[1]);
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	public void whenFindChildTaskByEndDate_returnTask()
	{
		List<ChildTask> actual=childTaskRepo.findAllByEndDate(LocalDate.now().plusDays(9));
		List<ChildTask> expected=Arrays.asList(childTasks[1],childTasks[2]);
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	public void whenFindChildTaskByPriority_returnTask()
	{
		List<ChildTask> actual=childTaskRepo.findAllByPriority(7);
		List<ChildTask> expected=Arrays.asList(childTasks[0],childTasks[1]);
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	public void whenFindParentTaskByChildtName_returnTask() 
	{
		ChildTask child=childTaskRepo.findByChildTask("Child Task 1_2");
		int parentid=child.getParent().getParentTaskID();
		int expectedid=childTasks[1].getParent().getParentTaskID();		
		assertThat(parentid).isEqualTo(expectedid);
	}
	
	@Test
	public void whenFindChildTaskByParentName_returnTask()
	{
		Optional<ParentTask> parent=parentTaskRepo.findByParentTask("In memory Parent Task");
		List<ChildTask> actual=childTaskRepo.findAllByParent(parent.get());
		List<ChildTask> expected=Arrays.asList(childTasks[4]);
		assertThat(actual).isEqualTo(expected);
	}
	
	
	@Test
	public void whenFindChildTaskByParentName_returnChildTaskName()
	{
		Optional<ParentTask> parent=parentTaskRepo.findByParentTask("In memory Parent Task");
		List<ChildTask> actual=childTaskRepo.findAllByParent(parent.get());
		String childtaskname=actual.get(0).getChildTask();
		ChildTask expected=childTasks[4];
		String expectedchildtaskname=expected.getChildTask();
		assertThat(childtaskname).isEqualTo(expectedchildtaskname);
	}
}
