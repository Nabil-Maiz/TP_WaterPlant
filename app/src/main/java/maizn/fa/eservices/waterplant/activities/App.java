package maizn.fa.eservices.waterplant.activities;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import maizn.fa.eservices.waterplant.entities.DaoMaster;
import maizn.fa.eservices.waterplant.entities.DaoSession;
/*
 * Cette classe se lance avant toutes les autres au démarrage et va permettre d'initialiser des états que l'on va garder tout long du fonctionnement de l'appplication
 *
 */
public class App extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        // on déclare le nom de notre base de donnée sqlite
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "plant-db");
        // on récupère une base de donnée en écriture
        SQLiteDatabase database = helper.getWritableDatabase();
        // on crée une nouvelle session
        // on ne crée pas une fonction permettant de fermer la base cela se fait de manière transparente avec greendao
        daoSession = new DaoMaster(database).newSession();
    }

    public DaoSession getDaoSession() {
        return this.daoSession;
    }
}
