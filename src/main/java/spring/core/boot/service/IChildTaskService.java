package spring.core.boot.service;

import java.time.LocalDate;
import java.util.List;

import spring.core.boot.model.ChildTask;
import spring.core.boot.model.ParentTask;


public interface IChildTaskService {
	
	public int saveChildTask(ChildTask task);
	public ChildTask viewChildTask(int childTaskID);
	public List<ChildTask> viewAllChildTask();
	public boolean deleteChildTask(int childTaskID);
	
	public ChildTask findChildTask(String title);
	public ChildTask findParentTaskID(ParentTask parentID);
	public List<ChildTask> findAllChildTaskByStartDate(LocalDate date);
	public List<ChildTask> findAllChildTaskByEndDate(LocalDate date);
	public List<ChildTask> findAllChildTaskBySeekbar(int priority);

}
