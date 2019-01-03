package spring.core.boot.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.core.boot.model.ChildTask;
import spring.core.boot.model.ParentTask;

@Repository
public interface IChildTaskRepo extends JpaRepository<ChildTask,Integer> {
	
	public ChildTask findByChildTask(String title);
	public List<ChildTask> findAllByParent(ParentTask parent);
	public List<ChildTask> findAllByStartDate(LocalDate date);
	public List<ChildTask> findAllByEndDate(LocalDate date);
	public List<ChildTask> findAllByPriority(int priority);

}
