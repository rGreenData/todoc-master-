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
                                .addCallback(prepopulateDatabase())
                                .build();
                    }
                }
            }
            return INSTANCE;
        }

    /** Prepopulate the data base with projects from project class
     *
     */
    private static Callback prepopulateDatabase(){
            return new Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    Project[] projects = Project.getAllProjects();
                    for (Project project: projects) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("id",project.getId());
                        contentValues.put("name",project.getName());
                        contentValues.put("color",project.getColor());
                        db.insert("project", OnConflictStrategy.IGNORE, contentValues);
                    }
                }
            };
    }

    }

