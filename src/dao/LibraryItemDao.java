package dao;

import model.LibraryItem;
import model.MediaTypeEnum;
import model.Movie;

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
        }
    }

    @Override
    public void delete(LibraryItem item) {
        // TODO
    }

    @Override
    public LibraryItem load(LibraryItem item) {
        // TODO
        return null;
    }

    @Override
    public void update(LibraryItem item) {
        // TODO
    }

    public List<? extends LibraryItem> list(MediaTypeEnum mediaType) {
        switch (mediaType) {
            case MOVIE:
                return DaoFactory.getMovieDao().list();
            default: throw new IllegalArgumentException("Media type not recognized.");
        }
    }
}
