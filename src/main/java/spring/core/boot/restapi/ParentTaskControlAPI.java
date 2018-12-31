package spring.core.boot.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.core.boot.model.ParentTask;
import spring.core.boot.service.IParentTaskService;

@Controller
@RequestMapping("/parent")
@RestController
@CrossOrigin
public class ParentTaskControlAPI {
	
	@Autowired
	private IParentTaskService parentTaskService;
	
	@GetMapping("/view-all-parent-tasks")
	public ResponseEntity<List<ParentTask>> getAllParentTaskList(){
		
		return new ResponseEntity<>(
				parentTaskService.viewAllParentTask(),HttpStatus.OK);
	}
	
	@GetMapping("/parent-task/{id}")
	public ResponseEntity<ParentTask> getParentTaskbyID(@PathVariable("id")int parentTaskID){
		ParentTask parentTask=parentTaskService.viewParentTask(parentTaskID);
		ResponseEntity<ParentTask> responce=null;
		if(parentTask==null) 
			responce=new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			responce=new ResponseEntity<>(parentTask,HttpStatus.OK);
		return responce;

	}
	
	@PostMapping("/add-parent-task")
	public ResponseEntity<ParentTask> createParentTask(@RequestBody ParentTask parentTask){
		ResponseEntity<ParentTask> responce=null;
		
		int parentId=parentTaskService.saveParentTask(parentTask);

		
		if(parentId==-1) {
			responce= new ResponseEntity<ParentTask>(HttpStatus.SERVICE_UNAVAILABLE);}
		else {
		parentTask.setParentTaskID(parentId);
		responce= new ResponseEntity<ParentTask>(parentTask,HttpStatus.CREATED);
		}
		return responce;		
	}
	
	@PutMapping("/edit-parent-task/{id}")
	public ResponseEntity<ParentTask> editParentTask(@PathVariable("id")int parentTaskID,
			@RequestBody ParentTask newparentTask){
		
		ParentTask oldParentTask=parentTaskService.viewParentTask(parentTaskID);
		ResponseEntity<ParentTask> response=null;
		if(oldParentTask==null) {
			response=new ResponseEntity<> (HttpStatus.NOT_FOUND);}
		else {
			oldParentTask.setParentTask(newparentTask.getParentTask());
			parentTaskService.saveParentTask(oldParentTask);
			response=new ResponseEntity<> (oldParentTask,HttpStatus.OK);
			}
		return response;
		}
	
	@DeleteMapping("/delete-parent-task/{id}")
	public ResponseEntity<Void> deleteParentTask(@PathVariable("id")int parentTaskID){
		ResponseEntity<Void> resp=null;
		if(parentTaskService.deleteParentTask(parentTaskID))
		resp=new ResponseEntity<> (HttpStatus.OK);
		else
		resp=new ResponseEntity<> (HttpStatus.NOT_FOUND);
		return resp;
	}

	}
	
