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

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "child_task")
public class ChildTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int childTaskID;

	private String childTask;

    @JsonFormat
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate startDate;
    
    @JsonFormat
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate endDate;

	private int seekbar;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="parentTaskID")
	private ParentTask parentID;

	public ChildTask() {}

	public ChildTask(int childTaskID, String childTask, LocalDate startDate, LocalDate endDate, int seekbar,
			ParentTask parentID) {
		super();
		this.childTaskID = childTaskID;
		this.childTask = childTask;
		this.startDate = startDate;
		this.endDate = endDate;
		this.seekbar = seekbar;
		this.parentID = parentID;
	}
	public ChildTask(String childTask, LocalDate startDate, LocalDate endDate, int seekbar,
			ParentTask parentID) {
		super();
		this.childTask = childTask;
		this.startDate = startDate;
		this.endDate = endDate;
		this.seekbar = seekbar;
		this.parentID = parentID;
	}
	public ChildTask(String childTask, LocalDate startDate, LocalDate endDate, int seekbar) {
		super();
		this.childTask = childTask;
		this.startDate = startDate;
		this.endDate = endDate;
		this.seekbar = seekbar;
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

	public int getSeekbar() {
		return seekbar;
	}

	public void setSeekbar(int seekbar) {
		this.seekbar = seekbar;
	}

	public ParentTask getParentID() {
		return parentID;
	}

	public void setParentID(ParentTask parentID) {
		this.parentID = parentID;
	}

	@Override
	public String toString() {
		return "ChildTask [childTaskID=" + childTaskID + ", childTask=" + childTask + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", seekbar=" + seekbar + ", parentID=" + parentID + "]";
	}
	
	
	
	
}
