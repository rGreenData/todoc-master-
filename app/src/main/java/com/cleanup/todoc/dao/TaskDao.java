package com.cleanup.todoc.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task WHERE ProjectId = :projectId")
    LiveData<List<Task>> getTask(long projectId);

    @Insert
    long insertTask(Task pTask);

    @Update
    int updateTask(Task pTask);

    @Query("DELETE FROM Task WHERE id = :taskId")
    int deleteTask(long taskId);
}

