package spring.core.boot.service;

import java.util.List;
import java.util.Optional;

import spring.core.boot.model.ParentTask;

public interface IParentTaskService {	
	public int saveParentTask(ParentTask task);
	public ParentTask viewParentTask(int parentTaskID);
	public List<ParentTask> viewAllParentTask();
	public boolean deleteParentTask(int parentTaskID);
	public Optional<ParentTask> findByParentTaskName(String title);
}
