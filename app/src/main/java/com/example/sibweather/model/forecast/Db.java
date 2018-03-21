package com.example.sibweather.model.forecast;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.espresso.core.deps.guava.collect.Iterables;
import android.support.test.espresso.core.deps.guava.collect.Lists;

import com.example.sibweather.model.City;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Olga
 * on 17.01.2017.
 */
public class Db {
    //--------------------------------------------------------------------------------------------
    private static final int DB_VERSION = 1;
    //--------------------------------------------------------------------------------------------
    public static class DbHelper extends OrmLiteSqliteOpenHelper {
        private static final String DB_NAME = "sib_weather";

        private static DbHelper helper;

        private Dao<DbTables.Forecast, Integer> forecastsDao;
        private Dao<DbTables.DayDetailForecast, Integer> detailForecastsDao;

        public static DbHelper getInstance(Context context) {
            if (helper == null) {
                synchronized (DbHelper.class) {
                    if (helper == null) {
                        helper = OpenHelperManager.getHelper(context, DbHelper.class);
                    }
                }
            }

            return helper;
        }

        public DbHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
            try {
                TableUtils.createTable(connectionSource, DbTables.Forecast.class);
                TableUtils.createTable(connectionSource, DbTables.DayDetailForecast.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {/**/}

        Dao<DbTables.Forecast, Integer> getForecastsDao() throws SQLException, java.sql.SQLException {
            if (forecastsDao == null) {
                forecastsDao = getDao(DbTables.Forecast.class);
            }
            return forecastsDao;
        }

        Dao<DbTables.DayDetailForecast, Integer> getDetailForecastsDao() throws SQLException, java.sql.SQLException {
            if (detailForecastsDao == null) {
                detailForecastsDao = getDao(DbTables.DayDetailForecast.class);
            }
            return detailForecastsDao;
        }

    }
    //--------------------------------------------------------------------------------------------

    public final static class DAO {
        private final @NotNull DbHelper mDbHelper;

        public DAO(@NotNull Context context) {
            mDbHelper = DbHelper.getInstance(context);
        }

        public void saveForecasts(@NotNull City city, @NotNull List<DayForecast> dayForecasts) {
            try {

                final DeleteBuilder<DbTables.Forecast, Integer> deleteBuilder = mDbHelper.getForecastsDao().deleteBuilder();
                deleteBuilder.where().eq(DbTables.Forecast.Column.CITY, city.name());
                deleteBuilder.delete();

                for (DayForecast forecast : dayForecasts) {
                    mDbHelper.getForecastsDao().createOrUpdate(new DbTables.Forecast(city, forecast));
                }

            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }

        public List<DayForecast> getForecasts(@NotNull City city) {
            try {
                final Map<String, Object> select = new HashMap<>();
                select.put(DbTables.Forecast.Column.CITY, city.name());
                final List<DbTables.Forecast> forecasts = mDbHelper.getForecastsDao().queryForFieldValues(select);
                return Lists.newArrayList(Iterables.transform(forecasts, DbTables.Forecast::asDayForecast));

            } catch (java.sql.SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
