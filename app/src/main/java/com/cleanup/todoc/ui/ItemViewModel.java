package com.cleanup.todoc.ui;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectDataRepository;
import com.cleanup.todoc.repository.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class ItemViewModel extends ViewModel {

    //REPOSITORIES
    private final TaskDataRepository itemDataSource;
    private final ProjectDataRepository projectDataSource;
    private final Executor executor;

    //DATA
    private LiveData<List<Task>> currrentTasks;

    public ItemViewModel(TaskDataRepository pItemDataSource, ProjectDataRepository pProjectDataSource, Executor pExecutor) {
        this.itemDataSource = pItemDataSource;
        this.projectDataSource = pProjectDataSource;
        this.executor = pExecutor;
    }

    public void init(){
        if(this.currrentTasks != null){
            return;
        }

        currrentTasks = itemDataSource.getTasks();
    }

    //FOR PROJECT
    public LiveData<List<Project>>getAllProject(){ return this.projectDataSource.getAllProject(); }

    //FOR PROJECT
    public void addProject(final Project  project){ executor.execute(new Runnable() {
        @Override
        public void run() {
            projectDataSource.addProject(project);
        }
    });
    }

    //FOR TASK
    public void  addTask(final Task pTask){

        executor.execute(new Runnable() {
            @Override
            public void run() {
                itemDataSource.addTask(pTask);
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

    //FOR TASK
    public LiveData<List<Task>> getTasks(){ return itemDataSource.getTasks(); }

}
