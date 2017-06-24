package dao;

import model.LibraryItem;
import model.MediaTypeEnum;
import model.Movie;
import model.OrderByEnum;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * Created by czyczk on 2017-6-23.
 */
public class LibraryItemDao implements ILibraryItemDao<LibraryItem> {
    @Override
    public void add(LibraryItem item) {
        switch (item.getMediaType()) {
            case MOVIE:
                DaoFactory.getMovieDao().add((Movie) item);
                break;
            default: throw new NotImplementedException();
        }
        // TODO
    }

    @Override
    public void delete(LibraryItem item) {
        switch (item.getMediaType()) {
            case MOVIE:
                DaoFactory.getMovieDao().delete((Movie) item);
                break;
            default: throw new NotImplementedException();
        }
        // TODO
    }

    @Override
    public void update(LibraryItem oldItem, LibraryItem newItem) {
        if (oldItem.getMediaType() != newItem.getMediaType())
            throw new LibraryItemDaoException("Media types of the two items are not consistent.");

        switch (newItem.getMediaType()) {
            case MOVIE:
                DaoFactory.getMovieDao().update((Movie) oldItem, (Movie) newItem);
                break;
            default: throw new NotImplementedException();
        }
        // TODO
    }

//    public List<? extends LibraryItem> list(MediaTypeEnum mediaType) {
//        switch (mediaType) {
//            case MOVIE:
//                return DaoFactory.getMovieDao().list();
//            default: throw new IllegalArgumentException("Media type not recognized.");
//        }
//    }

    public int count(MediaTypeEnum mediaType) {
        return count(mediaType, null);
    }
    public int count(MediaTypeEnum mediaType, String[] additionalConditions) {
        switch (mediaType) {
            case MOVIE:
                return DaoFactory.getMovieDao().count(additionalConditions);
            default: throw new NotImplementedException();
        }
    }

    public List<Movie> listMovies() {
        return DaoFactory.getMovieDao().list();
    }
    public List<? extends LibraryItem> list(int ownerId, MediaTypeEnum mediaType, OrderByEnum orderBy, int start, int range) {
        switch (mediaType) {
            case MOVIE:
                return DaoFactory.getMovieDao().list(ownerId, orderBy, start, range);
            default: throw new NotImplementedException();
        }
    }
}
