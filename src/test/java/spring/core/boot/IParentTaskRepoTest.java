package spring.core.boot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import spring.core.boot.model.ParentTask;
import spring.core.boot.repository.IParentTaskRepo;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class IParentTaskRepoTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private IParentTaskRepo parentTaskRepo;
	
	private ParentTask[] parentTasks;
	
	@Before
	public void setUp()
	{
		parentTasks=new ParentTask[] { 
				new ParentTask("Complete Task 1"),
				new ParentTask("Finish Project"),
				new ParentTask("Meet Manager"),
				new ParentTask("Approve Project")};
		for(ParentTask parent:parentTasks)
		{
			entityManager.persist(parent);
	}}
	
	@After
	public void tearDown() {
		for(ParentTask parent:parentTasks)
			entityManager.remove(parent);
	}
	
	@Test
	public void whenFindParentTaskByParentTask_returnMatchingTask()
	{
		ParentTask actual=parentTaskRepo.findByParentTask("Meet Manager");
		ParentTask expected=parentTasks[2];
		assertThat(actual).isEqualTo(expected);
	}
	
	}
	
