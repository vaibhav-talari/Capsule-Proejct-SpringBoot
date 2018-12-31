package spring.core.boot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="parent_task")
public class ParentTask {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int parentTaskID;

	private String parentTask;

	public ParentTask() {}

	public ParentTask(int parentTaskID, String parentTask) {
		super();
		this.parentTaskID = parentTaskID;
		this.parentTask = parentTask;
	}
	public ParentTask(String parentTask) {
		super();
		this.parentTask = parentTask;
	}

	public int getParentTaskID() {
		return parentTaskID;
	}

	public void setParentTaskID(int parentTaskID) {
		this.parentTaskID = parentTaskID;
	}

	public String getParentTask() {
		return parentTask;
	}

	public void setParentTask(String parentTask) {
		this.parentTask = parentTask;
	}

	@Override
	public String toString() {
		return "ParentTask [parentTaskID=" + parentTaskID + ", parentTask=" + parentTask + "]";
	}
	
	
	

}
