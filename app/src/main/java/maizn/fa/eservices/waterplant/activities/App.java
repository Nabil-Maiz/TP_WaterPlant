package maizn.fa.eservices.waterplant.activities;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import maizn.fa.eservices.waterplant.entities.DaoMaster;
import maizn.fa.eservices.waterplant.entities.DaoSession;

public class App extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate(){
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"plant-db");
        SQLiteDatabase database = helper.getWritableDatabase();
        daoSession = new DaoMaster(database).newSession();
    }

    public DaoSession getDaoSession(){
        return this.daoSession;
    }
}
