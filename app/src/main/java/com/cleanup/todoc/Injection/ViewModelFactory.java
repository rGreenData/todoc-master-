package com.cleanup.todoc.Injection;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import com.cleanup.todoc.repository.ProjectDataRepository;
import com.cleanup.todoc.repository.TaskDataRepository;
import com.cleanup.todoc.ui.ItemViewModel;
import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final ProjectDataRepository projectDataSource;
    private final TaskDataRepository taskDataSource;
    private final Executor executor;

    public ViewModelFactory(TaskDataRepository pItemDataSource, ProjectDataRepository pProjectDataSource, Executor pExecutor) {

        this.taskDataSource = pItemDataSource;
        this.projectDataSource = pProjectDataSource;
        this.executor = pExecutor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ItemViewModel.class)){
            return (T) new ItemViewModel(taskDataSource, projectDataSource, executor);
        }
       throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
