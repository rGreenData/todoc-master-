package com.cleanup.todoc.repository;


import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

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

    public void addProject(Project project){ this.projectDao.createProject(project); }
}
