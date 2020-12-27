package space.pal.sig.view.news

import space.pal.sig.model.entity.News

/**
 * SpaceNow
 * Created by Catalin on 12/27/2020
 **/
interface NewsClickListener {
    fun onClick(news: News)
}