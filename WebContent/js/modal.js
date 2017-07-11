/**
 * Created by vjvio_000 on 2017/7/11.
 */
function triggerEdit() {
    if(mediaType=='tv_show') {
        $(".title").show();
        $(".tvtitle").hide();
        $(".type").show();
        $(".etype").hide();
        $(".tvimdb").hide();
        $(".season").show();
        $(".imdb").show();

        $(".url").show();
        $(".episodeThumbnail").hide();
        $(".storyLine").hide();
        $(".titleOfEpisode").hide();
        $(".episode").hide();
        $(".episodeRating").hide();
        $(".episodeRuntime").hide();
        $(".releaseYear").show();
        $(".runtime").show();
        $(".rating").show();
        $(".plot").show();

    } else if(mediaType=='episode') {
        $(".tvtitle").show();
        $(".title").hide();
        $(".type").hide();
        $(".etype").show();
        $(".season").show();
        $(".tvimdb").show();
        $(".imdb").hide();

        $(".releaseYear").hide();
        $(".plot").hide();
        $(".storyLine").show();
        $(".rating").hide();
        $(".runtime").hide();
        $(".episodeRuntime").show();
        $(".episodeRating").show();
        $(".episode").show();
        $(".url").hide();
        $(".titleOfEpisode").show();
        $(".episodeThumbnail").show();
    }
}