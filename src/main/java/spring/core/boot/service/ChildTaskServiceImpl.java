package spring.core.boot.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.core.boot.model.ChildTask;
import spring.core.boot.model.ParentTask;
import spring.core.boot.repository.IChildTaskRepo;

@Service
public class ChildTaskServiceImpl implements IChildTaskService {
	
	@Autowired
	private IChildTaskRepo childTaskRepo;

	@Override
	public int saveChildTask(ChildTask task) {
		ChildTask isSaved=childTaskRepo.save(task);
		return isSaved==null?-1:isSaved.getChildTaskID();
	}

	@Override
	public ChildTask viewChildTask(int childTaskID) {
		Optional<ChildTask> isChildTask=childTaskRepo.findById(childTaskID);
		return isChildTask.isPresent()?isChildTask.get():null;
	}

	@Override
	public List<ChildTask> viewAllChildTask() {
		return childTaskRepo.findAll();
	}

	@Override
	public boolean deleteChildTask(int childTaskID) {
		boolean isDeleted=false;
		if(childTaskRepo.existsById(childTaskID)) {
			
			childTaskRepo.deleteById(childTaskID);
			isDeleted=true;
		}
		return isDeleted;
	}

	@Override
	public ChildTask findChildTask(String title) {
		return childTaskRepo.findByChildTask(title);		
	}

	@Override
	public ChildTask findParentTaskID(ParentTask parentID) {
		return childTaskRepo.findByParentID(parentID);		
	}

	@Override
	public List<ChildTask> findAllChildTaskByStartDate(LocalDate date) {
		return childTaskRepo.findAllByStartDate(date);		
	}

	@Override
	public List<ChildTask> findAllChildTaskByEndDate(LocalDate date) {
		return childTaskRepo.findAllByEndDate(date);		

	}

	@Override
	public List<ChildTask> findAllChildTaskBySeekbar(int priority) {
		return childTaskRepo.findAllBySeekbar(priority);		

	}

}
