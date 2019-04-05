package space.pal.sig.util;

import android.provider.BaseColumns;

/**
 * Created by icatalin on 11.02.2018.
 */

class SqlStructure {

    private SqlStructure() {}

    static class SqlData implements BaseColumns {

        static final String IMAGE_DATA_TABLE = "image_data";
        static final String date_column = "image_date";
        static final String hdurl_column = "image_hd_url";
        static final String url_column = "image_url";
        static final String title_column = "image_title";
        static final String author_column = "image_author";
        static final String description_column = "image_description";

        static final String FACTS_TABLE = "facts";
        static final String fact_name = "name";
        static final String is_fact_fav = "is_fav";

    }
}
