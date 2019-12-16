package com.cleanup.todoc.ui;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;
import java.util.concurrent.Executor;

public class ItemViewModel extends ViewModel {

    //REPOSITORIES
    private final TaskRepository itemDataSource;
    private final ProjectRepository projectDataSource;
    private final Executor executor;

    //DATA
    private LiveData<Project> currrentProject;

    public ItemViewModel(TaskRepository pItemDataSource, ProjectRepository pProjectDataSource, Executor pExecutor) {
        this.itemDataSource = pItemDataSource;
        this.projectDataSource = pProjectDataSource;
        this.executor = pExecutor;
    }

    public void init(long projectId){
        if(this.currrentProject != null){
            return;
        }
        //TODO FOR LOOP TO ADD project in database
        currrentProject = projectDataSource.getProject(projectId);
    }

    //FOR USER
    public LiveData<Project> getProject(long projectId){ return this.currrentProject; }

    //FOR TASK
    public void  addTask(Task pTask){
        final Task task = pTask;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                itemDataSource.addTask(task);
            }
        });
    }

    //FOR TASK
    public void  deleteTask(Long pId){
        final long id = pId;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                itemDataSource.deleteTask(id);
            }
        });
    }




}
