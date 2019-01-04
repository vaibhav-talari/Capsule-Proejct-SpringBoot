package spring.core.boot;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
//import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import spring.core.boot.model.ChildTask;
import spring.core.boot.model.ParentTask;
import spring.core.boot.restapi.ChildTaskControlAPI;
import spring.core.boot.service.IChildTaskService;
import spring.core.boot.service.IParentTaskService;

@RunWith(SpringRunner.class)
@WebMvcTest(ChildTaskControlAPI.class)
public class ChildTaskControlAPIUnitTest {
	
	@MockBean
	private IChildTaskService childTaskService;
	
	@MockBean IParentTaskService parentTaskService;
	
	@Autowired
	private MockMvc mockmvc;
	
	private ChildTask[] childTasks;
	private ParentTask[] parentTasks;
	
	
	
	@Test
	public void whenGetAllChildTaskRequest_returnAJSONArray() throws Exception
	{
		parentTasks=new ParentTask[] {
				new ParentTask("Parent Task-Create"),
				new ParentTask("Parent Task-Read"),
				new ParentTask("Parent Task-Update")
		};		
		childTasks=new ChildTask[] { 
				new ChildTask("Child Task 1_1",  LocalDate.now().minusDays(7),  
						LocalDate.now().plusDays(7), 7,false,
						parentTasks[0]),
				new ChildTask("Child Task 1_2",  LocalDate.now().minusDays(7),  
						LocalDate.now().plusDays(9), 7,false,
						parentTasks[0]),
				new ChildTask("Child Task 2_1",  LocalDate.now().minusDays(10),  
						LocalDate.now().plusDays(9), 30,false,
						parentTasks[1]),
				new ChildTask("Child Task 2_2",  LocalDate.now().minusDays(1),  
						LocalDate.now().plusDays(1), 1,false),
				new ChildTask("Child Task 2_3",  LocalDate.now().minusDays(1),  
						LocalDate.now().plusDays(1), 1,false,
						parentTasks[2])};		
		
		Mockito.when(childTaskService.viewAllChildTask())
		.thenReturn(Arrays.asList(childTasks));
		
		mockmvc.perform(get("/child/view-all-child-tasks").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].childTask",is(childTasks[0].getChildTask())))
		.andExpect(jsonPath("$[3].childTask",is(childTasks[3].getChildTask())))
		.andExpect(jsonPath("$[2].parent.parentTask",is(childTasks[2].getParent().getParentTask())));
	}
	
	@Test
	public void whenGetChildTaskRequest_returnAJSON() throws Exception
	{
		ChildTask childTasks=new ChildTask("Child Task 1_1",  LocalDate.now().minusDays(7),  
						LocalDate.now().plusDays(7), 7,false);	
		
		Mockito.when(childTaskService.viewChildTask(childTasks.getChildTaskID()))
		.thenReturn(childTasks);
		
		mockmvc.perform(get("/child/child-task/0").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.childTask",is(childTasks.getChildTask())));
		
	}
	
	@Test
	public void whenChildTaskPosted() throws Exception{
		String jsonString="{\"childTask\":\"Happy New Year\",\"priority\":1,\"parent\":{\"parentTaskID\":1,\"parentTask\":\"ParentTask\"}}";//date works in postman

		Mockito.when(childTaskService.saveChildTask(Mockito.any()))
		.thenReturn(1);
		
		RequestBuilder req = MockMvcRequestBuilders
				.post("/child/add-child-task")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString);
		
		mockmvc.perform(req)
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.childTaskID",is(1)))
		.andExpect(jsonPath("$.childTask",is("Happy New Year")));
	}
	
	@Test
	public void whenChildTaskPut() throws Exception{
		
		parentTasks=new ParentTask[] {
				new ParentTask("Parent Task-Create"),
				new ParentTask("Parent Task-Read"),
				new ParentTask("Parent Task-Update")
		};				
		
		ChildTask childTasks=new ChildTask("Child Task 1_1",  LocalDate.now().minusDays(7),  
						LocalDate.now().plusDays(7), 7,false,
						parentTasks[0]);
		
		Mockito.when(childTaskService.viewChildTask(childTasks.getChildTaskID()))
		.thenReturn(childTasks);

		String jsonString="{\"childTask\":\"Updated Meet Director\",\"parent\":{\"parentTaskID\":2,\"parentTask\":\"Updated ChildTask\"}}";

		Mockito.when(parentTaskService.saveParentTask(Mockito.any()))
		.thenReturn(0);
		
		RequestBuilder req = MockMvcRequestBuilders
				.put("/child/edit-child-task/0")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString);
		
		mockmvc.perform(req)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.childTaskID",is(0)))
		.andExpect(jsonPath("$.childTask",is("Updated Meet Director")))
		.andExpect(jsonPath("$.parent.parentTask",is("Updated ChildTask")));		
	}
	
	@Test
	public void whenDeletChildTaskRequest_returnNULLArray() throws Exception
	{
		
		ParentTask parentTasks=new ParentTask("Parent Task-Create");
	
		ChildTask childTasks=new ChildTask("Child Task 1_1", LocalDate.now().minusDays(7),  
						LocalDate.now().plusDays(7), 7,false,
						parentTasks);		
		
		Mockito.when(childTaskService.deleteChildTask(childTasks.getChildTaskID()))
		.thenReturn(true);
		
		mockmvc.perform(MockMvcRequestBuilders.delete("/child/delete-child-task/0")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());		
	}
	
	/*@Test
	public void whenSearchChildTaskByName_returnAJSON() throws Exception {
		ChildTask childTasks=new ChildTask("Child Task 1_1", LocalDate.now().minusDays(7),  
				LocalDate.now().plusDays(7), 7,false);	

		Mockito.when(childTaskService.findChildTask("Child Task 1_1"))
		.thenReturn(childTasks);
		
		mockmvc.perform(get("/child/search-child-task-name/Child Task 1_1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.endDate",is(childTasks.getEndDate().toString())));
		
	}
	
	@Test
	public void whenSearchChildTaskByParentTask_returnAJSON() throws Exception {
		
		parentTasks=new ParentTask[] {
				new ParentTask("Parent Task-Create"),
				new ParentTask("Parent Task-Read"),
				new ParentTask("Parent Task-Update")
		};
		ChildTask childTasks=new ChildTask("Child Task 1_1", LocalDate.now().minusDays(7),  
				LocalDate.now().plusDays(7), 7,false,parentTasks[1]);	

		ParentTask Value = parentTasks[1];
		Optional<ParentTask> returnValue = Optional.of((ParentTask) Value);
		Mockito.<Optional<ParentTask>>when(parentTaskService.findByParentTaskName("Parent Task-Read"))
		.thenReturn(returnValue);
		
		Mockito.when(childTaskService.findChildTaskByParentTask(parentTasks[1]))
		.thenReturn(Arrays.asList(childTasks));
		
		mockmvc.perform(get("/child/search-child-task-parent-name/Parent Task-Read").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.childTask",is(childTasks.getChildTask().toString())));
		
	}
	
	@Test
	public void whenSearchChildTaskByStartDate_returnAJSONArray() throws Exception {
		
		parentTasks=new ParentTask[] {
				new ParentTask("Parent Task-Create"),
				new ParentTask("Parent Task-Read"),
				new ParentTask("Parent Task-Update")
		};
		childTasks=new ChildTask[] { 
				new ChildTask("Child Task 1_1",  LocalDate.now().minusDays(7),  
						LocalDate.now().plusDays(7), 7,false,
						parentTasks[0]),
				new ChildTask("Child Task 1_2",  LocalDate.now().minusDays(7),  
						LocalDate.now().plusDays(9), 7,false,
						parentTasks[0]),
				new ChildTask("Child Task 2_1",  LocalDate.now().minusDays(10),  
						LocalDate.now().plusDays(9), 30,false,
						parentTasks[1]),
				new ChildTask("Child Task 2_2",  LocalDate.now().minusDays(1),  
						LocalDate.now().plusDays(1), 1,false),
				new ChildTask("Child Task 2_3",  LocalDate.now().minusDays(1),  
						LocalDate.now().plusDays(1), 1,false,
						parentTasks[2])};		
		Mockito.when(childTaskService.findAllChildTaskByStartDate(LocalDate.now().minusDays(7)))
		.thenReturn(Arrays.asList(childTasks[0],childTasks[1]));
		
		mockmvc.perform(get("/child/search-child-task-start-date?date=2018-12-24").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].startDate",is(childTasks[0].getStartDate().toString())))
		.andExpect(jsonPath("$[1].startDate",is(childTasks[1].getStartDate().toString())));
		
	}
	
	@Test
	public void whenSearchChildTaskByEndDate_returnAJSONArray() throws Exception {
		
		parentTasks=new ParentTask[] {
				new ParentTask("Parent Task-Create"),
				new ParentTask("Parent Task-Read"),
				new ParentTask("Parent Task-Update")
		};
		childTasks=new ChildTask[] { 
				new ChildTask("Child Task 1_1",  LocalDate.now().minusDays(7),  
						LocalDate.now().plusDays(7), 7,false,
						parentTasks[0]),
				new ChildTask("Child Task 1_2",  LocalDate.now().minusDays(7),  
						LocalDate.now().minusDays(2), 7,false,
						parentTasks[0]),
				new ChildTask("Child Task 2_1",  LocalDate.now().minusDays(10),  
						LocalDate.now().minusDays(2), 30,false,
						parentTasks[1]),
				new ChildTask("Child Task 2_2",  LocalDate.now().minusDays(1),  
						LocalDate.now().plusDays(1), 1,false),
				new ChildTask("Child Task 2_3",  LocalDate.now().minusDays(1),  
						LocalDate.now().plusDays(1), 1,false,
						parentTasks[2])};		
		Mockito.when(childTaskService.findAllChildTaskByEndDate(LocalDate.now().minusDays(2)))
		.thenReturn(Arrays.asList(childTasks[1],childTasks[2]));
		
		mockmvc.perform(get("/child/search-child-task-end-date?date=2018-12-29").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[1].endDate",is(childTasks[2].getEndDate().toString())));
		
	}
	
	@Test
	public void whenSearchChildTaskByPriority_returnAJSONArray() throws Exception {
		
		parentTasks=new ParentTask[] {
				new ParentTask("Parent Task-Create"),
				new ParentTask("Parent Task-Read"),
				new ParentTask("Parent Task-Update")
		};
		childTasks=new ChildTask[] { 
				new ChildTask("Child Task 1_1",  LocalDate.now().minusDays(7),  
						LocalDate.now().plusDays(7), 7,false,
						parentTasks[0]),
				new ChildTask("Child Task 1_2",  LocalDate.now().minusDays(7),  
						LocalDate.now().plusDays(9), 7,false,
						parentTasks[0]),
				new ChildTask("Child Task 2_1",  LocalDate.now().minusDays(10),  
						LocalDate.now().plusDays(9), 30,false,
						parentTasks[1]),
				new ChildTask("Child Task 2_2",  LocalDate.now().minusDays(1),  
						LocalDate.now().plusDays(1), 1,false),
				new ChildTask("Child Task 2_3",  LocalDate.now().minusDays(1),  
						LocalDate.now().plusDays(1), 1,false,
						parentTasks[2])};		
		Mockito.when(childTaskService.findAllChildTaskByPriority(1))
		.thenReturn(Arrays.asList(childTasks[3],childTasks[4]));
		
		mockmvc.perform(get("/child/search-child-task-priority-date?priority=1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].seekbar",is(childTasks[3].getPriority())))
		.andExpect(jsonPath("$[1].seekbar",is(childTasks[4].getPriority())));

		
	}
*/
}
