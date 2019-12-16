package com.cleanup.todoc.repository;


import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectRepository {

    private final ProjectDao projectDao;

    public ProjectRepository(ProjectDao pProjectDao) {
        projectDao = pProjectDao;
    }

    public LiveData<Project> getProject(long projectId)
    {
        return this.projectDao.getProject(projectId);
    }

    public LiveData<List<Project>> getAllProject()
    {

        return this.projectDao.getAllProject();
    }
}
