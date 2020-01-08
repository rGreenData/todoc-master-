package com.cleanupdoc.project.repository;


import android.arch.lifecycle.LiveData;
import com.cleanupdoc.project.dao.ProjectDao;
import com.cleanupdoc.project.model.Project;
import java.util.List;

public class ProjectDataRepository {

    private final ProjectDao projectDao;

    public ProjectDataRepository(ProjectDao pProjectDao) {
        projectDao = pProjectDao;
    }

    public LiveData<List<Project>> getAllProject()
    {

        return this.projectDao.getAllProject();
    }
}
