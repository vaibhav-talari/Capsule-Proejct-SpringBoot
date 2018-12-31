package spring.core.boot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.core.boot.model.ParentTask;
import spring.core.boot.repository.IParentTaskRepo;

@Service
public class ParentTaskServiceImpl implements IParentTaskService{
	
	@Autowired
	private IParentTaskRepo parentTaskRepo;

	@Override
	public int saveParentTask(ParentTask task) {
		ParentTask isParentTaskSaved=parentTaskRepo.save(task);		
		return isParentTaskSaved==null?-1:isParentTaskSaved.getParentTaskID();
	}
	
	@Override
	public ParentTask viewParentTask(int parentTaskID) {
		Optional<ParentTask> isParentTask=parentTaskRepo.findById(parentTaskID);
		return isParentTask.isPresent()?isParentTask.get():null;
	}

	@Override
	public List<ParentTask> viewAllParentTask() {
		return parentTaskRepo.findAll();
	}

	@Override
	public boolean deleteParentTask(int parentTaskID) {
		boolean isDeleted=false;
		if(parentTaskRepo.existsById(parentTaskID))
		{
			parentTaskRepo.deleteById(parentTaskID);
			isDeleted=true;
		}
		return isDeleted;
	}

	@Override
	public ParentTask findByParentTaskName(String title) {
		return parentTaskRepo.findByParentTask(title);
	}

}
