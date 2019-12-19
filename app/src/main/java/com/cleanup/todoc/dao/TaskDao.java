package com.cleanup.todoc.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.cleanup.todoc.model.Task;
import java.util.List;

@Dao
public interface TaskDao {


    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getTasks();

    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert//(onConflict = OnConflictStrategy.IGNORE)
    long insertTask(Task pTask);

    @Query("DELETE FROM Task WHERE id = :taskId")
    int deleteTask(long taskId);
}

