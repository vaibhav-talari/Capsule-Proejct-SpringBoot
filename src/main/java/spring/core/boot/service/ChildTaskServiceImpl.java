package spring.core.boot.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.core.boot.model.ChildTask;
import spring.core.boot.model.ParentTask;
import spring.core.boot.repository.IChildTaskRepo;
import spring.core.boot.repository.IParentTaskRepo;

@Service
public class ChildTaskServiceImpl implements IChildTaskService {

	@Autowired
	private IChildTaskRepo childTaskRepo;
	@Autowired
	private IParentTaskRepo parentTaskRepo;

	@Override
	public int saveChildTask(ChildTask task) {
		ChildTask isSaved = null;
		System.out.println("Before entering"+task.isEndTask());
		if (task.isEndTask()) {
			System.out.println(task);
			task.setEndTask(true);
			System.out.println(task.isEndTask());
			if (task.getParent() == null) {
				Optional<ParentTask> parentnone = parentTaskRepo.findByParentTask("-N/A-");
				task.setParent(parentnone.get());
				isSaved = childTaskRepo.save(task);
			} else {
				Optional<ParentTask> parentname = parentTaskRepo.findByParentTask(task.getParent().getParentTask());
				if (parentname.isPresent()) {
					task.setParent(parentname.get());
					isSaved = childTaskRepo.save(task);
				} else {
					isSaved = childTaskRepo.save(task);
				}
			}
		}else {
			System.out.println(task);
			task.setEndTask(false);
			System.out.println(task.isEndTask());
			if (task.getParent() == null) {
				Optional<ParentTask> parentnone = parentTaskRepo.findByParentTask("-N/A-");
				task.setParent(parentnone.get());
				isSaved = childTaskRepo.save(task);
			} else {
				Optional<ParentTask> parentname = parentTaskRepo.findByParentTask(task.getParent().getParentTask());
				if (parentname.isPresent()) {
					task.setParent(parentname.get());
					isSaved = childTaskRepo.save(task);
				} else {
					isSaved = childTaskRepo.save(task);
				}
			}
			
		}
		return isSaved == null ? -1 : isSaved.getChildTaskID();

	}

	@Override
	public ChildTask viewChildTask(int childTaskID) {
		Optional<ChildTask> isChildTask = childTaskRepo.findById(childTaskID);
		return isChildTask.isPresent() ? isChildTask.get() : null;
	}

	@Override
	public List<ChildTask> viewAllChildTask() {
		return childTaskRepo.findAll();
	}

	@Override
	public boolean deleteChildTask(int childTaskID) {
		boolean isDeleted = false;
		if (childTaskRepo.existsById(childTaskID)) {

			childTaskRepo.deleteById(childTaskID);
			isDeleted = true;
		}
		return isDeleted;
	}

	@Override
	public ChildTask findChildTask(String title) {
		return childTaskRepo.findByChildTask(title);
	}

	@Override
	public List<ChildTask> findChildTaskByParentTask(ParentTask parent) {
		return childTaskRepo.findAllByParent(parent);
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
	public List<ChildTask> findAllChildTaskByPriority(int priority) {
		return childTaskRepo.findAllByPriority(priority);

	}

}
