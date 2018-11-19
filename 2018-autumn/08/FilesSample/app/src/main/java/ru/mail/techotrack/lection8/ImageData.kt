package ru.mail.techotrack.lection8


object ImageData {
    private val list: Array<Image>

    val images: Array<Image>
        get() = list

    class Image(val text: String) {
    }

    fun getImage(pos: Int): Image {
        return list[pos]
    }

    init {
        list = arrayOf(
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/omar/normal/2.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/omar/normal/4.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/omar/normal/1.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/omar/normal/3.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/omar/normal/5.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/omar/normal/6.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/omar/normal/7.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/omar/normal/8.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/drewer/normal/1.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/drewer/normal/2.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/drewer/normal/3.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/drewer/normal/4.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/drewer/normal/5.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/drewer/normal/6.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/drewer/normal/7.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/drewer/normal/8.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/drewer/normal/9.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/drewer/normal/10.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/ted/normal/Ted-Lansing-01.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/ted/normal/Ted-Lansing-02.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/ted/normal/Ted-Lansing-03.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/ted/normal/Ted-Lansing-04.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/ted/normal/Ted-Lansing-05.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/ted/normal/Ted-Lansing-06.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/ted/normal/Ted-Lansing-07.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/ted/normal/Ted-Lansing-08.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/ducsu/normal/photomatix-gallery-ducsu-01.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/ducsu/normal/photomatix-gallery-ducsu-02.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/ducsu/normal/photomatix-gallery-ducsu-03.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/ducsu/normal/photomatix-gallery-ducsu-04.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/ducsu/normal/photomatix-gallery-ducsu-05.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/ducsu/normal/photomatix-gallery-ducsu-06.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/ducsu/normal/photomatix-gallery-ducsu-07.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/ducsu/normal/photomatix-gallery-ducsu-08.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/ducsu/normal/photomatix-gallery-ducsu-09.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/ducsu/normal/photomatix-gallery-ducsu-10.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/ducsu/normal/photomatix-gallery-ducsu-11.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/ducsu/normal/photomatix-gallery-ducsu-12.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/ducsu/normal/photomatix-gallery-ducsu-13.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/ducsu/normal/photomatix-gallery-ducsu-14.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/ducsu/normal/photomatix-gallery-ducsu-15.jpg"),
                Image("http://images.hdrsoft.com/gallery/photographers/images_squared/ducsu/normal/photomatix-gallery-ducsu-16.jpg"))
    }
}
