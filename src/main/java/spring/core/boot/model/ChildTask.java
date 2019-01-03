package spring.core.boot.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "child_task")
public class ChildTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int childTaskID;

	private String childTask;

	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate startDate;
    
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate endDate;

	private int priority;
	
	@JsonProperty
	private boolean endTask;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="parentTaskID")
	private ParentTask parent;

	public ChildTask() {}

	public ChildTask(int childTaskID, String childTask, LocalDate startDate, LocalDate endDate, int priority,
			boolean endTask, ParentTask parent) {
		super();
		this.childTaskID = childTaskID;
		this.childTask = childTask;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priority = priority;
		this.endTask = endTask;
		this.parent = parent;
	}
	public ChildTask(String childTask, LocalDate startDate, LocalDate endDate, int priority,
			boolean endTask, ParentTask parent) {
		super();
		this.childTask = childTask;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priority = priority;
		this.endTask = endTask;
		this.parent = parent;
	}
	public ChildTask(String childTask, LocalDate startDate, LocalDate endDate, int priority,
			boolean endTask) {
		super();
		this.childTask = childTask;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priority = priority;
		this.endTask = endTask;
	}

	public int getChildTaskID() {
		return childTaskID;
	}

	public void setChildTaskID(int childTaskID) {
		this.childTaskID = childTaskID;
	}

	public String getChildTask() {
		return childTask;
	}

	public void setChildTask(String childTask) {
		this.childTask = childTask;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	
	public boolean isEndTask() {
		return endTask;
	}

	public void setEndTask(boolean endTask) {
		this.endTask = endTask;
	}

	public ParentTask getParent() {
		return parent;
	}

	public void setParent(ParentTask parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "ChildTask [childTaskID=" + childTaskID + ", childTask=" + childTask + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", priority=" + priority + ", endTask=" + endTask + ", parent=" + parent
				+ "]";
	}

	
	
	
}
