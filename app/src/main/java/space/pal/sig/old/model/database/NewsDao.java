package space.pal.sig.old.model.database;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import io.reactivex.Completable;
import space.pal.sig.old.model.News;
import space.pal.sig.old.model.NewsSource;

/**
 * SpaceNow
 * Created by Catalin on 7/18/2020
 **/
@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(News news);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(News... newsList);

    @Query("delete from news")
    Completable delete();

    @Query("select * from news where source = :source order by date desc")
    DataSource.Factory<Integer, News> findAllBySource(NewsSource source);

    @Query("select * from news where id = :id")
    LiveData<News> findById(String id);

}
