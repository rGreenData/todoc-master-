package com.cleanup.todoc.database;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;
import com.cleanup.todoc.dao.ProjectDao;
import com.cleanup.todoc.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
    public abstract class TodocDataBase extends RoomDatabase {

        //--- SINGLETON ---
        private static volatile TodocDataBase INSTANCE;

        //--- DAO ---
        public abstract TaskDao taskDao();
        public abstract ProjectDao ProjectDao();

        //--- INSTANCE ---
        public static TodocDataBase getInstance(Context pContext) {
            if (INSTANCE == null) {
                synchronized (TodocDataBase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(pContext.getApplicationContext(),
                                TodocDataBase.class, "MyDataBase")
                                .addCallback(new Callback() {
                                    @Override
                                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                       // super.onCreate(db);
                                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                INSTANCE.ProjectDao().
                                                        createProject(new Project(1L, "Projet Tartampion",
                                                                0xFFEADAD1));
                                                INSTANCE.ProjectDao().
                                                        createProject(new Project(2L, "Projet Lucidia",
                                                                0xFFB4CDBA));
                                                INSTANCE.ProjectDao().
                                                        createProject(new Project(3L, "Projet Circus",
                                                                0xFFA3CED2));
                                            }
                                        });
                                    }
                                })
                                .build();
                    }
                }
            }
            return INSTANCE;
        }
    }

