package com.cleanup.todoc.dao;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
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
    private static long PROJECT_ID = 1;
    private static Project PROJECT_DEMO = new Project(PROJECT_ID, "Toto", 0xFFEADAD1);
    private static Task NEW_TASK_WEEKLY_MEETING = new Task(PROJECT_ID, "Weekly meeting", new Date().getTime());
    private static Task NEW_TASK_FILE_DOCUMENTS = new Task(PROJECT_ID, "File documents", new Date().getTime());
    private static Task NEW_TASK_CONTACT_THE_OFFICE = new Task(PROJECT_ID, "Contact the office", new Date().getTime());


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
    public void insertAndGetProject() {
        // Before insert a new Task
        this.database.ProjectDao().createProject(PROJECT_DEMO);

        // Test Read
        Project project = null;
        try {
            project = LiveDataTestUtil.getValue(this.database.ProjectDao().getProject(PROJECT_ID));
        } catch (InterruptedException pE) {
            pE.printStackTrace();
        }

        assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() ==
                PROJECT_DEMO.getId());

    }


    @Test
    public void getItemWhenNoTaskInserted() {
        List<Task> taskList = null;
        try {
            taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_ID));
        } catch (InterruptedException pE) {
            pE.printStackTrace();
        }

        assertTrue(taskList.isEmpty());
    }

    @Test
    public void insertAndGetItem() {

        List<Task> taskList = null;

        // Before : INSERT a demo project and demo tasks
        this.database.ProjectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(NEW_TASK_WEEKLY_MEETING);
        this.database.taskDao().insertTask(NEW_TASK_FILE_DOCUMENTS);
        this.database.taskDao().insertTask(NEW_TASK_CONTACT_THE_OFFICE);

        //TEST
        try {
            taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_DEMO.getId()));
        } catch (InterruptedException pE) {
            pE.printStackTrace();
        }

        assertTrue(taskList.size() == 3);
    }
}

