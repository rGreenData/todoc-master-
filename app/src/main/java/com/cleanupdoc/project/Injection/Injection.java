package com.cleanupdoc.project.Injection;

import android.content.Context;
import com.cleanupdoc.project.database.TodocDataBase;
import com.cleanupdoc.project.repository.ProjectDataRepository;
import com.cleanupdoc.project.repository.TaskDataRepository;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    private static TodocDataBase dataBase;

public static ProjectDataRepository provideProjectDataSource(Context context){

     dataBase = TodocDataBase.getInstance(context);

    return new ProjectDataRepository(dataBase.ProjectDao());
}

public static TaskDataRepository provideTaskDataSource(Context context){

    dataBase = TodocDataBase.getInstance(context);

    return new TaskDataRepository(dataBase.taskDao());
}

public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor();}

public static ViewModelFactory provideViewModelFactory(Context context){
    TaskDataRepository dataSourceTask = provideTaskDataSource(context);
    ProjectDataRepository dataSourceProject = provideProjectDataSource(context);
    Executor executor = provideExecutor();

    return new ViewModelFactory(dataSourceTask, dataSourceProject, executor);
}

}

