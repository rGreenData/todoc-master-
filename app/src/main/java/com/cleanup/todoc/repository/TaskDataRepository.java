package com.cleanup.todoc.repository;

import android.arch.lifecycle.LiveData;
import com.cleanup.todoc.dao.TaskDao;
import com.cleanup.todoc.model.Task;
import java.util.List;

public class TaskDataRepository {

    private final TaskDao taskDao;

    public TaskDataRepository(TaskDao pTaskDao) {
        taskDao = pTaskDao;
    }

    /**  Returns the project with the given unique identifier
     *
     *
     * @return the project with the given unique identifier
     */
    public LiveData<List<Task>> getTasks(){ return taskDao.getTasks(); }

    /** Add the task given
     *
     * @param pTask the task given
     */
    public void addTask(Task pTask){ taskDao.insertTask(pTask); }

    public void deleteTask(long taskId){ taskDao.deleteTask(taskId); }
}
