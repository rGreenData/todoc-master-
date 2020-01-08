package com.cleanupdoc.project.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.cleanupdoc.project.model.Project;
import java.util.List;

@Dao
public interface ProjectDao {

    @Query("SELECT * FROM project")
    LiveData<List<Project>> getAllProject();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long createProject(Project pProject);
}


