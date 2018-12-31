package spring.core.boot;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

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

import spring.core.boot.model.ParentTask;
import spring.core.boot.restapi.ParentTaskControlAPI;
import spring.core.boot.service.IParentTaskService;

@RunWith(SpringRunner.class)
@WebMvcTest(ParentTaskControlAPI.class)
public class ParentTaskControlAPIUnitTest {
	
	@MockBean
	private IParentTaskService parentTaskService;
	
	@Autowired
	private MockMvc mockmvc;
	
	@Test
	public void whenGetAllParentTaskRequest_returnAJSONArray() throws Exception
	{
		ParentTask[] tasks = new ParentTask[] {
				new ParentTask("Meet Manager"),				
				new ParentTask("Complete Database"), 				
				new ParentTask("Submit Reposts") };		
		Mockito.when(parentTaskService.viewAllParentTask())
		.thenReturn(Arrays.asList(tasks));
		
		mockmvc.perform(get("/parent/view-all-parent-tasks").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].parentTask",is(tasks[0].getParentTask())))
		.andExpect(jsonPath("$[1].parentTask",is(tasks[1].getParentTask())))
		.andExpect(jsonPath("$[2].parentTask",is(tasks[2].getParentTask())));
		
	}
	
	@Test
	public void whenGetParentTaskRequest_returnAJSONArray() throws Exception
	{
		ParentTask tasks =new ParentTask("Meet Manager");		
		
		Mockito.when(parentTaskService.viewParentTask(tasks.getParentTaskID()))
		.thenReturn(tasks);
		
		mockmvc.perform(get("/parent/parent-task/0").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.parentTask",is(tasks.getParentTask())));
		
	}
	
	@Test
	public void whenParentTaskPosted() throws Exception{
		String jsonString="{\"parentTask\":\"Achieve By tomorrow\"}";

		Mockito.when(parentTaskService.saveParentTask(Mockito.any()))
		.thenReturn(1);
		
		RequestBuilder req = MockMvcRequestBuilders
				.post("/parent/add-parent-task")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString);
		
		mockmvc.perform(req)
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.parentTaskID",is(1)))
		.andExpect(jsonPath("$.parentTask",is("Achieve By tomorrow")));
	}
	
	@Test
	public void whenParentTaskPut() throws Exception{
		
		ParentTask tasks =new ParentTask("Meet Manager");
		
		Mockito.when(parentTaskService.viewParentTask(tasks.getParentTaskID()))
		.thenReturn(tasks);

		String jsonString="{\"parentTask\":\"Updated Meet Director\"}";

		Mockito.when(parentTaskService.saveParentTask(Mockito.any()))
		.thenReturn(0);
		
		RequestBuilder req = MockMvcRequestBuilders
				.put("/parent/edit-parent-task/0")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString);
		
		mockmvc.perform(req)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.parentTaskID",is(0)))
		.andExpect(jsonPath("$.parentTask",is("Updated Meet Director")));
		
	}
	
	@Test
	public void whenDeletParentTaskRequest_returnNULLArray() throws Exception
	{
		ParentTask tasks = new ParentTask("Meet Manager");			
		
		Mockito.when(parentTaskService.deleteParentTask(tasks.getParentTaskID()))
		.thenReturn(true);
		
		mockmvc.perform(MockMvcRequestBuilders.delete("/parent/delete-parent-task/0")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());				
		
	}
	
	

}
