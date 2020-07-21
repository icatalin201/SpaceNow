package space.pal.sig.model.database;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import io.reactivex.Completable;
import space.pal.sig.model.News;
import space.pal.sig.model.NewsSource;

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

}
