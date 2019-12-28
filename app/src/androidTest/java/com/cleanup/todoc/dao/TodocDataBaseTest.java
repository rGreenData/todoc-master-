package com.cleanup.todoc.dao;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.cleanup.todoc.database.TodocDataBase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Date;
import java.util.List;
import static junit.framework.TestCase.assertTrue;


@RunWith(AndroidJUnit4.class)
public class TodocDataBaseTest {

    // FOR DATA
    private TodocDataBase database;
    // DATASET FOR TEST
    private static long PROJECT_ID_1 = 1L;
    private static long PROJECT_ID_2 = 2L;
    private static long PROJECT_ID_3 = 3L;
    private static Project PROJECT_DEMO_1 = new Project(PROJECT_ID_1, "Built a wall", 0xFFEADAD1);
    private static Project PROJECT_DEMO_2 = new Project(PROJECT_ID_2, "Association", 0xFFB4CDBA);
    private static Project PROJECT_DEMO_3 = new Project(PROJECT_ID_3, "Renovation", 0xFFA3CED2);
    private static Task NEW_TASK_WEEKLY_MEETING = new Task(PROJECT_ID_1, "Weekly meeting", new Date().getTime());
    private static Task NEW_TASK_FILE_DOCUMENTS = new Task(PROJECT_ID_1, "File documents", new Date().getTime());
    private static Task NEW_TASK_CONTACT_THE_OFFICE = new Task(PROJECT_ID_1, "Contact the office", new Date().getTime());


    //Push the execution of each test synchronously
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    //Create a database directly in memory
    @Before
    public void initDb() {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TodocDataBase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }


    @Test
    public void insertAndGetAllProject()throws InterruptedException {
        // Before insert a new Task
        this.database.ProjectDao().createProject(PROJECT_DEMO_1);
        this.database.ProjectDao().createProject(PROJECT_DEMO_2);
        this.database.ProjectDao().createProject(PROJECT_DEMO_3);

        // Test Read
         List<Project> project = null;
         project = LiveDataTestUtil.getValue(this.database.ProjectDao().getAllProject());

        assertTrue(project.size() == 3);
    }

    @Test
    public void getItemWhenNoTaskInserted()throws InterruptedException {
        List<Task> taskList = null;

            taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());

        assertTrue(taskList.isEmpty());
    }

    @Test
    public void insertAndGetItem() throws InterruptedException{

        List<Task> taskList = null;

        // Before : INSERT a demo project and demo tasks
        this.database.ProjectDao().createProject(PROJECT_DEMO_1);
        this.database.taskDao().insertTask(NEW_TASK_WEEKLY_MEETING);
        this.database.taskDao().insertTask(NEW_TASK_FILE_DOCUMENTS);
        this.database.taskDao().insertTask(NEW_TASK_CONTACT_THE_OFFICE);

        //TEST
        taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());


        assertTrue(taskList.size() == 3);
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException{

        // Before : INSERT a demo project and a demo task.
        this.database.ProjectDao().createProject(PROJECT_DEMO_1);
        this.database.taskDao().insertTask(NEW_TASK_CONTACT_THE_OFFICE);

        //Next, we get the task added
        List<Task> taskToDelete = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        this.database.taskDao().deleteTask(taskToDelete.get(0).getId());

        //TEST
        List<Task> taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        assertTrue(taskList.isEmpty());
    }
}

