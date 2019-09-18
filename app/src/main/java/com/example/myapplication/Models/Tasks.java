package com.example.myapplication.Models;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Tasks {

    public int Id;
    public String TaskId;
    public String ProjectId;
    public String ProjectName;
    public String ProjectCompanyId;
    public String ProjectCompanyName;
    public String TaskListIn;
    public String TaskName;
    public String Description;
    public String StartDate;//Date(1555439400000)\/","
    public String DueDate;//":"\/Date(1555525800000)\/","
    public String Priority;
    public String AssignedTo;
    public String AssignedBy;
    public String IsFileAdded;
    public String FileName;
    public String FilePath;
    public String PrivacyWhoCanSee;
    public String ProgressPercent;
    public String EstTimeToCompleteHr;
    public String EstTimeToCompleteMin;
    public String FollowerPeopleIds;
    public String TasksThatDepends;
    public String RepeatThisAfter;
    public String EndDate;
    public String DateOfEnd;
    public String RepeatD1;
    public String RepeatD2;
    public String RepeatD3;
    public String RepeatD4;
    public String RepeatD5;
    public String RepeatD6;
    public String RepeatD7;
    public String RepeatD8;
    public String RepeatD9;
    public String RepeatD10;
    public String RepeatD11;
    public String RepeatD12;
    public String IsReminderAdded;
    public String TaskUniqueKey;
    public String Tags;
    public int TaskIsActive;
    public String AddedById;
    public String AddedByName;
    public String ClientName;
    public String Doe;//":"\/Date(1555353000000)
    public String Dom;//":"\/Date(1555353000000)\/","
    public int IsDeletedFlag;
    public String IsTaskCompleted;
    public String TaskCompletePercent;
    public String TaskPerLastUpdatedBy;
    public String IsCommentAdded;
    public String CommentCount;
    public String TaskCompletedById;
    public String TaskCompletedByName;
    public String TaskCompleteDate;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTaskId() {
        return TaskId;
    }

    public void setTaskId(String taskId) {
        TaskId = taskId;
    }

    public String getProjectId() {
        return ProjectId;
    }

    public void setProjectId(String projectId) {
        ProjectId = projectId;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getProjectCompanyId() {
        return ProjectCompanyId;
    }

    public void setProjectCompanyId(String projectCompanyId) {
        ProjectCompanyId = projectCompanyId;
    }

    public String getProjectCompanyName() {
        return ProjectCompanyName;
    }

    public void setProjectCompanyName(String projectCompanyName) {
        ProjectCompanyName = projectCompanyName;
    }

    public String getTaskListIn() {
        return TaskListIn;
    }

    public void setTaskListIn(String taskListIn) {
        TaskListIn = taskListIn;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStartDate() {

        String dateFormat = "dd/MM/yyyy hh:mm:ss.SSS";
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        StartDate = StartDate.replaceAll("([^\\d+])", "");
        calendar.setTimeInMillis(Long.parseLong(StartDate));
        return formatter.format(calendar.getTime());
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getDueDate() {
        String dateFormat = "dd/MM/yyyy hh:mm:ss.SSS";
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        DueDate = DueDate.replaceAll("([^\\d+])", "");
        calendar.setTimeInMillis(Long.parseLong(DueDate));
        return formatter.format(calendar.getTime());
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public String getAssignedTo() {
        return AssignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        AssignedTo = assignedTo;
    }

    public String getAssignedBy() {
        return AssignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        AssignedBy = assignedBy;
    }

    public String getIsFileAdded() {
        return IsFileAdded;
    }

    public void setIsFileAdded(String isFileAdded) {
        IsFileAdded = isFileAdded;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public String getPrivacyWhoCanSee() {
        return PrivacyWhoCanSee;
    }

    public void setPrivacyWhoCanSee(String privacyWhoCanSee) {
        PrivacyWhoCanSee = privacyWhoCanSee;
    }

    public String getProgressPercent() {
        return ProgressPercent;
    }

    public void setProgressPercent(String progressPercent) {
        ProgressPercent = progressPercent;
    }

    public String getEstTimeToCompleteHr() {
        return EstTimeToCompleteHr;
    }

    public void setEstTimeToCompleteHr(String estTimeToCompleteHr) {
        EstTimeToCompleteHr = estTimeToCompleteHr;
    }

    public String getEstTimeToCompleteMin() {
        return EstTimeToCompleteMin;
    }

    public void setEstTimeToCompleteMin(String estTimeToCompleteMin) {
        EstTimeToCompleteMin = estTimeToCompleteMin;
    }

    public String getFollowerPeopleIds() {
        return FollowerPeopleIds;
    }

    public void setFollowerPeopleIds(String followerPeopleIds) {
        FollowerPeopleIds = followerPeopleIds;
    }

    public String getTasksThatDepends() {
        return TasksThatDepends;
    }

    public void setTasksThatDepends(String tasksThatDepends) {
        TasksThatDepends = tasksThatDepends;
    }

    public String getRepeatThisAfter() {
        return RepeatThisAfter;
    }

    public void setRepeatThisAfter(String repeatThisAfter) {
        RepeatThisAfter = repeatThisAfter;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getDateOfEnd() {
        return DateOfEnd;
    }

    public void setDateOfEnd(String dateOfEnd) {
        DateOfEnd = dateOfEnd;
    }

    public String getRepeatD1() {
        return RepeatD1;
    }

    public void setRepeatD1(String repeatD1) {
        RepeatD1 = repeatD1;
    }

    public String getRepeatD2() {
        return RepeatD2;
    }

    public void setRepeatD2(String repeatD2) {
        RepeatD2 = repeatD2;
    }

    public String getRepeatD3() {
        return RepeatD3;
    }

    public void setRepeatD3(String repeatD3) {
        RepeatD3 = repeatD3;
    }

    public String getRepeatD4() {
        return RepeatD4;
    }

    public void setRepeatD4(String repeatD4) {
        RepeatD4 = repeatD4;
    }

    public String getRepeatD5() {
        return RepeatD5;
    }

    public void setRepeatD5(String repeatD5) {
        RepeatD5 = repeatD5;
    }

    public String getRepeatD6() {
        return RepeatD6;
    }

    public void setRepeatD6(String repeatD6) {
        RepeatD6 = repeatD6;
    }

    public String getRepeatD7() {
        return RepeatD7;
    }

    public void setRepeatD7(String repeatD7) {
        RepeatD7 = repeatD7;
    }

    public String getRepeatD8() {
        return RepeatD8;
    }

    public void setRepeatD8(String repeatD8) {
        RepeatD8 = repeatD8;
    }

    public String getRepeatD9() {
        return RepeatD9;
    }

    public void setRepeatD9(String repeatD9) {
        RepeatD9 = repeatD9;
    }

    public String getRepeatD10() {
        return RepeatD10;
    }

    public void setRepeatD10(String repeatD10) {
        RepeatD10 = repeatD10;
    }

    public String getRepeatD11() {
        return RepeatD11;
    }

    public void setRepeatD11(String repeatD11) {
        RepeatD11 = repeatD11;
    }

    public String getRepeatD12() {
        return RepeatD12;
    }

    public void setRepeatD12(String repeatD12) {
        RepeatD12 = repeatD12;
    }

    public String getIsReminderAdded() {
        return IsReminderAdded;
    }

    public void setIsReminderAdded(String isReminderAdded) {
        IsReminderAdded = isReminderAdded;
    }

    public String getTaskUniqueKey() {
        return TaskUniqueKey;
    }

    public void setTaskUniqueKey(String taskUniqueKey) {
        TaskUniqueKey = taskUniqueKey;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
    }

    public int getTaskIsActive() {
        return TaskIsActive;
    }

    public void setTaskIsActive(int taskIsActive) {
        TaskIsActive = taskIsActive;
    }

    public String getAddedById() {
        return AddedById;
    }

    public void setAddedById(String addedById) {
        AddedById = addedById;
    }

    public String getAddedByName() {
        return AddedByName;
    }

    public void setAddedByName(String addedByName) {
        AddedByName = addedByName;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getDoe() {
        return Doe;
    }

    public void setDoe(String doe) {
        Doe = doe;
    }

    public String getDom() {
        return Dom;
    }

    public void setDom(String dom) {
        Dom = dom;
    }

    public int getIsDeletedFlag() {
        return IsDeletedFlag;
    }

    public void setIsDeletedFlag(int isDeletedFlag) {
        IsDeletedFlag = isDeletedFlag;
    }

    public String getIsTaskCompleted() {
        return IsTaskCompleted;
    }

    public void setIsTaskCompleted(String isTaskCompleted) {
        IsTaskCompleted = isTaskCompleted;
    }

    public String getTaskCompletePercent() {
        return TaskCompletePercent;
    }

    public void setTaskCompletePercent(String taskCompletePercent) {
        TaskCompletePercent = taskCompletePercent;
    }

    public String getTaskPerLastUpdatedBy() {
        return TaskPerLastUpdatedBy;
    }

    public void setTaskPerLastUpdatedBy(String taskPerLastUpdatedBy) {
        TaskPerLastUpdatedBy = taskPerLastUpdatedBy;
    }

    public String getIsCommentAdded() {
        return IsCommentAdded;
    }

    public void setIsCommentAdded(String isCommentAdded) {
        IsCommentAdded = isCommentAdded;
    }

    public String getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(String commentCount) {
        CommentCount = commentCount;
    }

    public String getTaskCompletedById() {
        return TaskCompletedById;
    }

    public void setTaskCompletedById(String taskCompletedById) {
        TaskCompletedById = taskCompletedById;
    }

    public String getTaskCompletedByName() {
        return TaskCompletedByName;
    }

    public void setTaskCompletedByName(String taskCompletedByName) {
        TaskCompletedByName = taskCompletedByName;
    }

    public String getTaskCompleteDate() {
        return TaskCompleteDate;
    }

    public void setTaskCompleteDate(String taskCompleteDate) {
        TaskCompleteDate = taskCompleteDate;
    }
}
