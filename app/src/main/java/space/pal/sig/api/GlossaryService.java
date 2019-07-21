package space.pal.sig.api;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import space.pal.sig.model.Glossary;

public interface GlossaryService {

    @GET("/api/v3/glossary/")
    Single<List<Glossary>> glossary(@Query("page") String page);

    @GET("/api/v3/glossary?page=all")
    Single<List<Glossary>> glossary();

}
