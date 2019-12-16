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
    private static long PROJECT_ID_1 = 1;
    private static long PROJECT_ID_2 = 2;
    private static Project PROJECT_DEMO_1 = new Project(PROJECT_ID_1, "Built a wall", 0xFFEADAD1);
    private static Project PROJECT_DEMO_2 = new Project(PROJECT_ID_2, "Association", 0xFFB4CDBA);
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
    public void insertAndGetProject()throws InterruptedException {
        // Before insert a new Task
        this.database.ProjectDao().createProject(PROJECT_DEMO_1);
        this.database.ProjectDao().createProject(PROJECT_DEMO_2);

        // Test Read
         Project project = null;

            project = LiveDataTestUtil.getValue(this.database.ProjectDao().getProject(PROJECT_ID_1));

       List<Project> projectList = null;

        projectList = LiveDataTestUtil.getValue(this.database.ProjectDao().getAllProject());


        assertTrue(project.getName().equals(PROJECT_DEMO_1.getName()) && project.getId() ==
                PROJECT_DEMO_1.getId());

        assertTrue(projectList.size() == 2);

    }


    @Test
    public void getItemWhenNoTaskInserted()throws InterruptedException {
        List<Task> taskList = null;

            taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_ID_1));

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
        taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_DEMO_1.getId()));


        assertTrue(taskList.size() == 3);
    }

    @Test
    public void insertAndUpdateTask()throws InterruptedException{
        // Before : INSERT a demo project and a demo task.
        this.database.ProjectDao().createProject(PROJECT_DEMO_1);
        this.database.taskDao().insertTask(NEW_TASK_CONTACT_THE_OFFICE);

        // Next, update task added and re-save it
        Task taskUpDate = LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_DEMO_1.getId())).get(0);
        taskUpDate.setName("Hello");
        this.database.taskDao().updateTask(taskUpDate);

        // TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_DEMO_1.getId()));
        assertTrue(tasks.size() == 1 && taskUpDate.getName() == "Hello");

    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException{

        // Before : INSERT a demo project and a demo task.
        this.database.ProjectDao().createProject(PROJECT_DEMO_1);
        this.database.taskDao().insertTask(NEW_TASK_CONTACT_THE_OFFICE);

        //Next, we get the task added
        Task tastToDelete =
                LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_DEMO_1.getId())).get(0);
        this.database.taskDao().deleteTask(tastToDelete.getId());

        //TEST
    List<Task> taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_DEMO_1.getId()));
        assertTrue(taskList.isEmpty());

    }
}

