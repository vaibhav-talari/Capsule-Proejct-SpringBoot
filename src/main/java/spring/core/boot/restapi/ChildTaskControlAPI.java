package spring.core.boot.restapi;

//import java.time.LocalDate;
import java.util.List;
//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.annotation.DateTimeFormat;
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
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import spring.core.boot.model.ChildTask;
//import spring.core.boot.model.ParentTask;
import spring.core.boot.service.IChildTaskService;
//import spring.core.boot.service.IParentTaskService;

@Controller
@RequestMapping("/child")
@RestController
@CrossOrigin
public class ChildTaskControlAPI {

	@Autowired
	private IChildTaskService childTaskService;
	//@Autowired
	//private IParentTaskService parentTaskService;

	@GetMapping("/view-all-child-tasks")
	public ResponseEntity<List<ChildTask>> getAllChildTaskList() {
		return new ResponseEntity<>(childTaskService.viewAllChildTask(), HttpStatus.OK);
	}

	@GetMapping("/child-task/{id}")
	public ResponseEntity<ChildTask> getChildTaskbyID(@PathVariable("id") int childTaskID) {
		ChildTask childTask = childTaskService.viewChildTask(childTaskID);
		ResponseEntity<ChildTask> responce = null;
		if (childTask == null)
			responce = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			responce = new ResponseEntity<>(childTask, HttpStatus.OK);
		return responce;

	}

	@PostMapping("/add-child-task")
	public ResponseEntity<ChildTask> createChildTask(@RequestBody ChildTask childTask) {
		ResponseEntity<ChildTask> responce = null;

		int childId = childTaskService.saveChildTask(childTask);
		if (childId==-1) {
			responce = new ResponseEntity<ChildTask>(HttpStatus.SERVICE_UNAVAILABLE);
		} else {
			childTask.setChildTaskID(childId);
			responce = new ResponseEntity<ChildTask>(childTask, HttpStatus.CREATED);
		}
		return responce;
	}

	@PutMapping("/edit-child-task/{id}")
	public ResponseEntity<ChildTask> editChildTask(@PathVariable("id") int childTaskID,
			@RequestBody ChildTask newChildTask) {

		ChildTask oldChildTask = childTaskService.viewChildTask(childTaskID);
		ResponseEntity<ChildTask> response = null;
		if (oldChildTask == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			oldChildTask.setChildTask(newChildTask.getChildTask());
			oldChildTask.setStartDate(newChildTask.getStartDate());
			oldChildTask.setEndDate(newChildTask.getEndDate());
			oldChildTask.setPriority(newChildTask.getPriority());
			oldChildTask.setParent(newChildTask.getParent());
			oldChildTask.setEndTask(newChildTask.isEndTask());
			childTaskService.saveChildTask(oldChildTask);
			response = new ResponseEntity<>(oldChildTask, HttpStatus.OK);
		}
		return response;
	}

	@DeleteMapping("/delete-child-task/{id}")
	public ResponseEntity<Void> deleteChildTask(@PathVariable("id") int childTaskID) {
		ResponseEntity<Void> resp = null;
		if (childTaskService.deleteChildTask(childTaskID))
			resp = new ResponseEntity<>(HttpStatus.OK);
		else
			resp = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return resp;
	}
//--------------------------------NOT REQUIRED-------------------------------------------------------------------//
	/*// search---title
	@GetMapping("/search-child-task-name/{name}")
	public ResponseEntity<ChildTask> searchChildTaskWithName(@PathVariable("name") String childTask) {
		ChildTask task = childTaskService.findChildTask(childTask);

		ResponseEntity<ChildTask> responce = null;
		if (task == null)
			responce = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			responce = new ResponseEntity<>(task, HttpStatus.OK);
		return responce;
	}

	// search---taks with parent-title
	@GetMapping("/search-child-task-parent-name/{name}")
	public ResponseEntity<List<ChildTask>> searchChildTaskWithParentName(@PathVariable("name") String parentTask) {
		Optional<ParentTask> searchtask = parentTaskService.findByParentTaskName(parentTask);
		List<ChildTask> task = childTaskService.findChildTaskByParentTask(searchtask.orElse(null));
		ResponseEntity<List<ChildTask>> responce = null;
		if (task == null)
			responce = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			responce = new ResponseEntity<>(task, HttpStatus.OK);
		return responce;
	}

	// search---taks with Start date
	@GetMapping("/search-child-task-start-date")
	public ResponseEntity<List<ChildTask>> searchChildTaskWithStartDate(@RequestParam(value="date")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
		List<ChildTask> task = childTaskService.findAllChildTaskByStartDate(startDate);
		ResponseEntity<List<ChildTask>> responce = null;
		if (task == null)
			responce = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			responce = new ResponseEntity<>(task, HttpStatus.OK);
		return responce;
	}

	// search---taks with End date
	@GetMapping("/search-child-task-end-date")
	public ResponseEntity<List<ChildTask>> searchChildTaskWithEndDate(@RequestParam(value="date")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		List<ChildTask> task = childTaskService.findAllChildTaskByEndDate(endDate);
		ResponseEntity<List<ChildTask>> responce = null;
		if (task == null)
			responce = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			responce = new ResponseEntity<>(task, HttpStatus.OK);
		return responce;
	}
	
	// search---taks with Priority date
		@GetMapping("/search-child-task-priority-date")
		public ResponseEntity<List<ChildTask>> searchChildTaskWithPriority(@RequestParam(value="priority") int seekbar) {
			List<ChildTask> task = childTaskService.findAllChildTaskByPriority(seekbar);
			ResponseEntity<List<ChildTask>> responce = null;
			if (task == null)
				responce = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			else
				responce = new ResponseEntity<>(task, HttpStatus.OK);
			return responce;
		}*/

}
