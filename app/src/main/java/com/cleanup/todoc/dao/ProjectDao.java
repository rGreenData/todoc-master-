package com.cleanup.todoc.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.cleanup.todoc.model.Project;
import java.util.List;

@Dao
public interface ProjectDao {

    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getAllProject();

    @Query("SELECT * FROM Project WHERE id = :projectId")
    LiveData<Project> getProject(long projectId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long createProject(Project pProject);
}


