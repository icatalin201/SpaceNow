package space.pal.sig.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import space.pal.sig.model.Feed;

@Dao
public interface FeedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void create(Feed... feeds);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Feed... feeds);

    @Delete
    void delete(Feed... feeds);

}
