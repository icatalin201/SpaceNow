package space.pal.sig.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import space.pal.sig.model.Glossary;

@Dao
public interface GlossaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void create(Glossary... glossaries);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Glossary... glossaries);

    @Delete
    void delete(Glossary... glossaries);

    @Query("select * from glossary")
    LiveData<List<Glossary>> findAll();

}
