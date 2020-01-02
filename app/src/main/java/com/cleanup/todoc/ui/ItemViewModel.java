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
    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    private final Executor executor;

    //DATA
    private LiveData<List<Project>> currentProject;

    public ItemViewModel(TaskDataRepository pItemDataSource, ProjectDataRepository pProjectDataSource, Executor pExecutor) {
        this.taskDataSource = pItemDataSource;
        this.projectDataSource = pProjectDataSource;
        this.executor = pExecutor;
    }

    public void init(){
        if(this.currentProject != null){
            return;
        }
        currentProject = projectDataSource.getAllProject();
    }

    //FOR PROJECT
    public LiveData<List<Project>>getAllProject(){ return currentProject; }

    //FOR TASK
    public void  addTask(final Task pTask){

        executor.execute(new Runnable() {
            @Override
            public void run() {
                taskDataSource.addTask(pTask);
            }
        });
    }

    //FOR TASK
    public void  deleteTask(Long pId){
        final long id = pId;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                taskDataSource.deleteTask(id);
            }
        });
    }

    //FOR TASK
    public LiveData<List<Task>> getTasks(){ return taskDataSource.getTasks(); }

}
