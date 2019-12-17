package com.cleanup.todoc.Injection;

import android.content.Context;
import com.cleanup.todoc.database.TodocDataBase;
import com.cleanup.todoc.repository.ProjectDataRepository;
import com.cleanup.todoc.repository.TaskDataRepository;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

public static ProjectDataRepository provideProjectDataSource(Context context){

    TodocDataBase dataBase = TodocDataBase.getInstance(context);

    return new ProjectDataRepository(dataBase.ProjectDao());
}

public static TaskDataRepository provideTaskDataSOurce(Context context){

    TodocDataBase dataBase = TodocDataBase.getInstance(context);

    return new TaskDataRepository(dataBase.taskDao());
}

public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor();}

public static ViewModelFactory provideViewModelFactory(Context context){
    TaskDataRepository dataSourceTask = provideTaskDataSOurce(context);
    ProjectDataRepository dataSourceProject = provideProjectDataSource(context);
    Executor executor = provideExecutor();

    return new ViewModelFactory(dataSourceTask, dataSourceProject, executor);
}

}

